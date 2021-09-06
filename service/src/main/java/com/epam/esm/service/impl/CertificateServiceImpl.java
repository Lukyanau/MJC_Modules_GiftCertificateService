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
import com.epam.esm.service.TagService;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.SearchParamsValidator;
import com.epam.esm.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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

    private final CertificateValidator certificateValidator;
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;
    private final TagValidator tagValidator;
    private final TagService tagService;
    private final TagRepository tagRepository;
    private final SearchParamsValidator searchValidator;

    /**
     * Transactional method for adding
     * certificate in DB
     *
     * @param certificateDto is object from request
     * @return added object
     */
    @Transactional
    @Override
    public ResponseCertificateDto addCertificate(RequestCertificateDto certificateDto) {
        certificateValidator.validateCertificateDto(certificateDto);
        GiftCertificate currentCertificate = certificateMapper.convertToEntity(certificateDto);
        setCertificateDateTime(currentCertificate);
        if (currentCertificate.getCertificateTags() == null) {
            return certificateMapper.convertToDto(certificateRepository.add(currentCertificate));
        }
        GiftCertificate addedCertificate = certificateRepository.add(currentCertificate);
        certificateDto.getCertificateTags().forEach(s -> tagValidator.checkTagDtoName(s.getName()));
        addedCertificate.setId(certificateRepository.getCertificateIdByName(addedCertificate.getName()));
        addedCertificate.getCertificateTags().forEach(t -> {
            updateCertificateTag(t);
            t.setId(tagRepository.getTagId(t.getName()));
            certificateRepository.addCertificateAndTagIds(addedCertificate.getId(), t.getId());
        });
        return certificateMapper.convertToDto(addedCertificate);
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
        currentCertificates.forEach(a -> a.setCertificateTags(tagService.getTagsByCertificateId(a.getId())));
        if (searchParams.containsKey(TAG_NAME)) {
            List<GiftCertificate> filteredCertificate = getCertificatesByTag(currentCertificates, searchParams.get(TAG_NAME));
            return filteredCertificate.stream().map(certificateMapper::convertToDto).collect(Collectors.toList());
        }
        return currentCertificates.stream().map(certificateMapper::convertToDto).collect(Collectors.toList());
    }

    /**
     * Method for searching certificate by it id
     *
     * @param id is certificate id
     * @return founded object
     */
    @Override
    public ResponseCertificateDto getCertificateById(long id) {
        certificateValidator.checkCertificateDtoId(id);
        Optional<GiftCertificate> giftCertificate = certificateRepository.getById(id);
        if (giftCertificate.isEmpty()) {
            throw new ServiceException(CERTIFICATE_WITH_ID_NOT_FOUND, String.valueOf(id));
        }
        GiftCertificate currentCertificate = giftCertificate.get();
        currentCertificate.setCertificateTags(tagService.getTagsByCertificateId(currentCertificate.getId()));
        return certificateMapper.convertToDto(currentCertificate);
    }

    /**
     * Transactional method for updating certificate
     *
     * @param id             is certificate id
     * @param certificateDto is object from request
     * @return updated object
     */
    @Transactional
    @Override
    public ResponseCertificateDto updateCertificate(long id, RequestCertificateDto certificateDto) {
        certificateValidator.checkCertificateDtoId(id);
        certificateValidator.validateCertificateDto(certificateDto);
        Optional<GiftCertificate> foundedCertificate = certificateRepository.getById(id);
        if (foundedCertificate.isEmpty()) {
            throw new ServiceException(NOT_UPDATE_CERTIFICATE, String.valueOf(id));
        }
        GiftCertificate preUpdatedCertificate = certificateMapper.convertToEntity(certificateDto);
        preUpdatedCertificate.setId(foundedCertificate.get().getId());
        preUpdatedCertificate.setCreated(foundedCertificate.get().getCreated());
        preUpdatedCertificate.setUpdated(LocalDateTime.now());
        if (preUpdatedCertificate.getCertificateTags() == null ||
                preUpdatedCertificate.getCertificateTags().size() == 0) {
            return certificateWithoutTagsUpdate(preUpdatedCertificate);
        }
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
        certificateValidator.checkCertificateDtoId(id);
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
        if (checkedPreUpdatedCertificate.getCertificateTags() == null) {
            checkedPreUpdatedCertificate.setCertificateTags(
                    tagService.getTagsByCertificateId(checkedPreUpdatedCertificate.getId()));
            certificateRepository.updateCertificate(id, checkedPreUpdatedCertificate);
            return certificateMapper.convertToDto(checkedPreUpdatedCertificate);
        }
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
        certificateValidator.checkCertificateDtoId(id);
        if (!(certificateRepository.delete(id)>0)){
            throw new ServiceException(NOT_DELETE_CERTIFICATE, String.valueOf(id));
        }
        return certificateRepository.delete(id) > 0;
    }

    private ResponseCertificateDto certificateWithoutTagsUpdate(GiftCertificate certificate) {
        List<Tag> tagsToDelete = tagService.getTagsByCertificateId(certificate.getId());
        tagsToDelete.forEach(t -> tagService.deleteFromCrossTable(t.getId(), certificate.getId()));
        certificateRepository.updateCertificate(certificate.getId(), certificate);
        return certificateMapper.convertToDto(certificate);
    }

    private ResponseCertificateDto certificateWithTagsUpdate(GiftCertificate certificate) {
        certificate.getCertificateTags().forEach(s -> tagValidator.checkTagDtoName(s.getName()));
        certificateRepository.updateCertificate(certificate.getId(), certificate);
        certificate.getCertificateTags().forEach(t -> {
            if (tagRepository.getByName(t.getName()).isPresent()) {
                t.setId(tagRepository.getTagId(t.getName()));
            }
        });
        List<Tag> currentTags = tagService.getTagsByCertificateId(certificate.getId());
        currentTags.forEach(t -> {
            if (!certificate.getCertificateTags().contains(t)) {
                tagService.deleteFromCrossTable(t.getId(), certificate.getId());
            }
        });
        List<Tag> remainingTags = tagService.getTagsByCertificateId(certificate.getId());
        certificate.getCertificateTags().forEach(t -> {
            if (!remainingTags.contains(t)) {
                updateCertificateTag(t);
                certificateRepository.addCertificateAndTagIds(certificate.getId(), t.getId());
            }
        });
        return certificateMapper.convertToDto(certificate);
    }

    private List<GiftCertificate> getCertificatesByTag(List<GiftCertificate> allCertificates, String tagName) {
        return allCertificates.stream().filter(s -> verificationCertificateTags(s.getCertificateTags(), tagName))
                .collect(Collectors.toList());
    }

    private boolean verificationCertificateTags(List<Tag> certificateTags, String tagName) {
        List<Tag> checkedTags = certificateTags.stream().filter(t -> t.getName().equals(tagName))
                .collect(Collectors.toList());
        return !checkedTags.isEmpty();
    }

    private void updateCertificateTag(Tag certificateTag) {
        if (tagRepository.getByName(certificateTag.getName()).isEmpty()) {
            tagRepository.add(certificateTag);
            certificateTag.setId(tagRepository.getTagId(certificateTag.getName()));
        }
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
