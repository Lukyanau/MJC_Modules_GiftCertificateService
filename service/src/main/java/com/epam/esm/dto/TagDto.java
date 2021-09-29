package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Relation(collectionRelation = "tags", itemRelation = "tag")
public class TagDto extends RepresentationModel<TagDto> {

  private long id;
  private String name;
}
