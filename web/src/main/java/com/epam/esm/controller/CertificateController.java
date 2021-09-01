package com.epam.esm.controller;

import com.epam.esm.dto.RequestCertificateDto;
import com.epam.esm.dto.ResponseCertificateDto;
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
    public List<ResponseCertificateDto> getCertificates(){
        return certificateService.getCertificates();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseCertificateDto getCertificateById(@PathVariable("id") long id) {
        return certificateService.getCertificateById(id);
    }

    @GetMapping(value = "/parameters")
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseCertificateDto> getCertificatesByParams(@RequestParam Map<String, String> searchParams) {
        return certificateService.getCertificatesByParams(searchParams);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseCertificateDto addCertificate(@RequestBody RequestCertificateDto certificateDto) {
        return certificateService.addCertificate(certificateDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseCertificateDto updateCertificate(@RequestBody RequestCertificateDto certificateDto){
        return certificateService.updateCertificate(certificateDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteCertificateById(@PathVariable("id") long id){
        return certificateService.deleteCertificateById(id);
    }
}
