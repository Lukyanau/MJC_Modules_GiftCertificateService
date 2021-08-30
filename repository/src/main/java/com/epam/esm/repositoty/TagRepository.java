package com.epam.esm.repositoty;

import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends BaseRepository<Tag>{
    List<Long> getTagsIdsByCertificateId(long id);
    Long getTagId(String tagName);
}
