package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.repositoty.CertificateRepository;
import com.epam.esm.repositoty.OrderRepository;
import com.epam.esm.repositoty.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.utils.OrderCreator;
import com.epam.esm.validator.BaseValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.exception.exception_code.ExceptionDescription.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final OrderRepository orderRepository;
    private final CertificateRepository certificateRepository;
    private final BaseValidator baseValidator;
    private final OrderCreator orderCreator;
    private final OrderMapper orderMapper;

    @Override
    public List<UserDto> getUsers() {
        Optional<List<User>> allUsers = Optional.ofNullable(userRepository.findAll());
        if (allUsers.isEmpty()) {
            throw new ServiceException(NOT_FOUND_USERS);
        }
        List<User> currentUsers = allUsers.get();
        return currentUsers.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(long id) {
        baseValidator.checkId(id);
        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(id));
        if (userOptional.isEmpty()) {
            throw new ServiceException(USER_WITH_ID_NOT_FOUND, String.valueOf(id));
        }
        User currentUser = userOptional.get();
        return userMapper.convertToDto(currentUser);
    }

    @Override
    public UserDto makeOrder(long userId, long certificateId) {
        baseValidator.checkId(userId);
        baseValidator.checkId(certificateId);
        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(userId));
        if (userOptional.isEmpty()) {
            throw new ServiceException(USER_WITH_ID_NOT_FOUND, String.valueOf(userId));
        }
        Optional<GiftCertificate> certificateOptional = certificateRepository.getById(certificateId);
        if (certificateOptional.isEmpty()) {
            throw new ServiceException(CERTIFICATE_WITH_ID_NOT_FOUND, String.valueOf(certificateId));
        }
        User currentUser = userOptional.get();
        checkBalanceAndPrice(currentUser.getBalance(), certificateOptional.get().getPrice());
        orderRepository.add(orderCreator.createOrder(currentUser, certificateOptional.get()));
        currentUser.setBalance(currentUser.getBalance().subtract(certificateOptional.get().getPrice()));
        userRepository.updateBalance(userId, currentUser.getBalance());
        User userWithAddedOrder = userRepository.findById(userId);
        return userMapper.convertToDto(userWithAddedOrder);
    }

    @Override
    public List<OrderDto> getUserOrders(long userId) {
        baseValidator.checkId(userId);
        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(userId));
        if (userOptional.isEmpty()) {
            throw new ServiceException(USER_WITH_ID_NOT_FOUND, String.valueOf(userId));
        }
        List<Order> userOrders = orderRepository.findAllByUserId(userId);
        return userOrders.stream().map(orderMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto getUserOrderById(long userId, long orderId) {
        baseValidator.checkId(userId);
        baseValidator.checkId(orderId);
        Optional<User> userOptional = Optional.ofNullable(userRepository.findById(userId));
        if (userOptional.isEmpty()) {
            throw new ServiceException(USER_WITH_ID_NOT_FOUND, String.valueOf(userId));
        }
        Optional<Order> userOrder = userOptional.get().getOrders().stream().
                filter(order -> order.getId() == orderId).findFirst();
        if(userOrder.isEmpty()){
            throw new ServiceException(ORDER_WITH_ID_NOT_FOUND, String.valueOf(orderId));
        }
        return orderMapper.convertToDto(userOrder.get());
    }

    private void checkBalanceAndPrice(BigDecimal balance, BigDecimal price) {
        if (balance.compareTo(price) < 0) {
            throw new ServiceException(NOT_ENOUGH_MONEY, String.valueOf(price));
        }
    }
}
