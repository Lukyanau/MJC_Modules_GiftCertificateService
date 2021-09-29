package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.impl.query.SqlQueryCreator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.repository.impl.query.SqlQuery.GET_CERTIFICATE_ID_BY_NAME;

@Repository
public class CertificateRepositoryImpl extends AbstractRepository<GiftCertificate>
    implements CertificateRepository {

  @PersistenceContext private final EntityManager entityManager;

  protected CertificateRepositoryImpl(EntityManager entityManager) {
    super(GiftCertificate.class);
    this.entityManager = entityManager;
  }

  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public Optional<List<GiftCertificate>> getAll(
      Map<String, String> searchParams, Integer page, Integer size) {
    return Optional.ofNullable(
        entityManager
            .createQuery(
                SqlQueryCreator.createQueryFromCertificateSearchParameters(searchParams),
                GiftCertificate.class)
            .setFirstResult((page - 1) * size)
            .setMaxResults(size)
            .getResultList());
  }

  @Override
  public Optional<List<GiftCertificate>> getByTags(
      List<String> tagNames, Integer page, Integer size) {
    return Optional.ofNullable(
        entityManager
            .createNativeQuery(
                SqlQueryCreator.createQueryForSearchCertificatesByTags(tagNames),
                GiftCertificate.class)
            .setFirstResult((page - 1) * size)
            .setMaxResults(size)
            .getResultList());
  }

  @Override
  public Long getCertificateIdByName(String certificateName) {
    TypedQuery<Long> query = entityManager.createQuery(GET_CERTIFICATE_ID_BY_NAME, Long.class);
    query.setParameter("name", certificateName);
    return query.getResultList().stream().reduce((a, b) -> b).orElse(null);
  }

  @Override
  @Transactional
  public void updateCertificate(GiftCertificate giftCertificate) {
    entityManager.merge(giftCertificate);
  }
}
