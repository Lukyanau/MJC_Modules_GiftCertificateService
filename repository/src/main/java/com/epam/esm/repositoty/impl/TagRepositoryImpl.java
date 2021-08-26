package com.epam.esm.repositoty.impl;

import com.epam.esm.configuration.ConnectionConfig;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.repositoty.TagRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepositoryImpl implements TagRepository {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConnectionConfig.class);
    TagDaoImpl tagDao = new TagDaoImpl(applicationContext.getBean(JdbcTemplate.class));

    private static final TagRepositoryImpl instance = new TagRepositoryImpl();

    private TagRepositoryImpl() {
    }

    public static TagRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public Tag add(Tag tag) {
        return tagDao.add(tag);
    }

    @Override
    public Tag getById(long id) {
        return tagDao.getById(id);
    }

    @Override
    public Tag getByName(String name) {
        return tagDao.getByName(name);
    }

    @Override
    public List<Tag> getAll() {
        return tagDao.getAll();
    }

    @Override
    public List<Tag> getTagsByCertificateName(String certificateName) {
        return tagDao.getTagsByCertificateName(certificateName);
    }

    @Override
    public boolean delete(long id) {
        return tagDao.delete(id);
    }

}
