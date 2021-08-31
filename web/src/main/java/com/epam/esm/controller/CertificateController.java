package com.epam.esm.controller;

import com.epam.esm.dto.RequestCertificateDTO;
import com.epam.esm.dto.ResponseCertificateDTO;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("certificates")
public class CertificateController {

    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseCertificateDTO> getCertificates(){
        return certificateService.getCertificates();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseCertificateDTO getCertificateById(@PathVariable("id") long id) {
        return certificateService.getCertificateById(id);
    }

    @GetMapping(value = "/parameters")
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseCertificateDTO> getCertificatesByParams(@RequestParam Map<String, String> searchParams) {
        return certificateService.getCertificatesByParams(searchParams);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseCertificateDTO addCertificate(@RequestBody RequestCertificateDTO certificateDTO) {
        return certificateService.addCertificate(certificateDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseCertificateDTO updateCertificate(@RequestBody RequestCertificateDTO certificateDTO){
        return certificateService.updateCertificate(certificateDTO);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteCertificateById(@PathVariable("id") long id){
        return certificateService.deleteCertificateById(id);
    }
}
