package com.epam.esm.repositoty;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends BaseRepository<GiftCertificate> {
    void updateCertificate(long id, GiftCertificate giftCertificate);
    Long getCertificateIdByName(String certificateName);

}
