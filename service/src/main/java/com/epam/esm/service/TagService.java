package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import java.util.List;

public interface TagService {
    List<TagDTO> getTags();
    TagDTO getTagById(long id);
    TagDTO getTagByName(String name);
    TagDTO addTag(TagDTO tagDTO);
    boolean deleteTagById(long id);
    List<Tag> getTagsByCertificateId(long id);
}
