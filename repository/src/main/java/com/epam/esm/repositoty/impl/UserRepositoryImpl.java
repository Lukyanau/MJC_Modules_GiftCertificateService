package com.epam.esm.repositoty.impl;

import com.epam.esm.entity.User;
import com.epam.esm.repositoty.UserRepository;
import com.epam.esm.repositoty.impl.query.NewSqlQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

import static com.epam.esm.repositoty.impl.query.NewSqlQuery.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll() {
        return entityManager.createQuery(SELECT_ALL_USERS, User.class).getResultList();
    }

    @Override
    public User findById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional
    public void updateBalance(long userId, BigDecimal newBalance) {
        Query query = entityManager.createNativeQuery(UPDATE_BALANCE);
        query.setParameter("balance", newBalance);
        query.setParameter("id", userId);
        query.executeUpdate();
    }
}
