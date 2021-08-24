package com.epam.esm.repositoty.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repositoty.TagRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepositoryImpl implements TagRepository {
    private static final TagRepositoryImpl instance = new TagRepositoryImpl();

    private TagRepositoryImpl() {
    }

    public static TagRepositoryImpl getInstance(){
        return instance;
    }

    @Override
    public Tag add(Tag tag) {
        return null;
    }

    @Override
    public Tag get(long id) {
        return null;
    }

    @Override
    public List<Tag> getAll() {
        return null;
    }

    @Override
    public List<Tag> getTagsByCertificateName(String certificateName) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }


}
