package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "orders", itemRelation = "order")
public class OrderDto extends RepresentationModel<OrderDto> {

  private Long id;
  private String userName;
  private List<ResponseCertificateDto> certificates;
  private BigDecimal totalCost;
  private LocalDateTime purchaseTime;
}
