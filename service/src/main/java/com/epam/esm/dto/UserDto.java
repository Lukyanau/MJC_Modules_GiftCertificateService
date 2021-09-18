package com.epam.esm.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String name;
    private String surname;
    private BigDecimal balance;
    private List<OrderDto> orders;
}
