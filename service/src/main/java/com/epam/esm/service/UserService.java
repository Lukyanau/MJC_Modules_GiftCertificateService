package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
    UserDto getUserById(long id);
    List<OrderDto> getUserOrders(long userId);
    OrderDto getUserOrderById(long userId, long orderId);
    UserDto makeOrder(long userId, long certificateId);
}
