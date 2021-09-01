package com.epam.esm.mapper;

import com.epam.esm.dto.RequestCertificateDto;
import com.epam.esm.dto.ResponseCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CertificateMapper {

    private final ModelMapper modelMapper;
    private final TagMapper tagMapper;

    @Autowired
    public CertificateMapper(ModelMapper modelMapper, TagMapper tagMapper) {
        this.modelMapper = modelMapper;
        this.tagMapper = tagMapper;
    }

    public GiftCertificate convertToEntity(RequestCertificateDto requestCertificateDTO) {
        GiftCertificate giftCertificate = modelMapper.map(requestCertificateDTO, GiftCertificate.class);
        giftCertificate.setCertificateTags(requestCertificateDTO.getCertificateTags()
                .stream().map(tagMapper::convertToEntity).collect(Collectors.toList()));
        return giftCertificate;
    }

    public GiftCertificate convertToEntity(ResponseCertificateDto responseCertificateDto) {
        GiftCertificate giftCertificate = modelMapper.map(responseCertificateDto, GiftCertificate.class);
        giftCertificate.setCertificateTags(responseCertificateDto.getCertificateTags()
                .stream().map(tagMapper::convertToEntity).collect(Collectors.toList()));
        return giftCertificate;
    }

    public ResponseCertificateDto convertToDto(GiftCertificate giftCertificate) {
        ResponseCertificateDto responseCertificateDto = modelMapper.map(giftCertificate, ResponseCertificateDto.class);
        responseCertificateDto.setCertificateTags(giftCertificate.getCertificateTags()
                .stream().map(tagMapper::convertToDto).collect(Collectors.toList()));
        return responseCertificateDto;
    }
}
