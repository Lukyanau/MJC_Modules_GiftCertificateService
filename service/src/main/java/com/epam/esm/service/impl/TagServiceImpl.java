package com.epam.esm.service.impl;

import com.epam.esm.dto.ResponseCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.InvalidIdException;
import com.epam.esm.exception.InvalidNameException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.exception_code.ExceptionWithCode;
import com.epam.esm.model_mapper.TagMapper;
import com.epam.esm.repositoty.impl.TagRepositoryImpl;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private static final TagServiceImpl instance = new TagServiceImpl();

    private TagServiceImpl() {
    }

    public static TagServiceImpl getInstance() {
        return instance;
    }

    TagValidator tagValidator = TagValidator.getInstance();
    TagRepositoryImpl tagRepository = TagRepositoryImpl.getInstance();
    TagMapper tagMapper = new TagMapper();

    @Override
    public List<TagDTO> getTags() throws NotFoundException {
        List<Tag> allTags = tagRepository.getAll();
        List<TagDTO> allTagDTOs = new ArrayList<>();
        if (allTags != null) {
            for (Tag tag : allTags) {
                allTagDTOs.add(tagMapper.convertToDTO(tag));
            }
            return allTagDTOs;
        }
        throw new NotFoundException(ExceptionWithCode.NOT_FOUND_TAGS.toString());
    }

    @Override
    public TagDTO getTagById(long id) throws NotFoundException, InvalidIdException {
        tagValidator.checkTagDTOId(id);
        Tag tag = tagRepository.getById(id);
        if (tag == null) {
            throw new NotFoundException(ExceptionWithCode.TAG_WITH_ID_NOT_FOUND.toString());
        }
        return tagMapper.convertToDTO(tag);
    }

    @Override
    public TagDTO getTagByName(String name) throws NotFoundException, InvalidNameException {
        tagValidator.checkTagDTOName(name);
        Tag tag = tagRepository.getByName(name);
        if (tag == null) {
            throw new NotFoundException(ExceptionWithCode.TAG_WITH_ID_NOT_FOUND.toString());
        }
        return tagMapper.convertToDTO(tag);
    }

    @Override
    public TagDTO addTag(TagDTO tagDTO) throws InvalidNameException {
        tagValidator.checkTagDTOName(tagDTO.getName());
        Tag addedTag = tagRepository.add(tagMapper.convertToEntity(tagDTO));
        return tagMapper.convertToDTO(addedTag);
    }

    @Override
    public boolean deleteTagById(long id) throws NotFoundException, InvalidIdException {
        Tag tag = tagMapper.convertToEntity(getTagById(id));
        return tagRepository.delete(tag.getId());
    }

}
