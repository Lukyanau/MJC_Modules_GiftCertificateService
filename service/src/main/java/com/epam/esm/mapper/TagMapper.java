package com.epam.esm.mapper;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagMapper {

  private final ModelMapper modelMapper;

  public Tag convertToEntity(TagDto tagDto) {
    return modelMapper.map(tagDto, Tag.class);
  }

  public TagDto convertToDto(Tag tag) {
    return modelMapper.map(tag, TagDto.class);
  }
}
