package com.epam.esm.service.impl;

import com.epam.esm.dto.RequestCertificateDto;
import com.epam.esm.dto.ResponseCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.repositoty.CertificateRepository;
import com.epam.esm.repositoty.TagRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.validator.BaseValidator;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.SearchParamsValidator;
import com.epam.esm.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.epam.esm.exception.exception_code.ExceptionDescription.*;
import static com.epam.esm.utils.CertificateSearchParameters.TAG_NAME;

/**
 * Certificate class with CRUD methods
 *
 * @author Lukyanau I.M.
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final CertificateValidator certificateValidator;
    private final CertificateMapper certificateMapper;
    private final SearchParamsValidator searchValidator;
    private final BaseValidator baseValidator;
    private final TagRepository tagRepository;
    private final TagValidator tagValidator;


    /**
     * Transactional method for adding
     * certificate in DB
     *
     * @param certificateDto is object from request
     * @return added object
     */
    @Override
    public ResponseCertificateDto addCertificate(RequestCertificateDto certificateDto) {
        certificateValidator.validateCertificateDto(certificateDto);
        certificateDto.setName(certificateDto.getName().trim().toLowerCase());
        GiftCertificate currentCertificate = certificateMapper.convertToEntity(certificateDto);
        setCertificateDateTime(currentCertificate);
        if (currentCertificate.getTags() == null) {
            ResponseCertificateDto certificateWithoutTags = certificateMapper.
                    convertToDto(certificateRepository.add(currentCertificate));
            certificateWithoutTags.setId(certificateRepository.getCertificateIdByName(certificateWithoutTags.getName()));
            return certificateWithoutTags;
        }
        currentCertificate.getTags().forEach(tag -> tag.setName(tag.getName().trim().toLowerCase()));
        currentCertificate.setTags(currentCertificate.getTags().
                stream().distinct().collect(Collectors.toList()));
        currentCertificate.getTags().forEach(tag -> tagValidator.checkTagDtoName(tag.getName()));
        currentCertificate.getTags().forEach(this::updateCertificateTag);
        ResponseCertificateDto addedResponseCertificate = certificateMapper.
                convertToDto(certificateRepository.add(currentCertificate));
        addedResponseCertificate.setId(certificateRepository.getCertificateIdByName(addedResponseCertificate.getName()));
        return addedResponseCertificate;
    }

    /**
     * Method for searching all certificates
     *
     * @param searchParams is Map with search parameters
     * @return list of founded objects
     */
    @Override
    public List<ResponseCertificateDto> getCertificates(Map<String, String> searchParams) {
        searchValidator.checkSearchParams(searchParams);
        Optional<List<GiftCertificate>> allCertificates = certificateRepository.getAll(searchParams);
        if (allCertificates.isEmpty()) {
            throw new ServiceException(NOT_FOUND_CERTIFICATES);
        }
        List<GiftCertificate> currentCertificates = allCertificates.get();
        if (searchParams.containsKey(TAG_NAME)) {
            List<GiftCertificate> filteredCertificate = getCertificatesByTag(currentCertificates,
                    searchParams.get(TAG_NAME).trim().toLowerCase());
            return filteredCertificate.stream().map(certificateMapper::convertToDto).collect(Collectors.toList());
        }
        return currentCertificates.stream().map(certificateMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ResponseCertificateDto> getCertificatesByTags(List<String> tags) {
        tags.forEach(tagValidator::checkTagDtoName);
        Optional<List<GiftCertificate>> allCertificates = certificateRepository.getAll(Collections.emptyMap());
        if (allCertificates.isEmpty()) {
            throw new ServiceException(NOT_FOUND_CERTIFICATES);
        }
        List<GiftCertificate> currentCertificates = allCertificates.get();
        AtomicReference<List<GiftCertificate>> filteredCertificates = new AtomicReference<>(currentCertificates);
        tags.forEach(tag ->
                filteredCertificates.set(getCertificatesByTag(filteredCertificates.get(), tag.trim().toLowerCase())));
        return filteredCertificates.get().stream().map(certificateMapper::convertToDto).collect(Collectors.toList());
    }

    /**
     * Method for searching certificate by it id
     *
     * @param id is certificate id
     * @return founded object
     */
    @Override
    public ResponseCertificateDto getCertificateById(long id) {
        baseValidator.checkId(id);
        Optional<GiftCertificate> giftCertificate = certificateRepository.getById(id);
        if (giftCertificate.isEmpty()) {
            throw new ServiceException(CERTIFICATE_WITH_ID_NOT_FOUND, String.valueOf(id));
        }
        GiftCertificate currentCertificate = giftCertificate.get();
        return certificateMapper.convertToDto(currentCertificate);
    }

    /**
     * Transactional method for updating certificate
     *
     * @param id             is certificate id
     * @param certificateDto is object from request
     * @return updated object
     */
    @Override
    public ResponseCertificateDto updateCertificate(long id, RequestCertificateDto certificateDto) {
        baseValidator.checkId(id);
        certificateValidator.validateCertificateDto(certificateDto);
        Optional<GiftCertificate> foundedCertificate = certificateRepository.getById(id);
        if (foundedCertificate.isEmpty()) {
            throw new ServiceException(NOT_UPDATE_CERTIFICATE, String.valueOf(id));
        }
        GiftCertificate preUpdatedCertificate = certificateMapper.convertToEntity(certificateDto);
        preUpdatedCertificate.setId(foundedCertificate.get().getId());
        preUpdatedCertificate.setCreated(foundedCertificate.get().getCreated());
        preUpdatedCertificate.setUpdated(LocalDateTime.now());
        if (preUpdatedCertificate.getTags() == null ||
                preUpdatedCertificate.getTags().size() == 0) {
            return certificateWithoutTagsUpdate(preUpdatedCertificate);
        }
        preUpdatedCertificate.getTags().forEach(tag -> tag.setName(tag.getName().trim().toLowerCase()));
        preUpdatedCertificate.setTags(preUpdatedCertificate.getTags().
                stream().distinct().collect(Collectors.toList()));
        return certificateWithTagsUpdate(preUpdatedCertificate);
    }

    /**
     * Method for partial updating certificate
     *
     * @param id                    is certificate id
     * @param preUpdatedCertificate is fields that should be updated
     * @return updated object
     */
    @Override
    public ResponseCertificateDto patchUpdateCertificate(long id, RequestCertificateDto preUpdatedCertificate) {
        baseValidator.checkId(id);
        Optional<GiftCertificate> giftCertificate = certificateRepository.getById(id);
        if (giftCertificate.isEmpty()) {
            throw new ServiceException(NOT_UPDATE_CERTIFICATE, String.valueOf(id));
        }
        preUpdatedCertificate.setId(id);
        checkPatchUpdateObjectFields(preUpdatedCertificate, giftCertificate.get());
        certificateValidator.validateCertificateDto(preUpdatedCertificate);
        GiftCertificate checkedPreUpdatedCertificate = certificateMapper.convertToEntity(preUpdatedCertificate);
        checkedPreUpdatedCertificate.setCreated(giftCertificate.get().getCreated());
        checkedPreUpdatedCertificate.setUpdated(LocalDateTime.now());
        if (checkedPreUpdatedCertificate.getTags() == null) {
            checkedPreUpdatedCertificate.setTags(giftCertificate.get().getTags());
            certificateRepository.updateCertificate(id, checkedPreUpdatedCertificate);
            return certificateMapper.convertToDto(certificateRepository.getById(id).get());
        }
        checkedPreUpdatedCertificate.getTags().forEach(tag -> tag.setName(tag.getName().trim().toLowerCase()));
        checkedPreUpdatedCertificate.setTags(checkedPreUpdatedCertificate.getTags().
                stream().distinct().collect(Collectors.toList()));
        return certificateWithTagsUpdate(checkedPreUpdatedCertificate);
    }

    /**
     * Method for deleting certificate by id
     *
     * @param id is certificate id
     * @return true or false
     */
    @Override
    public boolean deleteCertificateById(long id) {
        baseValidator.checkId(id);
        if (!(certificateRepository.delete(id) > 0)) {
            throw new ServiceException(NOT_DELETE_CERTIFICATE, String.valueOf(id));
        }
        return certificateRepository.delete(id) > 0;
    }

    @Transactional
    void updateCertificateTag(Tag certificateTag) {
        if (tagRepository.getByName(certificateTag.getName()).isEmpty()) {
            tagRepository.add(certificateTag);
        }
        certificateTag.setId(tagRepository.getTagId(certificateTag.getName()));
    }


    private ResponseCertificateDto certificateWithoutTagsUpdate(GiftCertificate certificate) {
        certificateRepository.updateCertificate(certificate.getId(), certificate);
        return certificateMapper.convertToDto(certificate);
    }


    private ResponseCertificateDto certificateWithTagsUpdate(GiftCertificate certificate) {
        certificate.getTags().forEach(tag -> tagValidator.checkTagDtoName(tag.getName()));
        certificate.getTags().forEach(this::updateCertificateTag);
        certificateRepository.updateCertificate(certificate.getId(), certificate);
        return certificateMapper.convertToDto(certificateRepository.getById(certificate.getId()).get());
    }

    private List<GiftCertificate> getCertificatesByTag(List<GiftCertificate> allCertificates, String tagName) {
        return allCertificates.stream().filter(certificate -> verificationCertificateTags
                (certificate.getTags(), tagName)).collect(Collectors.toList());
    }

    private boolean verificationCertificateTags(List<Tag> certificateTags, String tagName) {
        List<Tag> checkedTags = certificateTags.stream().filter(tag -> tag.getName().equals(tagName))
                .collect(Collectors.toList());
        return !checkedTags.isEmpty();
    }

    private void setCertificateDateTime(GiftCertificate addedCertificate) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        addedCertificate.setCreated(currentDateTime);
        addedCertificate.setUpdated(currentDateTime);
    }

    private void checkPatchUpdateObjectFields(RequestCertificateDto preUpdatedCertificate, GiftCertificate giftCertificate) {
        if (preUpdatedCertificate.getName() == null) {
            preUpdatedCertificate.setName(giftCertificate.getName());
        }
        if (preUpdatedCertificate.getDescription() == null) {
            preUpdatedCertificate.setDescription(giftCertificate.getDescription());
        }
        if (preUpdatedCertificate.getPrice() == null) {
            preUpdatedCertificate.setPrice(giftCertificate.getPrice());
        }
        if (preUpdatedCertificate.getDuration() == 0) {
            preUpdatedCertificate.setDuration(giftCertificate.getDuration());
        }
    }
}
