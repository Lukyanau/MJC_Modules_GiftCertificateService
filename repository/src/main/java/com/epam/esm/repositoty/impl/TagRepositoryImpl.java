package com.epam.esm.repositoty.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repositoty.impl.query.SqlQuery;
import com.epam.esm.repositoty.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Tag add(Tag tag) {
        jdbcTemplate.update(SqlQuery.ADD_TAG, tag.getName().trim().toLowerCase());
        return tag;
    }

    @Override
    public Optional<Tag> getById(long id) {
        return jdbcTemplate.query(SqlQuery.GET_TAG_BY_ID, new BeanPropertyRowMapper<>(Tag.class), id)
                .stream().findAny();
    }

    @Override
    public Optional<Tag> getByName(String name) {
        return jdbcTemplate.query(SqlQuery.GET_TAG_BY_NAME, new BeanPropertyRowMapper<>(Tag.class),
                        name.trim().toLowerCase()).stream().findAny();
    }

    @Override
    public Long getTagId(String tagName) {
        return jdbcTemplate.queryForObject(SqlQuery.GET_TAG_ID, Long.class, tagName);
    }

    @Override
    public Optional<List<Tag>> getAll(Map<String,String> searchParams) {
        return Optional.of(jdbcTemplate.query(SqlQuery.GET_ALL_TAGS, new BeanPropertyRowMapper<>(Tag.class)));
    }

    @Override
    public Optional<List<Long>> getTagsIdsByCertificateId(long id) {
        return Optional.of(jdbcTemplate.queryForList(SqlQuery.GET_TAGS_BY_CERTIFICATE_ID, Long.class, id));
    }

    @Override
    public int delete(long id) {
        return jdbcTemplate.update(SqlQuery.DELETE_TAG_BY_ID, id);
    }

    @Override
    public Long checkUsedTags(long id) {
        return jdbcTemplate.queryForObject(SqlQuery.CHECK_USED_TAGS, Long.class, id);
    }

    @Override
    public void deleteFromCrossTable(long tagId, long certificateId) {
        jdbcTemplate.update(SqlQuery.DELETE_FROM_CROSS_TABLE, tagId, certificateId);
    }
}
