package com.epam.esm.repositoty;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseRepository<T> {
    T add(T t);
    T get(long id);
    boolean delete(long id);
    List<T> getAll();
}
