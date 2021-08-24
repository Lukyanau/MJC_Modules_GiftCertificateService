package com.epam.esm.model_mapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;

public class TagMapper {
    public Tag convertToEntity(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setId(tagDTO.getId());
        tag.setName(tagDTO.getName());
        return tag;
    }

    public TagDTO convertToDTO(Tag tag) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        return tagDTO;
    }
}
