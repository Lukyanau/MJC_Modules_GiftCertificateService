package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface CertificateDao extends BaseDao<GiftCertificate> {
    GiftCertificate updateCertificate(GiftCertificate giftCertificate);
    List<GiftCertificate> getCertificatesByTag(String tagName);
    List<GiftCertificate> getCertificatesByPartOfName(String partOfName);
    List<GiftCertificate> getCertificatesByPartOfDescription(String partOfDescription);
}
