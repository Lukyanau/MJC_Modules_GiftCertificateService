package com.epam.esm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@NoArgsConstructor
@Setter
public class TagDto extends RepresentationModel<TagDto> {

    private long id;
    private String name;
}
