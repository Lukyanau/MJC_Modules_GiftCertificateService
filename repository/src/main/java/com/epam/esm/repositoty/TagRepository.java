package com.epam.esm.repositoty;

import com.epam.esm.entity.Tag;

import java.util.Optional;

public interface TagRepository extends BaseRepository<Tag>{
    Optional<Tag> getByName(String tagName);
    Long getTagId(String tagName);
    Long checkUsedTags(long id);
}
