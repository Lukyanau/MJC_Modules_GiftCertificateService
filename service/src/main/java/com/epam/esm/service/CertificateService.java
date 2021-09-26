package com.epam.esm.service;

import com.epam.esm.dto.RequestCertificateDto;
import com.epam.esm.dto.ResponseCertificateDto;

import java.util.List;
import java.util.Map;

public interface CertificateService {

    List<ResponseCertificateDto> getCertificates(Map<String, String> searchParams,Integer page, Integer size);
    List<ResponseCertificateDto> getCertificatesByTags(Map<String, String> tags,Integer page, Integer size);
    ResponseCertificateDto getCertificateById(long id);
    ResponseCertificateDto addCertificate(RequestCertificateDto certificateDTO);
    ResponseCertificateDto updateCertificate(long id, RequestCertificateDto certificateDTO);
    ResponseCertificateDto patchUpdateCertificate(long id, RequestCertificateDto requestCertificateDto);
    boolean deleteCertificateById(long id);
}
