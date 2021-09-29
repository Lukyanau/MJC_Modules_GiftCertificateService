package com.epam.esm.repository.impl;

import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.repository.impl.query.SqlQuery.FIND_MOST_USED_TAG_ID;
import static com.epam.esm.repository.impl.query.SqlQuery.SELECT_ALL_USERS;

@Repository
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

  @PersistenceContext private final EntityManager entityManager;

  protected UserRepositoryImpl(EntityManager entityManager) {
    super(User.class);
    this.entityManager = entityManager;
  }

  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public Optional<List<User>> getAll(Map<String, String> searchParams, Integer page, Integer size) {
    return Optional.ofNullable(
        entityManager
            .createQuery(SELECT_ALL_USERS, User.class)
            .setFirstResult((page - 1) * size)
            .setMaxResults(size)
            .getResultList());
  }

  @Override
  public Long findMostUsedTagId() {
    return Long.valueOf(
        String.valueOf(entityManager.createNativeQuery(FIND_MOST_USED_TAG_ID).getSingleResult()));
  }
}
