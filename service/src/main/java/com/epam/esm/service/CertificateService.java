package com.epam.esm.service;

import com.epam.esm.dto.RequestCertificateDTO;
import com.epam.esm.dto.ResponseCertificateDTO;
import com.epam.esm.exception.IncorrectInputParametersException;
import com.epam.esm.exception.InvalidIdException;
import com.epam.esm.exception.InvalidNameException;
import com.epam.esm.exception.NotFoundException;

import java.util.List;

public interface CertificateService {
    List<ResponseCertificateDTO> getCertificates() throws NotFoundException;
    ResponseCertificateDTO getCertificateById(long id) throws InvalidIdException, NotFoundException;
    ResponseCertificateDTO getCertificateByName(String name) throws InvalidNameException, NotFoundException;
    ResponseCertificateDTO addCertificate(RequestCertificateDTO certificateDTO)
            throws IncorrectInputParametersException, InvalidNameException;
    ResponseCertificateDTO updateCertificate(RequestCertificateDTO certificateDTO);
    boolean deleteCertificateById(long id) throws NotFoundException, InvalidIdException;
}
