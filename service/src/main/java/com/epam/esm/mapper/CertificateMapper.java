package com.epam.esm.mapper;

import com.epam.esm.dto.RequestCertificateDto;
import com.epam.esm.dto.ResponseCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CertificateMapper {

  private final ModelMapper modelMapper;
  private final TagMapper tagMapper;

  public GiftCertificate convertToEntity(RequestCertificateDto requestCertificateDTO) {
    GiftCertificate giftCertificate = modelMapper.map(requestCertificateDTO, GiftCertificate.class);
    if (requestCertificateDTO.getTags() != null) {
      giftCertificate.setTags(
          requestCertificateDTO.getTags().stream()
              .map(tagMapper::convertToEntity)
              .collect(Collectors.toList()));
    }
    return giftCertificate;
  }

  public ResponseCertificateDto convertToDto(GiftCertificate giftCertificate) {
    ResponseCertificateDto responseCertificateDto =
        modelMapper.map(giftCertificate, ResponseCertificateDto.class);
    if (giftCertificate.getTags() != null) {
      responseCertificateDto.setTags(
          giftCertificate.getTags().stream()
              .map(tagMapper::convertToDto)
              .collect(Collectors.toList()));
    }
    return responseCertificateDto;
  }
}
