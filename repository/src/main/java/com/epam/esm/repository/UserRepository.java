package com.epam.esm.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    Long findMostUsedTagId();
}
