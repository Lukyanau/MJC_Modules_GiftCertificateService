package com.epam.esm.repository;

import com.epam.esm.entity.Order;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order>{

  List<Order> findAllByUserId(long userId, Integer page, Integer size);
}
