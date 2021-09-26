package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserWithoutOrdersDto;

import java.util.List;

public interface UserService {

    List<UserWithoutOrdersDto> getUsers(Integer page, Integer size);
    UserWithoutOrdersDto getUserById(long id);
    List<OrderDto> getUserOrders(long userId, Integer page, Integer size);
    OrderDto getUserOrderById(long userId, long orderId);
    TagDto getUserMostUsedTag();
    UserDto makeOrder(long userId, List<Long> certificatesIds);
}
