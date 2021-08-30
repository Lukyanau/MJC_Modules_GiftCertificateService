package com.epam.esm.service.impl;

import com.epam.esm.dto.RequestCertificateDTO;
import com.epam.esm.dto.ResponseCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.exception_code.ExceptionWithCode;
import com.epam.esm.model_mapper.CertificateMapper;
import com.epam.esm.repositoty.impl.CertificateRepositoryImpl;
import com.epam.esm.repositoty.impl.TagRepositoryImpl;
import com.epam.esm.service.CertificateService;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.epam.esm.exception.exception_code.ExceptionWithCode.*;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateValidator certificateValidator;
    private final CertificateRepositoryImpl certificateRepository;
    private final CertificateMapper certificateMapper;
    private final TagValidator tagValidator;
    private final TagServiceImpl tagService;
    private final TagRepositoryImpl tagRepository;

    @Autowired
    public CertificateServiceImpl(CertificateValidator certificateValidator, TagValidator tagValidator,
                                  CertificateRepositoryImpl certificateRepository, CertificateMapper certificateMapper,
                                  TagServiceImpl tagService, TagRepositoryImpl tagRepository) {
        this.certificateValidator = certificateValidator;
        this.tagValidator = tagValidator;
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
        this.tagService = tagService;
        this.tagRepository = tagRepository;
    }

    @Override
    //Enable transaction management
    public ResponseCertificateDTO addCertificate(RequestCertificateDTO certificateDTO) {
        certificateValidator.validateCertificateDTO(certificateDTO);
        if (certificateRepository.getByName(certificateDTO.getName()) != null) {
            throw new ServiceException(NOT_ADD_CERTIFICATE.getId(), NOT_ADD_CERTIFICATE.name());
        }
        GiftCertificate currentCertificate = certificateMapper.convertToEntity(certificateDTO);
        setCertificateTime(currentCertificate);
        if (Objects.isNull(currentCertificate.getCertificateTags())) {
            return certificateMapper.convertToDTO(certificateRepository.add(currentCertificate));
        }
        certificateDTO.getCertificateTags().forEach(s -> tagValidator.checkTagDTOName(s.getName()));
        GiftCertificate addedCertificate = certificateRepository.add(currentCertificate);
        addedCertificate.setId(certificateRepository.getCertificateId(addedCertificate.getName()));
        addedCertificate.getCertificateTags().forEach(t -> {
            checkAddedCertificateTags(t);
            certificateRepository.addCertificateAndTagIds(addedCertificate.getId(), t.getId());
        });
        return certificateMapper.convertToDTO(addedCertificate);
    }

    @Override
    public List<ResponseCertificateDTO> getCertificates() {
        List<GiftCertificate> allCertificates = certificateRepository.getAll();
        if (!Objects.isNull(allCertificates)) {
            allCertificates.forEach(a -> a.setCertificateTags(tagService.getTagsByCertificateId(a.getId())));
            return allCertificates.stream().map(certificateMapper::convertToDTO).collect(Collectors.toList());
        }
        throw new ServiceException(ExceptionWithCode.NOT_FOUND_CERTIFICATES.toString());
    }

    @Override
    public ResponseCertificateDTO getCertificateById(long id) {
        certificateValidator.checkCertificateDTOId(id);
        GiftCertificate giftCertificate = certificateRepository.getById(id);
        if (Objects.isNull(giftCertificate)) {
            throw new ServiceException(ExceptionWithCode.CERTIFICATE_WITH_ID_NOT_FOUND.toString());
        }
        giftCertificate.setCertificateTags(tagService.getTagsByCertificateId(giftCertificate.getId()));
        return certificateMapper.convertToDTO(giftCertificate);
    }


    @Override
    public ResponseCertificateDTO getCertificateByName(String name) {
        certificateValidator.checkCertificateDTOName(name);
        GiftCertificate giftCertificate = certificateRepository.getByName(name);
        if (Objects.isNull(giftCertificate)) {
            throw new ServiceException(ExceptionWithCode.CERTIFICATE_WITH_NAME_NOT_FOUND.toString());
        }
        giftCertificate.setCertificateTags(tagService.getTagsByCertificateId(giftCertificate.getId()));
        return certificateMapper.convertToDTO(giftCertificate);
    }

    @Override
    public ResponseCertificateDTO updateCertificate(RequestCertificateDTO certificateDTO) {
        certificateValidator.validateCertificateDTO(certificateDTO);
        if (Objects.isNull(certificateRepository.getByName(certificateDTO.getName()))) {
            throw new ServiceException(NOT_ADD_CERTIFICATE.getId(), NOT_ADD_CERTIFICATE.name());
        }
        //todo write method
        return null;
    }

    @Override
    public boolean deleteCertificateById(long id) {
        GiftCertificate currentCertificate = certificateMapper.convertToEntity(getCertificateById(id));
        return certificateRepository.delete(currentCertificate.getId());
    }

    @Override
    public List<ResponseCertificateDTO> getCertificatesByTag(String tagName) {
        List<ResponseCertificateDTO> allCertificates = getCertificates();
        return allCertificates.stream().filter(s -> checkCertificateTags(s.getCertificateTags(), tagName))
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseCertificateDTO> getCertificatesByPartOfName(String partOfName) {
        List<ResponseCertificateDTO> allCertificates = getCertificates();
        return allCertificates.stream().filter(s -> s.getName().contains(partOfName)).collect(Collectors.toList());
    }

    @Override
    public List<ResponseCertificateDTO> getCertificatesByPartOfDescription(String partOfDescription) {
        List<ResponseCertificateDTO> allCertificates = getCertificates();
        return allCertificates.stream().filter(s -> s.getDescription().contains(partOfDescription))
                .collect(Collectors.toList());
    }

    private boolean checkCertificateTags(List<TagDTO> certificateTags, String name) {
        List<TagDTO> checkedTags = certificateTags.stream().filter(t -> t.getName().equals(name))
                .collect(Collectors.toList());
        return !checkedTags.isEmpty();
    }

    private void checkAddedCertificateTags(Tag certificateTag) {
        if (Objects.isNull(tagRepository.getByName(certificateTag.getName()))) {
            tagRepository.add(certificateTag);
            certificateTag.setId(tagRepository.getTagId(certificateTag.getName()));
        }
    }

    private void setCertificateTime(GiftCertificate addedCertificate) {
        LocalDateTime currentDataTime = LocalDateTime.now();
        addedCertificate.setCreated(currentDataTime);
        addedCertificate.setUpdated(currentDataTime);
    }

}
