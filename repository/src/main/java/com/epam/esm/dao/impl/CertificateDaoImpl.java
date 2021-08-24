package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public class CertificateDaoImpl implements CertificateDao {
    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        return null;
    }

    @Override
    public GiftCertificate get(Long id) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
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
