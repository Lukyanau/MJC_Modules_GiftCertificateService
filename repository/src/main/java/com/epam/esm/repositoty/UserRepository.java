package com.epam.esm.repositoty;

import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserRepository{

    List<User> findAll();
    User findById(long id);
    void updateBalance(long userId, BigDecimal newBalance);
}
