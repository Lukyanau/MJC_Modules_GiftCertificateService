package com.epam.esm.repositoty.impl;

import com.epam.esm.dao.SqlQuery;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
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
//        GiftCertificate addedCertificate = certificateDao.add(giftCertificate);
//        addedCertificate.setId(certificateDao.getByName(addedCertificate.getName()).getId());
//        for (Tag tag : addedCertificate.getCertificateTags()) {
//            if (tagDao.getByName(tag.getName()) != null) {
//                certificateDao.addCertificateAndTagIds(addedCertificate.getId(), tag.getId());
//            } else {
//                tagDao.add(tag);
//                certificateDao.addCertificateAndTagIds(addedCertificate.getId(), tagDao.getByName(tag.getName()).getId());
//            }
//        }
//        return addedCertificate;
        return null;
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
    public boolean delete(long id) {
        return false;
    }

    @Override
    public GiftCertificate updateCertificate(GiftCertificate giftCertificate) {
        return null;
    }

    @Override
    public List<GiftCertificate> getCertificatesByTag(String tagName) {
        return null;
    }

    @Override
    public List<GiftCertificate> getCertificatesByPartOfName(String partOfName) {
        return null;
    }

    @Override
    public List<GiftCertificate> getCertificatesByPartOfDescription(String partOfDescription) {
        return null;
    }
}
