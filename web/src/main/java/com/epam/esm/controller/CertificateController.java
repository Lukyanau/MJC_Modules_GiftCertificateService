package com.epam.esm.controller;

import com.epam.esm.dto.RequestCertificateDTO;
import com.epam.esm.dto.ResponseCertificateDTO;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("certificates")
public class CertificateController {

    private final CertificateServiceImpl certificateService;

    @Autowired
    public CertificateController(CertificateServiceImpl certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    public List<ResponseCertificateDTO> getCertificates(){
        return certificateService.getCertificates();
    }

    @GetMapping(value = "/id")
    public ResponseCertificateDTO getCertificateById(@RequestParam("id") long id) {
        return certificateService.getCertificateById(id);
    }

    @GetMapping(value = "/name")
    public ResponseCertificateDTO getCertificateByName(@RequestParam("name") String name) {
        return certificateService.getCertificateByName(name);
    }

    @GetMapping(value = "/tagName")
    public List<ResponseCertificateDTO> getCertificatesByTag(@RequestParam("tagName") String tagName) {
        return certificateService.getCertificatesByTag(tagName);
    }

    @GetMapping(value = "/partOfName")
    public List<ResponseCertificateDTO> getCertificatesByPartOfName(@RequestParam("partOfName") String partOfName) {
        return certificateService.getCertificatesByPartOfName(partOfName);
    }

    @GetMapping(value = "/partOfDescription")
    public List<ResponseCertificateDTO> getCertificatesByPartOfDescription
            (@RequestParam("partOfDescription") String partOfDescription) {
        return certificateService.getCertificatesByPartOfDescription(partOfDescription);
    }

    @PostMapping
    public ResponseCertificateDTO addCertificate(@RequestBody RequestCertificateDTO certificateDTO) {
        return certificateService.addCertificate(certificateDTO);
    }

    @PutMapping
    public ResponseCertificateDTO updateCertificate(@RequestBody RequestCertificateDTO certificateDTO){
        return certificateService.updateCertificate(certificateDTO);
    }

    @DeleteMapping
    public boolean deleteCertificateById(@RequestParam("id") long id){
        return certificateService.deleteCertificateById(id);
    }
}
