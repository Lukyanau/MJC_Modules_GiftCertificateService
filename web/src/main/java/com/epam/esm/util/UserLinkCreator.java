package com.epam.esm.util;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserWithoutOrdersDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.epam.esm.utils.GlobalCustomConstants.PAGE_DEFAULT;
import static com.epam.esm.utils.GlobalCustomConstants.SIZE_DEFAULT;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserLinkCreator<T extends UserWithoutOrdersDto> {

  private static final long USER_ID_EXAMPLE = 1;
  private static final long ORDER_ID_EXAMPLE = USER_ID_EXAMPLE;

  public CollectionModel<T> addLinkToUsers(List<T> allUsers) {
    allUsers.forEach(this::addLinkToUser);
    Link selfLink = linkTo(UserController.class).withSelfRel();
    return CollectionModel.of(allUsers, selfLink);
  }

  public EntityModel<T> addLinkToUserAndReturn(T user) {
    addLinkToUser(user);
    return EntityModel.of(user);
  }

  private void addLinkToUser(T user) {
    Link getUsersLink =
        linkTo(methodOn(UserController.class).getUsers(PAGE_DEFAULT, SIZE_DEFAULT))
            .withRel("get all");
    Link getUserByIdLink =
        linkTo(methodOn(UserController.class).getUserById(user.getId())).withRel("get by id");
    Link getOrdersLink =
        linkTo(
                methodOn(UserController.class)
                    .getUserOrders(user.getId(), PAGE_DEFAULT, SIZE_DEFAULT))
            .withRel("get user orders");
    Link getOrderByIdLink =
        linkTo(methodOn(UserController.class).getUserOrderById(user.getId(), ORDER_ID_EXAMPLE))
            .withRel("get order by id");
    Link getMostUsedTagLink =
        linkTo(methodOn(UserController.class).getUserMostUsedTag()).withRel("get most used tag");
    Link makeOrderLink =
        linkTo(methodOn(UserController.class).makeOrder(user.getId(), Collections.emptyList()))
            .withRel("make order");
    user.add(
        getUsersLink,
        getUserByIdLink,
        getOrdersLink,
        getOrderByIdLink,
        getMostUsedTagLink,
        makeOrderLink);
  }

  public CollectionModel<OrderDto> addLinkToOrders(List<OrderDto> orders) {
    orders.forEach(this::addLinkToOrder);
    Link selfLink = linkTo(UserController.class).withSelfRel();
    return CollectionModel.of(orders, selfLink);
  }

  public EntityModel<OrderDto> addLinkToOrderAndReturn(OrderDto order) {
    addLinkToOrder(order);
    return EntityModel.of(order);
  }

  private void addLinkToOrder(OrderDto order) {
    Link getUsersLink =
        linkTo(methodOn(UserController.class).getUsers(PAGE_DEFAULT, SIZE_DEFAULT))
            .withRel("get all users");
    Link getUserByIdLink =
        linkTo(methodOn(UserController.class).getUserById(USER_ID_EXAMPLE))
            .withRel("get user by id");
    Link getOrdersLink =
        linkTo(
                methodOn(UserController.class)
                    .getUserOrders(USER_ID_EXAMPLE, PAGE_DEFAULT, SIZE_DEFAULT))
            .withRel("get user orders");
    Link getOrderByIdLink =
        linkTo(methodOn(UserController.class).getUserOrderById(USER_ID_EXAMPLE, ORDER_ID_EXAMPLE))
            .withRel("get order by id");
    Link getMostUsedTagLink =
        linkTo(methodOn(UserController.class).getUserMostUsedTag()).withRel("get most used tag");
    Link makeOrderLink =
        linkTo(methodOn(UserController.class).makeOrder(USER_ID_EXAMPLE, Collections.emptyList()))
            .withRel("make order");
    order.add(
        getUsersLink,
        getUserByIdLink,
        getOrdersLink,
        getOrderByIdLink,
        getMostUsedTagLink,
        makeOrderLink);
  }

  public EntityModel<TagDto> addLinkToTag(TagDto tag) {
    Link getUsersLink =
        linkTo(methodOn(UserController.class).getUsers(PAGE_DEFAULT, SIZE_DEFAULT))
            .withRel("get all users");
    Link getUserByIdLink =
        linkTo(methodOn(UserController.class).getUserById(USER_ID_EXAMPLE))
            .withRel("get user by id");
    Link getOrdersLink =
        linkTo(
                methodOn(UserController.class)
                    .getUserOrders(USER_ID_EXAMPLE, PAGE_DEFAULT, SIZE_DEFAULT))
            .withRel("get user orders");
    Link getOrderByIdLink =
        linkTo(methodOn(UserController.class).getUserOrderById(USER_ID_EXAMPLE, ORDER_ID_EXAMPLE))
            .withRel("get order by id");
    Link makeOrderLink =
        linkTo(methodOn(UserController.class).makeOrder(USER_ID_EXAMPLE, Collections.emptyList()))
            .withRel("make order");
    Link getMostUsedTagLink =
        linkTo(methodOn(UserController.class).getUserMostUsedTag()).withRel("get most used tag");
    return EntityModel.of(
        tag,
        getUsersLink,
        getUserByIdLink,
        getOrdersLink,
        getOrderByIdLink,
        getMostUsedTagLink,
        makeOrderLink);
  }
}
