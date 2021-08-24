package com.epam.esm.dao;

import java.util.List;

public interface BaseDao<T> {
    T add(T t);
    T get(Long id);
    boolean delete(Long id);
    List<T> getAll();
}
