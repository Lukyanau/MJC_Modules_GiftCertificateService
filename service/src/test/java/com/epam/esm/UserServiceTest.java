package com.epam.esm;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserWithoutOrdersDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.repository.impl.OrderRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.repository.impl.UserRepositoryImpl;
import com.epam.esm.service.UserService;
import com.epam.esm.service.impl.UserServiceImpl;
import com.epam.esm.utils.OrderCreator;
import com.epam.esm.validator.BaseValidator;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserServiceTest.class)
public class UserServiceTest {

    private UserRepositoryImpl userRepository;
    private UserMapper userMapper;
    private OrderRepositoryImpl orderRepository;
    private OrderCreator orderCreator;
    private OrderMapper orderMapper;
    private CertificateRepositoryImpl certificateRepository;
    private TagRepositoryImpl tagRepository;
    private TagMapper tagMapper;
    private BaseValidator baseValidator;
    private Gson gson;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepositoryImpl.class);
        orderRepository = mock(OrderRepositoryImpl.class);
        certificateRepository = mock(CertificateRepositoryImpl.class);
        tagRepository = mock(TagRepositoryImpl.class);
        orderCreator = mock(OrderCreator.class);
        baseValidator = new BaseValidator();
        gson = new Gson();
        userMapper = mock(UserMapper.class);
        orderMapper = mock(OrderMapper.class);
        tagMapper = mock(TagMapper.class);
        userService = new UserServiceImpl(userRepository, userMapper, orderRepository, orderCreator, orderMapper,
                certificateRepository, tagRepository, tagMapper, baseValidator, gson);

    }

    @AfterEach
    void tearDown() {
        userRepository = null;
        orderRepository = null;
        certificateRepository = null;
        tagRepository = null;
        orderCreator = null;
        baseValidator = null;
        gson = null;
        userMapper = null;
        orderMapper = null;
        tagMapper = null;
    }

    @Test
    void getUsersCorrectDataShouldReturnListOfUsers() {
        User user1 = new User(1L, "firstName", "secondName", Collections.emptyList());
        User user2 = new User(2L, "firstName", "secondName", Collections.emptyList());
        User user3 = new User(3L, "firstName", "secondName", Collections.emptyList());
        when(userRepository.getAll(Collections.emptyMap(), 1, 1)).thenReturn(Optional.of(List.of(user1, user2, user3)));

        int expectedResultSize = 3;

        int actualResultSize = userService.getUsers(1, 1).size();
        assertEquals(expectedResultSize, actualResultSize);
    }

    @Test
    void getDataNoUsersShouldThrowException() {
        when(userRepository.getAll(Collections.emptyMap(), 1, 1)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> userService.getUsers(1, 1));
    }

    @Test
    void getDataIncorrectPaginationParamsShouldThrowException() {
        assertThrows(ServiceException.class, () -> userService.getUsers(0, 0));
    }

    @Test
    void getExistUserByIdShouldReturnCorrectUser() {
        User user1 = new User(1L, "firstName", "secondName", Collections.emptyList());
        UserWithoutOrdersDto expectedUser = new UserWithoutOrdersDto(1L, "firstName", "secondName");
        when(userRepository.getById(1L)).thenReturn(Optional.of(user1));
        when(userMapper.convertToDtoWithoutOrders(any())).thenReturn(expectedUser);
        UserWithoutOrdersDto actualUser = userService.getUserById(1L);

        assertEquals(actualUser, expectedUser);
    }

    @Test
    void getNonExistUserByIdShouldThrowException() {
        when(userRepository.getById(1)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> userService.getUserById(1));
    }

    @Test
    void getUserByIncorrectIdShouldThrowException() {
        assertThrows(ServiceException.class, () -> userService.getUserById(0));
    }

    @Test
    void makeOrderShouldReturnCorrectUser() {
        List<Long> certificateIds = List.of(1L, 2L, 3L, 4L);
        GiftCertificate giftCertificate = new GiftCertificate();
        List<Order> userOrders = List.of(new Order(11L, "11userName", "dsa",
                BigDecimal.valueOf(1000), LocalDateTime.now()), new Order(12L, "12userName", "dsa",
                BigDecimal.valueOf(1500), LocalDateTime.now()), new Order(13L, "13userName", "dsa",
                BigDecimal.valueOf(2000), LocalDateTime.now()));
        List<OrderDto> userOrdersDto = List.of(new OrderDto(11L, "11userName", Collections.emptyList(),
                BigDecimal.valueOf(1000), LocalDateTime.now()), new OrderDto(12L, "12userName", Collections.emptyList(),
                BigDecimal.valueOf(1500), LocalDateTime.now()), new OrderDto(13L, "13userName", Collections.emptyList(),
                BigDecimal.valueOf(2000), LocalDateTime.now()));
        User user1 = new User(1L, "firstName", "secondName", userOrders);
        UserDto expectedUser = new UserDto(1L, "firstName", "secondName", userOrdersDto);
        when(userRepository.getById(1)).thenReturn(Optional.of(user1));
        when(userMapper.convertToDto(user1)).thenReturn(expectedUser);
        when(certificateRepository.getById(anyLong())).thenReturn(Optional.of(giftCertificate));
        when(orderCreator.createOrder(any(), any(), any())).thenReturn(new Order());
        when(orderRepository.add(any())).thenReturn(new Order());

        UserDto actualUser = userService.makeOrder(1L, certificateIds);

        assertEquals(actualUser, expectedUser);
    }

    @Test
    void makeOrderIncorrectIdsShouldThrowException() {
        List<Long> certificateIds = List.of(-1L, -2L, -3L, -4L);
        assertThrows(ServiceException.class, () -> userService.makeOrder(1L, certificateIds));

    }

    @Test
    void getUserOrdersShouldReturnCorrectList() {
        List<Order> userOrders = List.of(new Order(11L, "11userName", "dsa",
                BigDecimal.valueOf(1000), LocalDateTime.now()), new Order(12L, "12userName", "dsa",
                BigDecimal.valueOf(1500), LocalDateTime.now()), new Order(13L, "13userName", "dsa",
                BigDecimal.valueOf(2000), LocalDateTime.now()));
        OrderDto newOrder = new OrderDto(11L, "11userName", Collections.emptyList(),
                BigDecimal.valueOf(1000), LocalDateTime.now());
        User user1 = new User(1L, "firstName", "secondName", userOrders);
        int expectedSize = 3;
        when(userRepository.getById(1L)).thenReturn(Optional.of(user1));
        when(orderRepository.findAllByUserId(1L, 1, 1)).thenReturn(userOrders);
        when(orderMapper.convertToDto(any())).thenReturn(newOrder);

        int actualListSize = userService.getUserOrders(1L, 1, 1).size();

        assertEquals(expectedSize, actualListSize);
    }

    @Test
    void getUserOrdersIncorrectUserIdShouldThrowException() {
        assertThrows(ServiceException.class, () -> userService.getUserOrders(-1L, 1, 1));
    }

    @Test
    void getUserOrdersIncorrectPaginationParamsShouldThrowException() {
        assertThrows(ServiceException.class, () -> userService.getUserOrders(1L, -11, -11));
    }

    @Test
    void getUserOrderByIdShouldReturnCorrectOrder() {
        List<Order> userOrders = List.of(new Order(11L, 11L, "11userName", "dsa",
                BigDecimal.valueOf(1000), LocalDateTime.now()), new Order(12L, 12L, "12userName", "dsa",
                BigDecimal.valueOf(1500), LocalDateTime.now()), new Order(13L, 13L, "13userName", "dsa",
                BigDecimal.valueOf(2000), LocalDateTime.now()));
        OrderDto newOrder = new OrderDto(11L, "11userName", Collections.emptyList(),
                BigDecimal.valueOf(1000), LocalDateTime.now());
        User user1 = new User(1L, "firstName", "secondName", userOrders);
        when(userRepository.getById(1L)).thenReturn(Optional.of(user1));
        when(orderMapper.convertToDto(any())).thenReturn(newOrder);

        OrderDto actualOrder = userService.getUserOrderById(1, 11);

        assertEquals(newOrder, actualOrder);

    }

    @Test
    void getUserOrderByIdIncorrectUserIdShouldThrowException() {
        assertThrows(ServiceException.class, () -> userService.getUserOrderById(-1L, 11));
    }

    @Test
    void getUserOrderByIdIncorrectOrderIdShouldThrowException() {
        assertThrows(ServiceException.class, () -> userService.getUserOrderById(1L, -11));
    }

    @Test
    void getMostUserTagShouldReturnCorrectTag() {
        Tag mostUsedTag = new Tag(1L, "felix");
        TagDto mappedTag = new TagDto(1L, "felix");
        when(userRepository.findMostUsedTagId()).thenReturn(1L);
        when(tagRepository.getById(1L)).thenReturn(Optional.of(mostUsedTag));
        when(tagMapper.convertToDto(any())).thenReturn(mappedTag);

        TagDto actualTag = userService.getUserMostUsedTag();

        assertEquals(actualTag, mappedTag);
    }

}
