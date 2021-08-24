package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class TagDaoImpl implements TagDao {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Tag add(Tag tag) {
        String SQL = "INSERT INTO tag (name) VALUES (?)";
        jdbcTemplate.update(SQL, tag.getName());
        return tag;
    }

    @Override
    public Tag get(Long id) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List<Tag> getAll() {
        return null;
    }

}
