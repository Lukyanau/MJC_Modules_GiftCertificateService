package com.epam.esm.mapper;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.ResponseCertificateDto;
import com.epam.esm.entity.Order;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class OrderMapper {

  private final Gson gson;

  public OrderDto convertToDto(Order order) {
    OrderDto orderDto = new OrderDto();
    orderDto.setId(order.getId());
    orderDto.setUserName(order.getUserName());
    Type listType = new TypeToken<ArrayList<ResponseCertificateDto>>() {}.getType();
    ArrayList<ResponseCertificateDto> certificates =
        gson.fromJson(order.getCertificates(), listType);
    orderDto.setCertificates(certificates);
    orderDto.setTotalCost(order.getCost());
    orderDto.setPurchaseTime(order.getPurchaseTime());
    return orderDto;
  }
}
