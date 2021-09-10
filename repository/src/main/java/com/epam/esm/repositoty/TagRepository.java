package com.epam.esm.repositoty;

import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends BaseRepository<Tag>{
    Optional<List<Long>> getTagsIdsByCertificateId(long id);
    Optional<Tag> getByName(String tagName);
    Long getTagId(String tagName);
    Long checkUsedTags(long id);
    void deleteFromCrossTable(long tagId, long certificateId);
}
