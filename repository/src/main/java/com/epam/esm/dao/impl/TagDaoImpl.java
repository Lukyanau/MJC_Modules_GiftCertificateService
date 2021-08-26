package com.epam.esm.dao.impl;

import com.epam.esm.dao.SqlQuery;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class TagDaoImpl implements TagDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tag add(Tag tag) {
        Tag addedTag = getByName(tag.getName());
        if (addedTag == null) {
            jdbcTemplate.update(SqlQuery.ADD_TAG, tag.getName());
            return tag;
        }
        return null;
    }

    @Override
    public Tag getById(long id) {
        return jdbcTemplate.query(SqlQuery.GET_TAG_BY_ID, new Object[]{id}, new BeanPropertyRowMapper<>(Tag.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public Tag getByName(String name) {
        return jdbcTemplate.query(SqlQuery.GET_TAG_BY_NAME, new Object[]{name}, new BeanPropertyRowMapper<>(Tag.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update(SqlQuery.DELETE_TAG_BY_ID, id) > 0;
    }

    @Override
    public List<Tag> getAll() {
        return jdbcTemplate.query(SqlQuery.GET_ALL_TAGS, new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public List<Tag> getTagsByCertificateName(String certificateName) {
        long certificateId = jdbcTemplate.query(SqlQuery.GET_CERTIFICATE_ID, new BeanPropertyRowMapper<>(Long.class))
                .stream().findAny().orElse(0L);
        List<Long> certificateTagIds = jdbcTemplate.query(SqlQuery.GET_TAGS_BY_CERTIFICATE_ID, new Object[]{certificateId},
                new BeanPropertyRowMapper<>(Long.class));
        List<Tag> certificateTags = new ArrayList<>();
        for (Long tagId : certificateTagIds) {
            certificateTags.add(jdbcTemplate.query(SqlQuery.GET_TAG_BY_ID, new Object[]{tagId},
                    new BeanPropertyRowMapper<>(Tag.class)).stream().findAny().orElse(null));
        }
        return certificateTags;
    }
}
