package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends BaseRepository<GiftCertificate>{

  Optional<List<GiftCertificate>> getByTags(List<String> tagNames, Integer page, Integer size);

  void updateCertificate(GiftCertificate giftCertificate);

  Long getCertificateIdByName(String certificateName);
}
