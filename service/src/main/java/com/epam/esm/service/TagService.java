package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;
import java.util.Map;

public interface TagService {

    List<TagDto> getTags(Map<String, String> name);
    TagDto getTagById(long id);
    TagDto addTag(TagDto tagDTO);
    boolean deleteTagById(long id);
}
