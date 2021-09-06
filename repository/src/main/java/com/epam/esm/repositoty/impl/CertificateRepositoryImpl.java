package com.epam.esm.repositoty.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repositoty.CertificateRepository;
import com.epam.esm.repositoty.impl.query.SqlQuery;
import com.epam.esm.repositoty.impl.query.SqlQueryCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CertificateRepositoryImpl implements CertificateRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        jdbcTemplate.update(SqlQuery.ADD_CERTIFICATE, giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(), giftCertificate.getCreated(),
                giftCertificate.getUpdated());
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> getById(long id) {
        return jdbcTemplate.query(SqlQuery.GET_CERTIFICATE_BY_ID,
                new BeanPropertyRowMapper<>(GiftCertificate.class), id).stream().findAny();
    }

    @Override
    public Optional<List<GiftCertificate>> getAll(Map<String,String> searchParams) {
        return Optional.of(jdbcTemplate.query(SqlQueryCreator.createQueryFromSearchParameters(searchParams),
                new BeanPropertyRowMapper<>(GiftCertificate.class)));
    }

    @Override
    public Long getCertificateIdByName(String certificateName) {
        return jdbcTemplate.queryForObject(SqlQuery.GET_CERTIFICATE_ID, Long.class, certificateName);
    }

    @Override
    public int delete(long id) {
        return jdbcTemplate.update(SqlQuery.DELETE_CERTIFICATE_BY_ID, id);
    }


    @Override
    public void addCertificateAndTagIds(long certificateId, long tagId) {
        jdbcTemplate.update(SqlQuery.ADD_CERTIFICATE_AND_TAG_IDS, certificateId, tagId);
    }

    @Override
    public void updateCertificate(long id, GiftCertificate giftCertificate) {
        jdbcTemplate.update(SqlQuery.UPDATE_CERTIFICATE_BY_NAME, giftCertificate.getName(),
                giftCertificate.getDescription(), giftCertificate.getPrice(),
                giftCertificate.getDuration(), giftCertificate.getUpdated(), giftCertificate.getId());
    }
}
