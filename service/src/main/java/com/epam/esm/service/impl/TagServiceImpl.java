package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repositoty.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.exception.exception_code.ExceptionDescription.*;
import static com.epam.esm.utils.TagSearchParameters.NAME;

/**
 * Tag class with CRD methods
 *
 * @author Lukyanau I.M.
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final TagValidator tagValidator;

    /**
     * Method for searching all tags
     *
     * @param searchParam is search parameter
     * @return list of founded objects
     */
    @Override
    public List<TagDto> getTags(Map<String, String> searchParam) {
        Optional<List<Tag>> allTags = tagRepository.getAll(Collections.emptyMap());
        if (allTags.isEmpty()) {
            throw new ServiceException(NOT_FOUND_TAGS);
        }
        List<Tag> currentTags = allTags.get();
        if (!searchParam.isEmpty()) {
            if (searchParam.get(NAME) != null) {
                tagValidator.checkTagDtoName(searchParam.get(NAME).trim());
                List<Tag> filteredTags = currentTags.stream().filter(tag -> tag.getName().
                                equals(searchParam.get(NAME).trim().toLowerCase()))
                        .collect(Collectors.toList());
                if (filteredTags.isEmpty()) {
                    throw new ServiceException(NO_TAGS_WITH_THIS_NAME, searchParam.get(NAME).trim());
                }
                return filteredTags.stream().map(tagMapper::convertToDto).collect(Collectors.toList());
            }
            throw new ServiceException(NO_SUCH_SEARCH_PARAMETER, searchParam.keySet().toString());
        }
        return currentTags.stream().map(tagMapper::convertToDto).collect(Collectors.toList());
    }

    /**
     * Method for searching tag by id
     *
     * @param id is tag id
     * @return founded object
     */
    @Override
    public TagDto getTagById(long id) {
        tagValidator.checkTagDtoId(id);
        Optional<Tag> tag = tagRepository.getById(id);
        if (tag.isEmpty()) {
            throw new ServiceException(TAG_WITH_ID_NOT_FOUND, String.valueOf(id));
        }
        return tagMapper.convertToDto(tag.get());
    }

    /**
     * Method for adding tag in DB
     *
     * @param tagDto is object from request
     * @return added object
     */
    @Override
    public TagDto addTag(TagDto tagDto) {
        tagValidator.checkTagDtoName(tagDto.getName());
        tagDto.setName(tagDto.getName());
        if (tagRepository.getByName(tagDto.getName()).isPresent()) {
            throw new ServiceException(NOT_ADD_TAG, tagDto.getName().trim());
        }
        Tag currentTag = tagRepository.add(tagMapper.convertToEntity(tagDto));
        currentTag.setId(tagRepository.getTagId(currentTag.getName()));
        return tagMapper.convertToDto(currentTag);
    }

    /**
     * Method for deleting tag
     *
     * @param id is tag id
     * @return true or false
     */
    @Override
    public boolean deleteTagById(long id) {
        tagValidator.checkTagDtoId(id);
        if (tagRepository.checkUsedTags(id) > 0) {
            throw new ServiceException(TAG_USED_IN_SOME_CERTIFICATES, String.valueOf(id));
        }
        if (!(tagRepository.delete(id) > 0)) {
            throw new ServiceException(NOT_DELETE_TAG, String.valueOf(id));
        }
        return true;
    }

    /**
     * Method for searching all tags
     * for a specific certificate
     *
     * @param id is certificate id
     * @return list of founded tags
     */
    @Override
    public List<Tag> getTagsByCertificateId(long id) {
        Optional<List<Long>> optionalCertificateTagsIds = tagRepository.getTagsIdsByCertificateId(id);
        if (optionalCertificateTagsIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> currentCertificateTagsIds = optionalCertificateTagsIds.get();
        List<Optional<Tag>> currentTags = currentCertificateTagsIds
                .stream().map(tagRepository::getById).collect(Collectors.toList());
        return currentTags.stream().map(Optional::get).collect(Collectors.toList());
    }

    /**
     * Method for deleting relations between certificate and tag
     *
     * @param tagId         is tag id
     * @param certificateId is certificate id
     */
    @Override
    public void deleteFromCrossTable(long tagId, long certificateId) {
        tagRepository.deleteFromCrossTable(tagId, certificateId);
    }

}
