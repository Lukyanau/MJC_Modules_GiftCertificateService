package com.epam.esm.service.impl;

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
import com.epam.esm.utils.OrderCreator;
import com.epam.esm.validator.BaseValidator;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.exception.exception_code.ExceptionDescription.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepositoryImpl userRepository;
    private final UserMapper userMapper;
    private final OrderRepositoryImpl orderRepository;
    private final OrderCreator orderCreator;
    private final OrderMapper orderMapper;
    private final CertificateRepositoryImpl certificateRepository;
    private final TagRepositoryImpl tagRepository;
    private final TagMapper tagMapper;
    private final BaseValidator baseValidator;
    private final Gson gson;

    /**
     * method for searching all users
     *
     * @return list of founded users
     */
    @Override
    public List<UserWithoutOrdersDto> getUsers(Integer page, Integer size) {
        baseValidator.checkPaginationParams(page, size);
        Optional<List<User>> allUsers = userRepository.getAll(Collections.emptyMap(), page, size);
        if (allUsers.isEmpty()) {
            throw new ServiceException(NOT_FOUND_USERS);
        }
        List<User> currentUsers = allUsers.get();
        return currentUsers.stream().map(userMapper::convertToDtoWithoutOrders).collect(Collectors.toList());
    }

    /**
     * method for searching user by id
     *
     * @param id is user id
     * @return founded user
     */
    @Override
    public UserWithoutOrdersDto getUserById(long id) {
        baseValidator.checkId(id);
        Optional<User> userOptional = userRepository.getById(id);
        if (userOptional.isEmpty()) {
            throw new ServiceException(USER_WITH_ID_NOT_FOUND, String.valueOf(id));
        }
        User currentUser = userOptional.get();
        return userMapper.convertToDtoWithoutOrders(currentUser);
    }

    /**
     * method for making user order
     *
     * @param userId         is user id
     * @param certificateIds is certificate id
     * @return user with order
     */
    @Override
    public UserDto makeOrder(long userId, List<Long> certificateIds) {
        baseValidator.checkId(userId);
        if (certificateIds.isEmpty()) {
            throw new ServiceException(NO_CERTIFICATE_IDS_FOR_ORDER);
        }
        certificateIds.forEach(baseValidator::checkId);
        Optional<User> userOptional = userRepository.getById(userId);
        if (userOptional.isEmpty()) {
            throw new ServiceException(USER_WITH_ID_NOT_FOUND, String.valueOf(userId));
        }
        List<GiftCertificate> currentCertificates = new ArrayList<>();
        certificateIds.forEach(id -> {
            Optional<GiftCertificate> certificateOptional = certificateRepository.getById(id);
            if (certificateOptional.isEmpty()) {
                throw new ServiceException(CERTIFICATE_WITH_ID_NOT_FOUND, String.valueOf(id));
            }
            currentCertificates.add(certificateOptional.get());
        });
        String certificatesInJson = gson.toJson(currentCertificates);
        orderRepository.add(orderCreator.createOrder(userOptional.get(), currentCertificates, certificatesInJson));
        User userWithAddedOrder = userRepository.getById(userId).get();
        return userMapper.convertToDto(userWithAddedOrder);
    }

    /**
     * method for searching all user orders
     *
     * @param userId is user id
     * @return list of user orders
     */
    @Override
    public List<OrderDto> getUserOrders(long userId, Integer page, Integer size) {
        baseValidator.checkId(userId);
        baseValidator.checkPaginationParams(page, size);
        Optional<User> userOptional = userRepository.getById(userId);
        if (userOptional.isEmpty()) {
            throw new ServiceException(USER_WITH_ID_NOT_FOUND, String.valueOf(userId));
        }
        List<Order> userOrders = orderRepository.findAllByUserId(userId, page, size);
        return userOrders.stream().map(orderMapper::convertToDto).collect(Collectors.toList());
    }

    /**
     * method for searching user order by id
     *
     * @param userId  is user id
     * @param orderId is order id
     * @return user order
     */
    @Override
    public OrderDto getUserOrderById(long userId, long orderId) {
        baseValidator.checkId(userId);
        baseValidator.checkId(orderId);
        Optional<User> userOptional = userRepository.getById(userId);
        if (userOptional.isEmpty()) {
            throw new ServiceException(USER_WITH_ID_NOT_FOUND, String.valueOf(userId));
        }
        Optional<Order> userOrder = userOptional.get().getOrders().stream().
                filter(order -> order.getId() == orderId).findFirst();
        if (userOrder.isEmpty()) {
            throw new ServiceException(ORDER_WITH_ID_NOT_FOUND, String.valueOf(orderId));
        }
        return orderMapper.convertToDto(userOrder.get());
    }

    /**
     * method found most used tag of user with the highest cost of all orders
     *
     * @return founded tag
     */
    @Override
    public TagDto getUserMostUsedTag() {
        Long mostUsedTagId = userRepository.findMostUsedTagId();
        Optional<Tag> mostUsedTag = tagRepository.getById(mostUsedTagId);
        if (mostUsedTag.isEmpty()) {
            throw new ServiceException(TAG_WITH_ID_NOT_FOUND, String.valueOf(mostUsedTagId));
        }
        return tagMapper.convertToDto(mostUsedTag.get());
    }

}
