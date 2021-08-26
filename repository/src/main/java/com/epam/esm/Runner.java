package com.epam.esm;

import com.epam.esm.repositoty.impl.TagRepositoryImpl;

public class Runner {
    public static void main(String[] args) {
        TagRepositoryImpl repository = TagRepositoryImpl.getInstance();
        System.out.println(repository.getAll());
    }
}
