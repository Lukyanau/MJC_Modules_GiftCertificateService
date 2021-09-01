package com.epam.esm.service;

import com.epam.esm.dto.RequestCertificateDto;
import com.epam.esm.dto.ResponseCertificateDto;

import java.util.List;
import java.util.Map;

public interface CertificateService {
    List<ResponseCertificateDto> getCertificates();
    ResponseCertificateDto getCertificateById(long id);
    List<ResponseCertificateDto> getCertificatesByParams(Map<String,String> searchParams);
    ResponseCertificateDto addCertificate(RequestCertificateDto certificateDTO);
    ResponseCertificateDto updateCertificate(RequestCertificateDto certificateDTO);
    boolean deleteCertificateById(long id);
}
