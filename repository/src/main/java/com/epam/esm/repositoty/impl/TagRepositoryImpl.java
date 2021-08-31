package com.epam.esm.repositoty.impl;

import com.epam.esm.repositoty.SqlQuery;
import com.epam.esm.entity.Tag;
import com.epam.esm.repositoty.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tag add(Tag tag) {
        jdbcTemplate.update(SqlQuery.ADD_TAG, tag.getName());
        return tag;
    }

    @Override
    public Tag getById(long id) {
        return jdbcTemplate.query(SqlQuery.GET_TAG_BY_ID, new BeanPropertyRowMapper<>(Tag.class), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public Tag getByName(String name) {
        return jdbcTemplate.query(SqlQuery.GET_TAG_BY_NAME, new BeanPropertyRowMapper<>(Tag.class), name)
                .stream().findAny().orElse(null);
    }

    @Override
    public Long getTagId(String tagName) {
        return jdbcTemplate.queryForObject(SqlQuery.GET_TAG_ID, Long.class, tagName);
    }

    @Override
    public List<Tag> getAll() {
        return jdbcTemplate.query(SqlQuery.GET_ALL_TAGS, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public List<Long> getTagsIdsByCertificateId(long id) {
        return jdbcTemplate.queryForList(SqlQuery.GET_TAGS_BY_CERTIFICATE_ID, Long.class, id);
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update(SqlQuery.DELETE_TAG_BY_ID, id) > 0;
    }

}
