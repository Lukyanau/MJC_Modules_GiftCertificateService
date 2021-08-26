package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import javax.sql.DataSource;
import java.util.List;

public interface TagDao extends BaseDao<Tag>{
    List<Tag> getTagsByCertificateName(String certificateName);
}
