package com.epam.esm.repository.impl;

import com.epam.esm.repository.BaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public abstract class AbstractRepository<T> implements BaseRepository<T> {

  private final Class<T> type;

  protected AbstractRepository(Class<T> type) {
    this.type = type;
  }

  protected abstract EntityManager getEntityManager();

  @Transactional
  public T add(T t) {
    getEntityManager().merge(t);
    return t;
  }

  public Optional<T> getById(long id) {
    return Optional.ofNullable(getEntityManager().find(type, id));
  }

  @Transactional
  public boolean delete(long id) {
    T abstractT = getEntityManager().find(type, id);
    if (abstractT != null) {
      getEntityManager().remove(abstractT);
      return true;
    }
    return false;
  }

  public abstract Optional<List<T>> getAll(
      Map<String, String> searchParams, Integer page, Integer size);
}
