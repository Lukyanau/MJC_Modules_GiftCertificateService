package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.BaseValidator;
import com.epam.esm.validator.SearchParamsValidator;
import com.epam.esm.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.exception.exception_code.ExceptionDescription.*;

/**
 * Tag class with CRD methods
 *
 * @author Lukyanau I.M.
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepositoryImpl tagRepository;
    private final TagMapper tagMapper;
    private final TagValidator tagValidator;
    private final BaseValidator baseValidator;
    private final SearchParamsValidator searchParamsValidator;

    /**
     * Method for searching all tags
     *
     * @param searchParam is search parameter
     * @return list of founded objects
     */
    @Override
    public List<TagDto> getTags(Map<String, String> searchParam, Integer page, Integer size) {
        baseValidator.checkPaginationParams(page, size);
        searchParamsValidator.checkTagSearchParams(searchParam);
        Optional<List<Tag>> allTags = tagRepository.getAll(searchParam, page, size);
        if (allTags.isEmpty()) {
            throw new ServiceException(NOT_FOUND_TAGS);
        }
        List<Tag> currentTags = allTags.get();
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
        baseValidator.checkId(id);
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
        tagDto.setName(tagDto.getName().trim().toLowerCase());
        if (tagRepository.getByName(tagDto.getName()).isPresent()) {
            throw new ServiceException(NOT_ADD_TAG, tagDto.getName());
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
        baseValidator.checkId(id);
        if (tagRepository.checkUsedTags(id) > 0) {
            throw new ServiceException(TAG_USED_IN_SOME_CERTIFICATES, String.valueOf(id));
        }
        if (!(tagRepository.delete(id))) {
            throw new ServiceException(NOT_DELETE_TAG, String.valueOf(id));
        }
        return true;
    }
}
