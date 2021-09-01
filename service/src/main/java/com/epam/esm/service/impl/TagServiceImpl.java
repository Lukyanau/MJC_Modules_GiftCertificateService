package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repositoty.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.exception.exception_code.ExceptionDescription.*;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final TagValidator tagValidator;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, TagMapper tagMapper, TagValidator tagValidator) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
        this.tagValidator = tagValidator;
    }

    @Override
    public List<TagDto> getTags() {
        List<Tag> allTags = tagRepository.getAll();
        if (allTags != null) {
            return allTags.stream().map(tagMapper::convertToDto).collect(Collectors.toList());
        }
        throw new ServiceException(NOT_FOUND_TAGS);
    }

    @Override
    public TagDto getTagById(long id) {
        tagValidator.checkTagDtoId(id);
        Tag tag = tagRepository.getById(id);
        if (tag == null) {
            throw new ServiceException(TAG_WITH_ID_NOT_FOUND);
        }
        return tagMapper.convertToDto(tag);
    }

    @Override
    public TagDto getTagByName(String name) {
        tagValidator.checkTagDtoName(name);
        Tag tag = tagRepository.getByName(name);
        if (tag == null) {
            throw new ServiceException(TAG_WITH_NAME_NOT_FOUND);
        }
        return tagMapper.convertToDto(tag);
    }

    @Override
    public TagDto addTag(TagDto tagDto) {
        tagValidator.checkTagDtoName(tagDto.getName());
        if (tagRepository.getByName(tagDto.getName()) != null) {
            throw new ServiceException(NOT_ADD_TAG);
        }
        return tagMapper.convertToDto(tagRepository.add(tagMapper.convertToEntity(tagDto)));
    }

    @Override
    public boolean deleteTagById(long id) {
        tagValidator.checkTagDtoId(id);
        if (tagRepository.getById(id) != null) {
            return tagRepository.delete(id);
        }
        throw new ServiceException(TAG_WITH_NAME_NOT_FOUND);
    }

    @Override
    public List<Tag> getTagsByCertificateId(long id) {
        List<Long> certificateTagsIds = tagRepository.getTagsIdsByCertificateId(id);
        if (certificateTagsIds == null) {
            return new ArrayList<>();
        }
        return certificateTagsIds.stream().map(tagRepository::getById).collect(Collectors.toList());
    }

}
