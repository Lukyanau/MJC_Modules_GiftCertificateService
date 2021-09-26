package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository {

    List<Order> findAllByUserId(long userId, Integer page, Integer size);
}
