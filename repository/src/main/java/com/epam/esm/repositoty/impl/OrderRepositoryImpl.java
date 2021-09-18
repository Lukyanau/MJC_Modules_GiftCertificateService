package com.epam.esm.repositoty.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.repositoty.OrderRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.repositoty.impl.query.NewSqlQuery.*;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<Order>> getAll(Map<String, String> searchParams) {
        return Optional.ofNullable(entityManager.createQuery(SELECT_ALL_ORDERS, Order.class).getResultList());
    }


    @Override
    public List<Order> findAllByUserId(long userId) {
        Query query = entityManager.createQuery(SELECT_ALL_ORDERS_BY_USER_ID);
        return query.setParameter("userId", userId).getResultList();

    }

    @Override
    @Transactional
    public Order add(Order order) {
        entityManager.merge(order);
        return order;
    }

    @Override
    public Optional<Order> getById(long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    //Unused
    public int delete(long id) {
        return 0;
    }

}
