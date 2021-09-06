package com.epam.esm.repositoty;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface BaseRepository<T> {
    T add(T t);
    Optional<T> getById(long id);
    int delete(long id);
    Optional<List<T>> getAll(Map<String,String> searchParams);
}
