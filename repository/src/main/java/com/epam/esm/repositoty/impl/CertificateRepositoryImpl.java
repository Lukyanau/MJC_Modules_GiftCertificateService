package com.epam.esm.repositoty.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repositoty.CertificateRepository;
import com.epam.esm.repositoty.impl.query.SqlQueryCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.repositoty.impl.query.NewSqlQuery.DELETE_CERTIFICATE_BY_ID;
import static com.epam.esm.repositoty.impl.query.NewSqlQuery.GET_CERTIFICATE_ID_BY_NAME;

@Repository
@RequiredArgsConstructor
public class CertificateRepositoryImpl implements CertificateRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @Transactional
    public GiftCertificate add(GiftCertificate giftCertificate) {
        entityManager.merge(giftCertificate);
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> getById(long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public Optional<List<GiftCertificate>> getAll(Map<String, String> searchParams) {
        return Optional.ofNullable(entityManager.createQuery(SqlQueryCreator.createQueryFromSearchParameters(searchParams),
                GiftCertificate.class).getResultList());
    }

    @Override
    public Long getCertificateIdByName(String certificateName) {
        TypedQuery<Long> query = entityManager.createQuery(GET_CERTIFICATE_ID_BY_NAME, Long.class);
        query.setParameter("name", certificateName);
        return query.getResultList().stream().reduce((a, b) -> b).orElse(null);
    }

    @Override
    @Transactional
    public int delete(long id) {
        Query query = entityManager.createQuery(DELETE_CERTIFICATE_BY_ID);
        return query.setParameter("id", id).executeUpdate();
    }

    @Override
    @Transactional
    public void updateCertificate(long id, GiftCertificate giftCertificate) {
        entityManager.merge(giftCertificate);
    }
}
