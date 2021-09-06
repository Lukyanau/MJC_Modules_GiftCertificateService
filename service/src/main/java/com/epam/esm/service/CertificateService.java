package com.epam.esm.service;

import com.epam.esm.dto.RequestCertificateDto;
import com.epam.esm.dto.ResponseCertificateDto;

import java.util.List;
import java.util.Map;

public interface CertificateService {
    List<ResponseCertificateDto> getCertificates(Map<String, String> searchParams);
    ResponseCertificateDto getCertificateById(long id);
    ResponseCertificateDto addCertificate(RequestCertificateDto certificateDTO);
    ResponseCertificateDto updateCertificate(long id, RequestCertificateDto certificateDTO);
    ResponseCertificateDto patchUpdateCertificate(long id, Map<String, Object> fields);
    boolean deleteCertificateById(long id);
}
