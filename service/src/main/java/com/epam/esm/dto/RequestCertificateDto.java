package com.epam.esm.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Relation(collectionRelation = "certificates", itemRelation = "certificate")
public class RequestCertificateDto extends RepresentationModel<RequestCertificateDto> {

    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private List<TagDto> tags;

}
