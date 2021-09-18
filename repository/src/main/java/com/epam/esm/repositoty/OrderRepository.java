package com.epam.esm.repositoty;

import com.epam.esm.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<Order>{

    List<Order> findAllByUserId(long userId);
}
