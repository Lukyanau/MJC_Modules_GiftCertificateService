package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.Optional;

public interface TagRepository {

    Optional<Tag> getByName(String tagName);
    Long getTagId(String tagName);
    Long checkUsedTags(long id);
}
