package com.epam.esm.service.impl;

import com.epam.esm.dto.RequestCertificateDTO;
import com.epam.esm.dto.ResponseCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.repositoty.impl.CertificateRepositoryImpl;
import com.epam.esm.repositoty.impl.TagRepositoryImpl;
import com.epam.esm.service.CertificateService;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.epam.esm.exception.exception_code.ExceptionDescription.*;
import static com.epam.esm.utils.CertificateSearchParameters.*;

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

    @Transactional
    @Override
    public ResponseCertificateDTO addCertificate(RequestCertificateDTO certificateDTO) {
        certificateValidator.validateCertificateDTO(certificateDTO);
        if (certificateRepository.getByName(certificateDTO.getName()) != null) {
            throw new ServiceException(NOT_ADD_CERTIFICATE);
        }
        GiftCertificate currentCertificate = certificateMapper.convertToEntity(certificateDTO);
        setCertificateTime(currentCertificate);
        if (currentCertificate.getCertificateTags() == null) {
            return certificateMapper.convertToDTO(certificateRepository.add(currentCertificate));
        }
        GiftCertificate addedCertificate = certificateRepository.add(currentCertificate);
        certificateDTO.getCertificateTags().forEach(s -> tagValidator.checkTagDTOName(s.getName()));
        addedCertificate.setId(certificateRepository.getCertificateId(addedCertificate.getName()));
        addedCertificate.getCertificateTags().forEach(t -> {
            updateCertificateTags(t);
            t.setId(tagRepository.getTagId(t.getName()));
            certificateRepository.addCertificateAndTagIds(addedCertificate.getId(), t.getId());
        });
        return certificateMapper.convertToDTO(addedCertificate);
    }

    @Override
    public List<ResponseCertificateDTO> getCertificates() {
        List<GiftCertificate> allCertificates = certificateRepository.getAll();
        if (allCertificates != null) {
            allCertificates.forEach(a -> a.setCertificateTags(tagService.getTagsByCertificateId(a.getId())));
            return allCertificates.stream().map(certificateMapper::convertToDTO).collect(Collectors.toList());
        }
        throw new ServiceException(NOT_FOUND_CERTIFICATES);
    }

    @Override
    public ResponseCertificateDTO getCertificateById(long id) {
        certificateValidator.checkCertificateDTOId(id);
        GiftCertificate giftCertificate = certificateRepository.getById(id);
        if (giftCertificate == null) {
            throw new ServiceException(CERTIFICATE_WITH_ID_NOT_FOUND);
        }
        giftCertificate.setCertificateTags(tagService.getTagsByCertificateId(giftCertificate.getId()));
        return certificateMapper.convertToDTO(giftCertificate);
    }

    @Override
    public List<ResponseCertificateDTO> getCertificatesByParams(Map<String, String> searchParams) {
        List<ResponseCertificateDTO> allCertificates = getCertificates();
        if (!searchParams.keySet().isEmpty()) {
            if (searchParams.get(NAME) != null) {
                certificateValidator.checkCertificateDTOName(searchParams.get(NAME));
                allCertificates = getCertificatesByName(allCertificates, searchParams.get(NAME));
            }
            if (searchParams.get(TAG_NAME) != null) {
                tagValidator.checkTagDTOName(searchParams.get(TAG_NAME));
                allCertificates = getCertificatesByTag(allCertificates, searchParams.get(TAG_NAME));
            }
            if (searchParams.get(PART_OF_NAME) != null) {
                certificateValidator.checkCertificateDTOName(searchParams.get(PART_OF_NAME));
                allCertificates = getCertificatesByPartOfName(allCertificates, searchParams.get(PART_OF_NAME));
            }
            if (searchParams.get(PART_OF_DESCRIPTION) != null) {
                certificateValidator.checkCertificateDTODescription(searchParams.get(PART_OF_DESCRIPTION));
                allCertificates = getCertificatesByPartOfDescription(allCertificates, searchParams.get(PART_OF_DESCRIPTION));
            }
            return allCertificates;
        }
        throw new ServiceException(NO_SEARCH_PARAMETERS_FOR_CERTIFICATE);

    }

    @Transactional
    @Override
    public ResponseCertificateDTO updateCertificate(RequestCertificateDTO certificateDTO) {
        certificateValidator.validateCertificateDTO(certificateDTO);
        if (certificateRepository.getByName(certificateDTO.getName()) == null) {
            throw new ServiceException(NOT_ADD_CERTIFICATE);
        }
        GiftCertificate currentCertificate = certificateMapper.convertToEntity(certificateDTO);
        currentCertificate.setUpdated(LocalDateTime.now());
        if (currentCertificate.getCertificateTags() == null) {
            return certificateMapper.convertToDTO(certificateRepository.updateCertificate(currentCertificate));
        }
        certificateDTO.getCertificateTags().forEach(s -> tagValidator.checkTagDTOName(s.getName()));
        GiftCertificate updatedCertificate = certificateRepository.updateCertificate(currentCertificate);
        updatedCertificate.setId(certificateRepository.getCertificateId(updatedCertificate.getName()));
        updatedCertificate.setCertificateTags(tagService.getTagsByCertificateId(updatedCertificate.getId()));
        currentCertificate.getCertificateTags().forEach(t -> {
            if (!updatedCertificate.getCertificateTags().contains(t)) {
                updateCertificateTags(t);
                t.setId(tagRepository.getTagId(t.getName()));
                certificateRepository.addCertificateAndTagIds(updatedCertificate.getId(), t.getId());
            }
        });
        return certificateMapper.convertToDTO(updatedCertificate);
    }

    @Override
    public boolean deleteCertificateById(long id) {
        GiftCertificate currentCertificate = certificateMapper.convertToEntity(getCertificateById(id));
        return certificateRepository.delete(currentCertificate.getId());
    }

    private List<ResponseCertificateDTO> getCertificatesByName(List<ResponseCertificateDTO> allCertificates,
                                                               String name) {
        return allCertificates.stream().filter(s -> s.getName().equals(name)).collect(Collectors.toList());
    }

    private List<ResponseCertificateDTO> getCertificatesByTag(List<ResponseCertificateDTO> allCertificates, String tagName) {
        return allCertificates.stream().filter(s -> verificationCertificateTags(s.getCertificateTags(), tagName))
                .collect(Collectors.toList());
    }

    private List<ResponseCertificateDTO> getCertificatesByPartOfName(List<ResponseCertificateDTO> allCertificates,
                                                                     String partOfName) {
        return allCertificates.stream().filter(s -> s.getName().contains(partOfName)).collect(Collectors.toList());
    }

    private List<ResponseCertificateDTO> getCertificatesByPartOfDescription(List<ResponseCertificateDTO> allCertificates,
                                                                            String partOfDescription) {
        return allCertificates.stream().filter(s -> s.getDescription().contains(partOfDescription))
                .collect(Collectors.toList());
    }

    private boolean verificationCertificateTags(List<TagDTO> certificateTags, String name) {
        List<TagDTO> checkedTags = certificateTags.stream().filter(t -> t.getName().equals(name))
                .collect(Collectors.toList());
        return !checkedTags.isEmpty();
    }

    private void updateCertificateTags(Tag certificateTag) {
        if (tagRepository.getByName(certificateTag.getName()) == null) {
            tagRepository.add(certificateTag);
        }
    }

    private void setCertificateTime(GiftCertificate addedCertificate) {
        LocalDateTime currentDataTime = LocalDateTime.now();
        addedCertificate.setCreated(currentDataTime);
        addedCertificate.setUpdated(currentDataTime);
    }

}
