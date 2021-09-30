package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "users", itemRelation = "user")
public class UserDto extends UserWithoutOrdersDto {

  private List<OrderDto> orders;

  public UserDto(Long id, String name, String surname, List<OrderDto> orders) {
    super(id, name, surname);
    this.orders = orders;
  }
}
