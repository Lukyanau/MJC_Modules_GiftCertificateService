package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.impl.query.SqlQueryCreator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.repository.impl.query.NewSqlQuery.*;

@Repository
public class TagRepositoryImpl extends AbstractRepository<Tag> implements TagRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public TagRepositoryImpl(EntityManager entityManager) {
        super(Tag.class);
        this.entityManager = entityManager;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
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
    public Optional<List<Tag>> getAll(Map<String, String> searchParams, Integer page, Integer size) {
        TypedQuery<Tag> query = entityManager.createQuery(SqlQueryCreator.createQueryFromTagSearchParameters(searchParams),
                Tag.class).setFirstResult((page - 1) * size).setMaxResults(size);
        return Optional.ofNullable(query.getResultList());
    }

    @Override
    @Transactional
    public Long checkUsedTags(long id) {
        Query query = entityManager.createNativeQuery(CHECK_USED_TAGS);
        query.setParameter("id", id);
        return Long.valueOf(String.valueOf(query.getSingleResult()));
    }

}
