package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model_mapper.TagMapper;
import com.epam.esm.repositoty.impl.TagRepositoryImpl;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.exception.exception_code.ExceptionWithCode.*;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepositoryImpl tagRepository;
    private final TagMapper tagMapper;
    private final TagValidator tagValidator;

    @Autowired
    public TagServiceImpl(TagRepositoryImpl tagRepository, TagMapper tagMapper, TagValidator tagValidator) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
        this.tagValidator = tagValidator;
    }

    @Override
    public List<TagDTO> getTags() {
        List<Tag> allTags = tagRepository.getAll();
        if (allTags != null) {
            return allTags.stream().map(tagMapper::convertToDTO).collect(Collectors.toList());
        }
        throw new ServiceException(NOT_FOUND_TAGS.getId(), NOT_FOUND_TAGS.name());
    }

    @Override
    public TagDTO getTagById(long id) {
        tagValidator.checkTagDTOId(id);
        Tag tag = tagRepository.getById(id);
        if (tag == null) {
            throw new ServiceException(TAG_WITH_ID_NOT_FOUND.getId(), TAG_WITH_ID_NOT_FOUND.name());
        }
        return tagMapper.convertToDTO(tag);
    }

    @Override
    public TagDTO getTagByName(String name) {
        tagValidator.checkTagDTOName(name);
        Tag tag = tagRepository.getByName(name);
        if (tag == null) {
            throw new ServiceException(TAG_WITH_NAME_NOT_FOUND.getId(), TAG_WITH_NAME_NOT_FOUND.name());
        }
        return tagMapper.convertToDTO(tag);
    }

    @Override
    public TagDTO addTag(TagDTO tagDTO) {
        tagValidator.checkTagDTOName(tagDTO.getName());
        if (tagRepository.getByName(tagDTO.getName()) != null) {
            throw new ServiceException(NOT_ADD_TAG.getId(), NOT_ADD_TAG.name());
        }
        return tagMapper.convertToDTO(tagRepository.add(tagMapper.convertToEntity(tagDTO)));
    }

    @Override
    public boolean deleteTagById(long id) {
        tagValidator.checkTagDTOId(id);
        if (tagRepository.getById(id) != null) {
            return tagRepository.delete(id);
        }
        throw new ServiceException(TAG_WITH_NAME_NOT_FOUND.getId(), TAG_WITH_NAME_NOT_FOUND.name());
    }

    @Override
    public List<Tag> getTagsByCertificateId(long id) {
        List<Long> certificateTagsIds = tagRepository.getTagsIdsByCertificateId(id);
        return certificateTagsIds.stream().map(tagRepository::getById).collect(Collectors.toList());
    }

}
