package com.epam.esm.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderDto extends RepresentationModel<OrderDto> {

    private Long id;
    private Long userId;
    private Long certificateId;
    private BigDecimal cost;
    private LocalDateTime purchaseTime;

}
