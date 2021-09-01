package com.epam.esm.repositoty.impl;

import com.epam.esm.repositoty.SqlQuery;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repositoty.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        jdbcTemplate.update(SqlQuery.ADD_CERTIFICATE, giftCertificate.getName(), giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(), giftCertificate.getCreated(),
                giftCertificate.getUpdated());
        return giftCertificate;
    }

    @Override
    public GiftCertificate getById(long id) {
        return jdbcTemplate.query(SqlQuery.GET_CERTIFICATE_BY_ID,
                new BeanPropertyRowMapper<>(GiftCertificate.class), id).stream().findAny().orElse(null);
    }

    @Override
    public GiftCertificate getByName(String name) {
        return jdbcTemplate.query(SqlQuery.GET_CERTIFICATE_BY_NAME,
                new BeanPropertyRowMapper<>(GiftCertificate.class), name).stream().findAny().orElse(null);
    }

    @Override
    public List<GiftCertificate> getAll() {
        return jdbcTemplate.query(SqlQuery.GET_ALL_CERTIFICATES, new BeanPropertyRowMapper<>(GiftCertificate.class));
    }

    @Override
    public Long getCertificateIdByName(String certificateName) {
        return jdbcTemplate.queryForObject(SqlQuery.GET_CERTIFICATE_ID, Long.class, certificateName);
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update(SqlQuery.DELETE_CERTIFICATE_BY_ID, id) > 0;
    }


    @Override
    public void addCertificateAndTagIds(long certificateId, long tagId) {
        jdbcTemplate.update(SqlQuery.ADD_CERTIFICATE_AND_TAG_IDS, certificateId, tagId);
    }

    @Override
    public GiftCertificate updateCertificate(GiftCertificate giftCertificate) {
        jdbcTemplate.update(SqlQuery.UPDATE_CERTIFICATE_BY_NAME, giftCertificate.getDescription(), giftCertificate.getPrice(),
                giftCertificate.getDuration(), giftCertificate.getUpdated(), giftCertificate.getName());
        return giftCertificate;
    }
}
