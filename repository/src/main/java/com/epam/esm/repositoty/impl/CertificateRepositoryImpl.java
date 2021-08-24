package com.epam.esm.repositoty.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repositoty.CertificateRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {
    private static final CertificateRepositoryImpl instance = new CertificateRepositoryImpl();

    private CertificateRepositoryImpl() {
    }

    public static CertificateRepositoryImpl getInstance(){
        return instance;
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        return null;
    }

    @Override
    public GiftCertificate get(long id) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public List<GiftCertificate> getAll() {
        return null;
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
