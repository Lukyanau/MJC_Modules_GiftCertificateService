package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "certificates", itemRelation = "certificate")
public class RequestCertificateDto extends RepresentationModel<RequestCertificateDto> {

  private long id;
  private String name;
  private String description;
  private BigDecimal price;
  private int duration;
  private List<TagDto> tags;

  public RequestCertificateDto(
      String name, String description, BigDecimal price, int duration, List<TagDto> tags) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.duration = duration;
    this.tags = tags;
  }
}
