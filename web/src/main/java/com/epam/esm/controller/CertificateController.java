package com.epam.esm.controller;

import com.epam.esm.dto.RequestCertificateDTO;
import com.epam.esm.dto.ResponseCertificateDTO;
import com.epam.esm.exception.IncorrectInputParametersException;
import com.epam.esm.exception.InvalidIdException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificateController {
    CertificateServiceImpl certificateService = CertificateServiceImpl.getInstance();

    @GetMapping
    public List<ResponseCertificateDTO> getCertificates() throws NotFoundException {
        return certificateService.getCertificates();
    }

    @GetMapping(value = "/id")
    public ResponseCertificateDTO getCertificateById(@RequestParam("id") long id) throws InvalidIdException, NotFoundException {
        return certificateService.getCertificateById(id);
    }

    @PostMapping
    public ResponseCertificateDTO addCertificate(@RequestBody RequestCertificateDTO certificateDTO)
            throws IncorrectInputParametersException {
        return certificateService.addCertificate(certificateDTO);
    }

    @PutMapping
    public ResponseCertificateDTO updateCertificate(@RequestBody RequestCertificateDTO certificateDTO){
        return certificateService.updateCertificate(certificateDTO);
    }

    @DeleteMapping
    @ResponseBody
    public boolean deleteCertificateById(@RequestParam("id") long id) throws NotFoundException, InvalidIdException {
        return certificateService.deleteCertificateById(id);
    }
}
