package com.epam.esm.model_mapper;

import com.epam.esm.dto.RequestCertificateDTO;
import com.epam.esm.dto.ResponseCertificateDTO;
import com.epam.esm.entity.GiftCertificate;

public class CertificateMapper {
    public GiftCertificate convertToEntity(RequestCertificateDTO requestCertificateDTO) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(requestCertificateDTO.getId());
        giftCertificate.setName(requestCertificateDTO.getName());
        giftCertificate.setDescription(requestCertificateDTO.getDescription());
        giftCertificate.setPrice(requestCertificateDTO.getPrice());
        giftCertificate.setDuration(requestCertificateDTO.getDuration());
        giftCertificate.setCertificateTags(requestCertificateDTO.getCertificateTags());
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
        giftCertificate.setCertificateTags(responseCertificateDTO.getCertificateTags());
        return giftCertificate;
    }

    public ResponseCertificateDTO convertToDTO(GiftCertificate giftCertificate){
        ResponseCertificateDTO responseCertificateDTO = new ResponseCertificateDTO();
        responseCertificateDTO.setId(giftCertificate.getId());
        responseCertificateDTO.setName(giftCertificate.getName());
        responseCertificateDTO.setDescription(giftCertificate.getDescription());
        responseCertificateDTO.setPrice(giftCertificate.getPrice());
        responseCertificateDTO.setDuration(giftCertificate.getDuration());
        responseCertificateDTO.setCreated(giftCertificate.getCreated());
        responseCertificateDTO.setUpdated(giftCertificate.getUpdated());
        responseCertificateDTO.setCertificateTags(giftCertificate.getCertificateTags());
        return responseCertificateDTO;
    }
}
