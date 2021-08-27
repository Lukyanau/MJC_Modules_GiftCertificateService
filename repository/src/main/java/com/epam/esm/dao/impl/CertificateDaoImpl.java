package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.SqlQuery;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CertificateDaoImpl implements CertificateDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        if (getByName(giftCertificate.getName()) == null) {
            jdbcTemplate.update(SqlQuery.ADD_CERTIFICATE, giftCertificate.getName(), giftCertificate.getDescription(),
                    giftCertificate.getPrice(), giftCertificate.getDuration(), giftCertificate.getCreated(),
                    giftCertificate.getUpdated());
            return giftCertificate;
        }
        return null;
    }

    @Override
    public List<GiftCertificate> getAll() {
        return null;
    }

    @Override
    public GiftCertificate getById(long id) {
        return null;
    }

    @Override
    public GiftCertificate getByName(String name) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public void addCertificateAndTagIds(long certificateId, long tagId) {
        jdbcTemplate.update(SqlQuery.ADD_CERTIFICATE_AND_TAG_IDS, certificateId, tagId);
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
