package com.epam.esm.repository.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.repository.impl.query.NewSqlQuery.SELECT_ALL_ORDERS;
import static com.epam.esm.repository.impl.query.NewSqlQuery.SELECT_ALL_ORDERS_BY_USER_ID;

@Repository
public class OrderRepositoryImpl extends AbstractRepository<Order> implements OrderRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    protected OrderRepositoryImpl(EntityManager entityManager) {
        super(Order.class);
        this.entityManager = entityManager;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Optional<List<Order>> getAll(Map<String, String> searchParams, Integer page, Integer size) {
        return Optional.ofNullable(entityManager.createQuery(SELECT_ALL_ORDERS, Order.class).setFirstResult((page - 1) * size).
                setMaxResults(size).getResultList());
    }

    @Override
    public List<Order> findAllByUserId(long userId, Integer page, Integer size) {
        TypedQuery<Order> query = entityManager.createQuery(SELECT_ALL_ORDERS_BY_USER_ID, Order.class);
        return query.setParameter("userId", userId).setFirstResult((page - 1) * size).
                setMaxResults(size).getResultList();
    }


}
