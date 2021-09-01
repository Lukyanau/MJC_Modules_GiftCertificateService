package com.epam.esm.mapper;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public TagMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Tag convertToEntity(TagDto tagDto) {
        return modelMapper.map(tagDto, Tag.class);
    }

    public TagDto convertToDto(Tag tag) {
        return modelMapper.map(tag, TagDto.class);
    }
}
