package com.epam.esm.repositoty.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repositoty.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.repositoty.impl.query.NewSqlQuery.*;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private final EntityManager entityManager;

    @Override
    @Transactional
    public Tag add(Tag tag) {
        entityManager.merge(tag);
        return tag;
    }

    @Override
    public Optional<Tag> getById(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));

    }

    @Override
    public Optional<Tag> getByName(String name) {
        TypedQuery<Tag> query = entityManager.createQuery(GET_TAG_BY_NAME, Tag.class);
        return query.setParameter("name", name).getResultList().stream().findFirst();
    }

    @Override
    public Long getTagId(String tagName) {
        TypedQuery<Long> query = entityManager.createQuery(GET_TAG_ID_BY_NAME, Long.class);
        return query.setParameter("name", tagName).getSingleResult();
    }

    @Override
    public Optional<List<Tag>> getAll(Map<String, String> searchParams) {
        return Optional.ofNullable(entityManager.createQuery(SELECT_ALL_TAGS, Tag.class).getResultList());
    }

    @Override
    @Transactional
    public int delete(long id) {
        Query query = entityManager.createQuery(DELETE_TAG_BY_ID);
        return query.setParameter("id", id).executeUpdate();
    }

    @Override
    @Transactional
    public Long checkUsedTags(long id) {
        Query query = entityManager.createNativeQuery(CHECK_USED_TAGS);
        query.setParameter("id", id);
        return Long.valueOf(String.valueOf(query.getResultList().get(0)));
    }
}
