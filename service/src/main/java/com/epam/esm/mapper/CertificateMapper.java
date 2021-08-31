package com.epam.esm.mapper;

import com.epam.esm.dto.RequestCertificateDTO;
import com.epam.esm.dto.ResponseCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CertificateMapper {
    private final TagMapper tagMapper;

    @Autowired
    public CertificateMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    public GiftCertificate convertToEntity(RequestCertificateDTO requestCertificateDTO) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(requestCertificateDTO.getId());
        giftCertificate.setName(requestCertificateDTO.getName());
        giftCertificate.setDescription(requestCertificateDTO.getDescription());
        giftCertificate.setPrice(requestCertificateDTO.getPrice());
        giftCertificate.setDuration(requestCertificateDTO.getDuration());
        giftCertificate.setCertificateTags(requestCertificateDTO.getCertificateTags()
                .stream().map(tagMapper::convertToEntity).collect(Collectors.toList()));
        return giftCertificate;
    }

    public GiftCertificate convertToEntity(ResponseCertificateDTO responseCertificateDTO) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(responseCertificateDTO.getId());
        giftCertificate.setName(responseCertificateDTO.getName());
        giftCertificate.setDescription(responseCertificateDTO.getDescription());
        giftCertificate.setPrice(responseCertificateDTO.getPrice());
        giftCertificate.setDuration(responseCertificateDTO.getDuration());
        giftCertificate.setCreated(responseCertificateDTO.getCreated());
        giftCertificate.setUpdated(responseCertificateDTO.getUpdated());
        giftCertificate.setCertificateTags(responseCertificateDTO.getCertificateTags()
                .stream().map(tagMapper::convertToEntity).collect(Collectors.toList()));
        return giftCertificate;
    }

    public ResponseCertificateDTO convertToDTO(GiftCertificate giftCertificate) {
        ResponseCertificateDTO responseCertificateDTO = new ResponseCertificateDTO();
        responseCertificateDTO.setId(giftCertificate.getId());
        responseCertificateDTO.setName(giftCertificate.getName());
        responseCertificateDTO.setDescription(giftCertificate.getDescription());
        responseCertificateDTO.setPrice(giftCertificate.getPrice());
        responseCertificateDTO.setDuration(giftCertificate.getDuration());
        responseCertificateDTO.setCreated(giftCertificate.getCreated());
        responseCertificateDTO.setUpdated(giftCertificate.getUpdated());
        responseCertificateDTO.setCertificateTags(giftCertificate.getCertificateTags()
                .stream().map(tagMapper::convertToDTO).collect(Collectors.toList()));
        return responseCertificateDTO;
    }
}
