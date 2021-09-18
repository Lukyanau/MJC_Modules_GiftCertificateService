package com.epam.esm.utils;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderCreator {

    private OrderCreator() {

    }

    public Order createOrder(User user, GiftCertificate certificate) {
        return new Order(user.getId(), certificate.getId(), certificate.getPrice(), LocalDateTime.now());
    }
}
