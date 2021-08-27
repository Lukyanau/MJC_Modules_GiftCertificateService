package com.epam.esm.service.impl;

import com.epam.esm.dto.RequestCertificateDTO;
import com.epam.esm.dto.ResponseCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.NotAddException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.exception_code.ExceptionWithCode;
import com.epam.esm.model_mapper.CertificateMapper;
import com.epam.esm.repositoty.impl.CertificateRepositoryImpl;
import com.epam.esm.service.CertificateService;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.exception.exception_code.ExceptionWithCode.*;
@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateValidator certificateValidator;
    private final CertificateRepositoryImpl certificateRepository;
    private final CertificateMapper certificateMapper;
    private final TagValidator tagValidator;
    private final TagServiceImpl tagService;

    @Autowired
    public CertificateServiceImpl(CertificateValidator certificateValidator, TagValidator tagValidator,
                                  CertificateRepositoryImpl certificateRepository, CertificateMapper certificateMapper,
                                  TagServiceImpl tagService) {
        this.certificateValidator = certificateValidator;
        this.tagValidator = tagValidator;
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
        this.tagService = tagService;
    }

    @Override
    public ResponseCertificateDTO addCertificate(RequestCertificateDTO certificateDTO) {
        certificateValidator.validateCertificateDTO(certificateDTO);
        if(certificateRepository.getByName(certificateDTO.getName()) == null){
            throw new NotAddException(NOT_ADD_CERTIFICATE.getId(), NOT_ADD_CERTIFICATE.name());
        }
        certificateDTO.getCertificateTags().forEach(s -> tagValidator.checkTagDTOName(s.getName()));
        GiftCertificate currentCertificate = certificateMapper.convertToEntity(certificateDTO);
        LocalDateTime currentDataTime = LocalDateTime.now();
        currentCertificate.setCreated(currentDataTime);
        currentCertificate.setUpdated(currentDataTime);
        GiftCertificate addedCertificate = certificateRepository.add(currentCertificate);
        return certificateMapper.convertToDTO(addedCertificate);
    }

    @Override
    public List<ResponseCertificateDTO> getCertificates() {
        List<GiftCertificate> allCertificates = certificateRepository.getAll();
        if (allCertificates != null) {
            allCertificates.forEach(a->a.setCertificateTags(tagService.getTagsByCertificateId(a.getId())));
            return allCertificates
                    .stream().map(certificateMapper::convertToDTO).collect(Collectors.toList());
        }
        throw new NotFoundException(ExceptionWithCode.NOT_FOUND_CERTIFICATES.toString());
    }

    @Override
    public ResponseCertificateDTO getCertificateById(long id) {
        certificateValidator.checkCertificateDTOId(id);
        GiftCertificate giftCertificate = certificateRepository.getById(id);
        if (giftCertificate == null) {
            throw new NotFoundException(ExceptionWithCode.CERTIFICATE_WITH_ID_NOT_FOUND.toString());
        }
        giftCertificate.setCertificateTags(tagService.getTagsByCertificateId(giftCertificate.getId()));
        return certificateMapper.convertToDTO(giftCertificate);
    }


    @Override
    public ResponseCertificateDTO getCertificateByName(String name) {
        certificateValidator.checkCertificateDTOName(name);
        GiftCertificate giftCertificate = certificateRepository.getByName(name);
        if (giftCertificate == null) {
            throw new NotFoundException(ExceptionWithCode.CERTIFICATE_WITH_NAME_NOT_FOUND.toString());
        }
        giftCertificate.setCertificateTags(tagService.getTagsByCertificateId(giftCertificate.getId()));
        return certificateMapper.convertToDTO(giftCertificate);
    }

    @Override
    public ResponseCertificateDTO updateCertificate(RequestCertificateDTO certificateDTO) {
        return null;
    }

    @Override
    public boolean deleteCertificateById(long id) {
        GiftCertificate currentCertificate = certificateMapper.convertToEntity(getCertificateById(id));
        return certificateRepository.delete(currentCertificate.getId());
    }

    @Override
    public List<ResponseCertificateDTO> getCertificatesByTag(String tagName) {
        return null;
    }

    @Override
    public List<ResponseCertificateDTO> getCertificatesByPartOfName(String partOfName) {
        return null;
    }

    @Override
    public List<ResponseCertificateDTO> getCertificatesByPartOfDescription(String partOfDescription) {
        return null;
    }
}
