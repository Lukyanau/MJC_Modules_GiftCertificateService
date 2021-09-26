package com.epam.esm.utils;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderCreator {

    public Order createOrder(User user, List<GiftCertificate> certificates, String certificatesInJson) {
        BigDecimal totalCost = certificates.stream().map(GiftCertificate::getPrice).reduce(BigDecimal.ZERO,
                BigDecimal::add);
        return new Order(user.getId(), user.getName(), certificatesInJson, totalCost, LocalDateTime.now());
    }
}
