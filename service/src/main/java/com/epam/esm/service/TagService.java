package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.InvalidIdException;
import com.epam.esm.exception.InvalidNameException;
import com.epam.esm.exception.NotFoundException;

import java.util.List;

public interface TagService {
    List<TagDTO> getTags() throws NotFoundException;
    TagDTO getTagById(long id) throws NotFoundException, InvalidIdException;
    TagDTO getTagByName(String name) throws NotFoundException, InvalidNameException;
    TagDTO addTag(TagDTO tagDTO) throws InvalidNameException;
    boolean deleteTagById(long id) throws NotFoundException, InvalidIdException;
}
