package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import javax.sql.DataSource;

public interface TagDao extends BaseDao<Tag>{
    public void setDataSource(DataSource dataSource);
}
