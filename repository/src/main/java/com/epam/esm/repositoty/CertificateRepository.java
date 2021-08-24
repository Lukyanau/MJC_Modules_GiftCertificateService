package com.epam.esm.repositoty;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends BaseRepository<GiftCertificate>{
    GiftCertificate updateCertificate(GiftCertificate giftCertificate);
    List<GiftCertificate> getCertificatesByTag(String tagName);
    List<GiftCertificate> getCertificatesByPartOfName(String partOfName);
    List<GiftCertificate> getCertificatesByPartOfDescription(String partOfDescription);
}
