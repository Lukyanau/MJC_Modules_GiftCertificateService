package com.epam.esm.service;

import com.epam.esm.dto.RequestCertificateDTO;
import com.epam.esm.dto.ResponseCertificateDTO;

import java.util.List;
import java.util.Map;

public interface CertificateService {
    List<ResponseCertificateDTO> getCertificates();
    ResponseCertificateDTO getCertificateById(long id);
    List<ResponseCertificateDTO> getCertificatesByParams(Map<String,String> searchParams);
    ResponseCertificateDTO addCertificate(RequestCertificateDTO certificateDTO);
    ResponseCertificateDTO updateCertificate(RequestCertificateDTO certificateDTO);
    boolean deleteCertificateById(long id);
}
