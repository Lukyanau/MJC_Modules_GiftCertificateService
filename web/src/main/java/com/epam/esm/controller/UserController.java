package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    @GetMapping(value = "/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getUserOrders(@PathVariable("id") long id) {
        return userService.getUserOrders(id);
    }

    @GetMapping(value = "/{userId}/order/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto getUserOrderById(@PathVariable("userId") long userId, @PathVariable("orderId") long orderId) {
        return userService.getUserOrderById(userId, orderId);
    }

    @PostMapping(value = "{userId}/order/{certificateId}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto makeOrder(@PathVariable("userId") long userId, @PathVariable("certificateId") long certificateId) {
        return userService.makeOrder(userId, certificateId);
    }
}
