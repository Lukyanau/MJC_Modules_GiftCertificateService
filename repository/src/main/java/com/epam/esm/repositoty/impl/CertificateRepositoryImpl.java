package com.epam.esm.repositoty.impl;

import com.epam.esm.configuration.ConnectionConfig;
import com.epam.esm.dao.impl.CertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repositoty.CertificateRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConnectionConfig.class);
    CertificateDaoImpl certificateDao = new CertificateDaoImpl(applicationContext.getBean(JdbcTemplate.class));
    TagDaoImpl tagDao = new TagDaoImpl(applicationContext.getBean(JdbcTemplate.class));

    private static final CertificateRepositoryImpl instance = new CertificateRepositoryImpl();

    private CertificateRepositoryImpl() {
    }

    public static CertificateRepositoryImpl getInstance() {
        return instance;
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        GiftCertificate addedCertificate = certificateDao.add(giftCertificate);
        addedCertificate.setId(certificateDao.getByName(addedCertificate.getName()).getId());
        for (Tag tag : addedCertificate.getCertificateTags()) {
            if (tagDao.getByName(tag.getName()) != null) {
                certificateDao.addCertificateAndTagIds(addedCertificate.getId(), tag.getId());
            } else {
                tagDao.add(tag);
                certificateDao.addCertificateAndTagIds(addedCertificate.getId(), tagDao.getByName(tag.getName()).getId());
            }
        }
        return addedCertificate;
    }

    @Override
    public GiftCertificate getById(long id) {
        GiftCertificate receivedCertificate = certificateDao.getById(id);
        if (receivedCertificate != null) {
            receivedCertificate.setCertificateTags(tagDao.getTagsByCertificateName(receivedCertificate.getName()));
        }
        return receivedCertificate;
    }

    @Override
    public GiftCertificate getByName(String name) {
        GiftCertificate receivedCertificate = certificateDao.getByName(name);
        if (receivedCertificate != null) {
            receivedCertificate.setCertificateTags(tagDao.getTagsByCertificateName(receivedCertificate.getName()));
        }
        return receivedCertificate;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public List<GiftCertificate> getAll() {
        List<GiftCertificate> allCertificates = certificateDao.getAll();
        if (allCertificates != null) {
            for (GiftCertificate certificate : allCertificates) {
                certificate.setCertificateTags(tagDao.getTagsByCertificateName(certificate.getName()));
            }
        }
        return allCertificates;
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
