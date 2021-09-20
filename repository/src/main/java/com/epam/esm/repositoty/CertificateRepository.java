package com.epam.esm.repositoty;

import com.epam.esm.entity.GiftCertificate;

public interface CertificateRepository extends BaseRepository<GiftCertificate> {
    void updateCertificate(long id, GiftCertificate giftCertificate);
    Long getCertificateIdByName(String certificateName);

}
