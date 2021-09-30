package com.epam.esm.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BaseRepository<T> {

  T add(T t);

  Optional<T> getById(long id);

  boolean delete(long id);

  Optional<List<T>> getAll(Map<String, String> searchParams, Integer page, Integer size);
}
