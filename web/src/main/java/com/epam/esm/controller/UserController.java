package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    private static final long USER_ID_EXAMPLE = 1;
    private static final long ORDER_ID_EXAMPLE = USER_ID_EXAMPLE;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UserDto> getUsers() {
        List<UserDto> currentUsers = userService.getUsers();
        currentUsers.forEach(user -> {
            Link getByIdLink = linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel();
            Link getOrdersLink = linkTo(methodOn(UserController.class).getUserOrders(user.getId())).withRel("getUserOrders");
            Link getOrderByIdLink = linkTo(methodOn(UserController.class).getUserOrderById(user.getId(), ORDER_ID_EXAMPLE)).
                    withRel("getUserOrderById");
            Link getMostUsedTagLink = linkTo(methodOn(UserController.class).getUserMostUsedTag()).withRel("getMostUsedTag");
            Link makeOrderLink = linkTo(methodOn(UserController.class).makeOrder(user.getId(), ORDER_ID_EXAMPLE)).
                    withRel("makeOrder");
            user.add(getByIdLink, getOrdersLink, getOrderByIdLink, getMostUsedTagLink, makeOrderLink);
        });
        Link selfLink = linkTo(UserController.class).withSelfRel();
        return CollectionModel.of(currentUsers, selfLink);
    }

    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<UserDto> getUserById(@PathVariable("id") long id) {
        UserDto currentUser = userService.getUserById(id);
        Link getUsersLink = linkTo(methodOn(UserController.class).getUsers()).withRel("getUsers");
        Link getOrdersLink = linkTo(methodOn(UserController.class).getUserOrders(id)).
                withRel("getUserOrders");
        Link getOrderByIdLink = linkTo(methodOn(UserController.class).getUserOrderById(id, ORDER_ID_EXAMPLE)).
                withRel("getUserOrderById");
        Link getMostUsedTagLink = linkTo(methodOn(UserController.class).getUserMostUsedTag()).withRel("getMostUsedTag");
        Link makeOrderLink = linkTo(methodOn(UserController.class).makeOrder(id, ORDER_ID_EXAMPLE)).
                withRel("makeOrder");
        Link selfLink = linkTo(UserController.class).withSelfRel();
        return EntityModel.of(currentUser, getUsersLink, getOrdersLink, getOrderByIdLink, getMostUsedTagLink,
                makeOrderLink, selfLink);
    }

    @GetMapping(value = "/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<OrderDto> getUserOrders(@PathVariable("id") long id) {
        List<OrderDto> currentOrders = userService.getUserOrders(id);
        currentOrders.forEach(order -> {
            Link getAllUsers = linkTo(methodOn(UserController.class).getUsers()).withRel("getAllUsers");
            Link getByIdLink = linkTo(methodOn(UserController.class).getUserById(id)).withRel("getUserById");
            Link getOrderByIdLink = linkTo(methodOn(UserController.class).getUserOrderById(id, ORDER_ID_EXAMPLE)).
                    withRel("getUserOrderById");
            Link getMostUsedTagLink = linkTo(methodOn(UserController.class).getUserMostUsedTag()).withRel("getMostUsedTag");
            Link makeOrderLink = linkTo(methodOn(UserController.class).makeOrder(id, ORDER_ID_EXAMPLE)).
                    withRel("makeOrder");
            order.add(getAllUsers, getByIdLink, getOrderByIdLink, getMostUsedTagLink, makeOrderLink);
        });
        Link selfLink = linkTo(UserController.class).withSelfRel();
        return CollectionModel.of(currentOrders, selfLink);
    }

    @GetMapping(value = "/{userId}/order/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<OrderDto> getUserOrderById(@PathVariable("userId") long userId, @PathVariable("orderId") long orderId) {
        OrderDto currentOrder = userService.getUserOrderById(userId, orderId);
        Link getUsersLink = linkTo(methodOn(UserController.class).getUsers()).withRel("getUsers");
        Link getByIdLink = linkTo(methodOn(UserController.class).getUserById(userId)).withRel("getUserById");
        Link getOrdersLink = linkTo(methodOn(UserController.class).getUserOrders(userId)).
                withRel("getUserOrders");
        Link getMostUsedTagLink = linkTo(methodOn(UserController.class).getUserMostUsedTag()).withRel("getMostUsedTag");
        Link makeOrderLink = linkTo(methodOn(UserController.class).makeOrder(userId, ORDER_ID_EXAMPLE)).
                withRel("makeOrder");
        Link selfLink = linkTo(UserController.class).withSelfRel();
        return EntityModel.of(currentOrder, getUsersLink, getByIdLink, getOrdersLink, getMostUsedTagLink,
                makeOrderLink, selfLink);
    }

    @GetMapping(value = "/most_used_tag")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<TagDto> getUserMostUsedTag() {
        TagDto currentTag = userService.getUserMostUsedTag();
        Link getUsersLink = linkTo(methodOn(UserController.class).getUsers()).withRel("getUsers");
        Link getByIdLink = linkTo(methodOn(UserController.class).getUserById(USER_ID_EXAMPLE)).withRel("getUserById");
        Link getOrdersLink = linkTo(methodOn(UserController.class).getUserOrders(USER_ID_EXAMPLE)).
                withRel("getUserOrders");
        Link getOrderByIdLink = linkTo(methodOn(UserController.class).getUserOrderById(USER_ID_EXAMPLE, ORDER_ID_EXAMPLE)).
                withRel("getUserOrderById");
        Link makeOrderLink = linkTo(methodOn(UserController.class).makeOrder(USER_ID_EXAMPLE, ORDER_ID_EXAMPLE)).
                withRel("makeOrder");
        Link selfLink = linkTo(UserController.class).withSelfRel();
        return EntityModel.of(currentTag, getUsersLink, getByIdLink, getOrdersLink, getOrderByIdLink, makeOrderLink,
                selfLink);
    }

    @PostMapping(value = "{userId}/order/{certificateId}")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<UserDto> makeOrder(@PathVariable("userId") long userId,
                                          @PathVariable("certificateId") long certificateId) {
        UserDto currentUser = userService.makeOrder(userId, certificateId);
        Link getUsersLink = linkTo(methodOn(UserController.class).getUsers()).withRel("getUsers");
        Link getByIdLink = linkTo(methodOn(UserController.class).getUserById(userId)).withRel("getUserById");
        Link getOrdersLink = linkTo(methodOn(UserController.class).getUserOrders(userId)).
                withRel("getUserOrders");
        Link getOrderByIdLink = linkTo(methodOn(UserController.class).getUserOrderById(userId, ORDER_ID_EXAMPLE)).
                withRel("getUserOrderById");
        Link getMostUsedTagLink = linkTo(methodOn(UserController.class).getUserMostUsedTag()).withRel("getMostUsedTag");
        Link selfLink = linkTo(UserController.class).withSelfRel();
        return EntityModel.of(currentUser, getUsersLink, getByIdLink, getOrdersLink, getOrderByIdLink, getMostUsedTagLink,
                selfLink);
    }
}
