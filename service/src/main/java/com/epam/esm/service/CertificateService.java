package com.epam.esm.service;

import com.epam.esm.dto.RequestCertificateDTO;
import com.epam.esm.dto.ResponseCertificateDTO;

import java.util.List;

public interface CertificateService {
    List<ResponseCertificateDTO> getCertificates();
    ResponseCertificateDTO getCertificateById(long id);
    ResponseCertificateDTO getCertificateByName(String name);
    ResponseCertificateDTO addCertificate(RequestCertificateDTO certificateDTO);
    ResponseCertificateDTO updateCertificate(RequestCertificateDTO certificateDTO);
    boolean deleteCertificateById(long id);
    List<ResponseCertificateDTO> getCertificatesByTag(String tagName);
    List<ResponseCertificateDTO> getCertificatesByPartOfName(String partOfName);
    List<ResponseCertificateDTO> getCertificatesByPartOfDescription(String partOfDescription);
}
