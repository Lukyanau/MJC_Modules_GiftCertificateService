package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import java.util.List;

public interface TagService {
    List<TagDto> getTags();
    TagDto getTagById(long id);
    TagDto getTagByName(String name);
    TagDto addTag(TagDto tagDTO);
    boolean deleteTagById(long id);
    List<Tag> getTagsByCertificateId(long id);
}
