package com.epam.esm.dto;

import lombok.*;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.lang.NonNullApi;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "certificates", itemRelation = "certificate")
public class ResponseCertificateDto extends RequestCertificateDto {

  private LocalDateTime created;
  private LocalDateTime updated;

  public ResponseCertificateDto(
      long id,
      String name,
      String description,
      BigDecimal price,
      int duration,
      List<TagDto> tags,
      LocalDateTime created,
      LocalDateTime updated) {
    super(id, name, description, price, duration, tags);
    this.created = created;
    this.updated = updated;
  }
}
