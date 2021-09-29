package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserWithoutOrdersDto;
import com.epam.esm.service.UserService;
import com.epam.esm.util.UserLinkCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User controller with GET and POST methods
 *
 * @author Lukyanau I.M.
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

  private final UserService userService;
  private final UserLinkCreator<UserDto> userLinkCreator;
  private final UserLinkCreator<UserWithoutOrdersDto> userWithoutOrdersLinkCreator;

  /**
   * method founds all users with pagination
   *
   * @param page is number of page
   * @param size is count of users on page
   * @return collection of users with links
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public CollectionModel<UserWithoutOrdersDto> getUsers(
      @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
      @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
    return userWithoutOrdersLinkCreator.addLinkToUsers(userService.getUsers(page, size));
  }

  /**
   * method founds user by id
   *
   * @param id is user id
   * @return founded user with links
   */
  @GetMapping(value = "{id}")
  @ResponseStatus(HttpStatus.OK)
  public EntityModel<UserWithoutOrdersDto> getUserById(@PathVariable("id") long id) {
    return userWithoutOrdersLinkCreator.addLinkToUserAndReturn(userService.getUserById(id));
  }

  /**
   * method founds all user orders with pagination
   *
   * @param id is user id
   * @param page is page number
   * @param size is count of orders on page
   * @return collection of orders
   */
  @GetMapping(value = "/{id}/orders")
  @ResponseStatus(HttpStatus.OK)
  public CollectionModel<OrderDto> getUserOrders(
      @PathVariable("id") long id,
      @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
      @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
    return userLinkCreator.addLinkToOrders(userService.getUserOrders(id, page, size));
  }

  /**
   * method founds user order
   *
   * @param userId is user id
   * @param orderId is order id
   * @return user order
   */
  @GetMapping(value = "/{userId}/order/{orderId}")
  @ResponseStatus(HttpStatus.OK)
  public EntityModel<OrderDto> getUserOrderById(
      @PathVariable("userId") long userId, @PathVariable("orderId") long orderId) {
    return userLinkCreator.addLinkToOrderAndReturn(userService.getUserOrderById(userId, orderId));
  }

  /**
   * method founds most used tag of user with the highest cost of all orders
   *
   * @return most used tag
   */
  @GetMapping(value = "/most_used_tag")
  @ResponseStatus(HttpStatus.OK)
  public EntityModel<TagDto> getUserMostUsedTag() {
    return userLinkCreator.addLinkToTag(userService.getUserMostUsedTag());
  }

  /**
   * method make user order
   *
   * @param userId is user id
   * @param certificateIds is certificates ids
   * @return user with orders
   */
  @PostMapping(value = "{userId}/make_order")
  @ResponseStatus(HttpStatus.CREATED)
  public EntityModel<UserDto> makeOrder(
      @PathVariable("userId") long userId, @RequestParam List<Long> certificateIds) {
    return userLinkCreator.addLinkToUserAndReturn(userService.makeOrder(userId, certificateIds));
  }
}
