package com.epam.esm.dao;

import java.util.List;

public interface BaseDao<T> {
    T add(T t);
    T getById(long id);
    T getByName(String name);
    boolean delete(long id);
    List<T> getAll();
}
