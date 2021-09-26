package com.epam.esm.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Getter
@Setter
@Relation(collectionRelation = "certificates", itemRelation = "certificate")
public class ResponseCertificateDto extends RequestCertificateDto {

    private LocalDateTime created;
    private LocalDateTime updated;

}
