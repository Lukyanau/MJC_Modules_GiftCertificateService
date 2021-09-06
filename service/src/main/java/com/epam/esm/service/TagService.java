package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import java.util.List;

public interface TagService {
    List<TagDto> getTags(String name);
    TagDto getTagById(long id);
    TagDto addTag(TagDto tagDTO);
    boolean deleteTagById(long id);
    List<Tag> getTagsByCertificateId(long id);
    void deleteFromCrossTable(long tagId, long certificateId);
}
