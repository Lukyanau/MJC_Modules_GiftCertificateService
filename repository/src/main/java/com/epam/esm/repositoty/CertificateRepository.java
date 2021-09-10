package com.epam.esm.repositoty;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends BaseRepository<GiftCertificate> {
    void addCertificateAndTagIds(long certificateId, long tagId);
    void updateCertificate(long id, GiftCertificate giftCertificate);
    Long getCertificateIdByName(String certificateName);

}
