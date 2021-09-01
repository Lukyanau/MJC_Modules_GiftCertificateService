package com.epam.esm.service.impl;

import com.epam.esm.dto.RequestCertificateDto;
import com.epam.esm.dto.ResponseCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.repositoty.CertificateRepository;
import com.epam.esm.repositoty.TagRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.epam.esm.exception.exception_code.ExceptionDescription.*;
import static com.epam.esm.utils.CertificateSearchParameters.*;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateValidator certificateValidator;
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;
    private final TagValidator tagValidator;
    private final TagService tagService;
    private final TagRepository tagRepository;

    @Autowired
    public CertificateServiceImpl(CertificateValidator certificateValidator, TagValidator tagValidator,
                                  CertificateRepository certificateRepository, CertificateMapper certificateMapper,
                                  TagService tagService, TagRepository tagRepository) {
        this.certificateValidator = certificateValidator;
        this.tagValidator = tagValidator;
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
        this.tagService = tagService;
        this.tagRepository = tagRepository;
    }

    @Transactional
    @Override
    public ResponseCertificateDto addCertificate(RequestCertificateDto certificateDto) {
        certificateValidator.validateCertificateDto(certificateDto);
        if (certificateRepository.getByName(certificateDto.getName()) != null) {
            throw new ServiceException(NOT_ADD_CERTIFICATE);
        }
        GiftCertificate currentCertificate = certificateMapper.convertToEntity(certificateDto);
        setCertificateTime(currentCertificate);
        if (currentCertificate.getCertificateTags() == null) {
            return certificateMapper.convertToDto(certificateRepository.add(currentCertificate));
        }
        GiftCertificate addedCertificate = certificateRepository.add(currentCertificate);
        certificateDto.getCertificateTags().forEach(s -> tagValidator.checkTagDtoName(s.getName()));
        addedCertificate.setId(certificateRepository.getCertificateIdByName(addedCertificate.getName()));
        addedCertificate.getCertificateTags().forEach(t -> {
            updateCertificateTags(t);
            t.setId(tagRepository.getTagId(t.getName()));
            certificateRepository.addCertificateAndTagIds(addedCertificate.getId(), t.getId());
        });
        return certificateMapper.convertToDto(addedCertificate);
    }

    @Override
    public List<ResponseCertificateDto> getCertificates() {
        List<GiftCertificate> allCertificates = certificateRepository.getAll();
        if (allCertificates != null) {
            allCertificates.forEach(a -> a.setCertificateTags(tagService.getTagsByCertificateId(a.getId())));
            return allCertificates.stream().map(certificateMapper::convertToDto).collect(Collectors.toList());
        }
        throw new ServiceException(NOT_FOUND_CERTIFICATES);
    }

    @Override
    public ResponseCertificateDto getCertificateById(long id) {
        certificateValidator.checkCertificateDtoId(id);
        GiftCertificate giftCertificate = certificateRepository.getById(id);
        if (giftCertificate == null) {
            throw new ServiceException(CERTIFICATE_WITH_ID_NOT_FOUND);
        }
        giftCertificate.setCertificateTags(tagService.getTagsByCertificateId(giftCertificate.getId()));
        return certificateMapper.convertToDto(giftCertificate);
    }

    @Override
    public List<ResponseCertificateDto> getCertificatesByParams(Map<String, String> searchParams) {
        List<ResponseCertificateDto> allCertificates = getCertificates();
        if (!searchParams.keySet().isEmpty()) {
            if (searchParams.get(NAME) != null) {
                certificateValidator.checkCertificateDtoName(searchParams.get(NAME));
                allCertificates = getCertificatesByName(allCertificates, searchParams.get(NAME));
            }
            if (searchParams.get(TAG_NAME) != null) {
                tagValidator.checkTagDtoName(searchParams.get(TAG_NAME));
                allCertificates = getCertificatesByTag(allCertificates, searchParams.get(TAG_NAME));
            }
            if (searchParams.get(PART_OF_NAME) != null) {
                certificateValidator.checkCertificateDtoName(searchParams.get(PART_OF_NAME));
                allCertificates = getCertificatesByPartOfName(allCertificates, searchParams.get(PART_OF_NAME));
            }
            if (searchParams.get(PART_OF_DESCRIPTION) != null) {
                certificateValidator.checkCertificateDtoDescription(searchParams.get(PART_OF_DESCRIPTION));
                allCertificates = getCertificatesByPartOfDescription(allCertificates, searchParams.get(PART_OF_DESCRIPTION));
            }
            if (searchParams.get(SORT_BY_NAME) != null) {
                allCertificates.sort(Comparator.comparing(ResponseCertificateDto::getName));
            }
            if (searchParams.get(SORT_BY_DATE) != null) {
                allCertificates.sort(Comparator.comparing(ResponseCertificateDto::getCreated));
            }
            return allCertificates;
        }
        throw new ServiceException(NO_SEARCH_PARAMETERS_FOR_CERTIFICATE);

    }

    @Transactional
    @Override
    public ResponseCertificateDto updateCertificate(RequestCertificateDto certificateDto) {
        certificateValidator.validateCertificateDto(certificateDto);
        if (certificateRepository.getByName(certificateDto.getName()) == null) {
            throw new ServiceException(NOT_ADD_CERTIFICATE);
        }
        GiftCertificate currentCertificate = certificateMapper.convertToEntity(certificateDto);
        currentCertificate.setUpdated(LocalDateTime.now());
        if (currentCertificate.getCertificateTags() == null) {
            return certificateMapper.convertToDto(certificateRepository.updateCertificate(currentCertificate));
        }
        certificateDto.getCertificateTags().forEach(s -> tagValidator.checkTagDtoName(s.getName()));
        GiftCertificate updatedCertificate = certificateRepository.updateCertificate(currentCertificate);
        updatedCertificate.setId(certificateRepository.getCertificateIdByName(updatedCertificate.getName()));
        updatedCertificate.setCertificateTags(tagService.getTagsByCertificateId(updatedCertificate.getId()));
        currentCertificate.getCertificateTags().forEach(t -> {
            if (!updatedCertificate.getCertificateTags().contains(t)) {
                updateCertificateTags(t);
                t.setId(tagRepository.getTagId(t.getName()));
                certificateRepository.addCertificateAndTagIds(updatedCertificate.getId(), t.getId());
            }
        });
        return certificateMapper.convertToDto(updatedCertificate);
    }

    @Override
    public boolean deleteCertificateById(long id) {
        GiftCertificate currentCertificate = certificateMapper.convertToEntity(getCertificateById(id));
        return certificateRepository.delete(currentCertificate.getId());
    }

    private List<ResponseCertificateDto> getCertificatesByName(List<ResponseCertificateDto> allCertificates,
                                                               String name) {
        return allCertificates.stream().filter(s -> s.getName().equals(name)).collect(Collectors.toList());
    }

    private List<ResponseCertificateDto> getCertificatesByTag(List<ResponseCertificateDto> allCertificates, String tagName) {
        return allCertificates.stream().filter(s -> verificationCertificateTags(s.getCertificateTags(), tagName))
                .collect(Collectors.toList());
    }

    private List<ResponseCertificateDto> getCertificatesByPartOfName(List<ResponseCertificateDto> allCertificates,
                                                                     String partOfName) {
        return allCertificates.stream().filter(s -> s.getName().contains(partOfName)).collect(Collectors.toList());
    }

    private List<ResponseCertificateDto> getCertificatesByPartOfDescription(List<ResponseCertificateDto> allCertificates,
                                                                            String partOfDescription) {
        return allCertificates.stream().filter(s -> s.getDescription().contains(partOfDescription))
                .collect(Collectors.toList());
    }

    private boolean verificationCertificateTags(List<TagDto> certificateTags, String name) {
        List<TagDto> checkedTags = certificateTags.stream().filter(t -> t.getName().equals(name))
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
