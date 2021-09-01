package com.epam.esm.repositoty;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends BaseRepository<GiftCertificate> {
    void addCertificateAndTagIds(long certificateId, long tagId);
    GiftCertificate updateCertificate(GiftCertificate giftCertificate);
    GiftCertificate getByName(String certificateName);
    Long getCertificateIdByName(String certificateName);

}
