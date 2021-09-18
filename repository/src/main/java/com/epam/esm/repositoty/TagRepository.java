package com.epam.esm.repositoty;

import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends BaseRepository<Tag>{
    Optional<Tag> getByName(String tagName);
    Long getTagId(String tagName);
    Long checkUsedTags(long id);
}
