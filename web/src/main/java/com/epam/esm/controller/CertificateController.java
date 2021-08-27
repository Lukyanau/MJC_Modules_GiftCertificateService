package com.epam.esm.controller;

import com.epam.esm.dto.RequestCertificateDTO;
import com.epam.esm.dto.ResponseCertificateDTO;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/certificates")
public class CertificateController {
    private final CertificateServiceImpl certificateService;

    @Autowired
    public CertificateController(CertificateServiceImpl certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    @ResponseBody
    public List<ResponseCertificateDTO> getCertificates(){
        return certificateService.getCertificates();
    }

    @GetMapping(value = "/id")
    @ResponseBody
    public ResponseCertificateDTO getCertificateById(@RequestParam("id") long id) {
        return certificateService.getCertificateById(id);
    }

    @GetMapping(value = "/name")
    @ResponseBody
    public ResponseCertificateDTO getCertificateByName(@RequestParam("name") String name) {
        return certificateService.getCertificateByName(name);
    }

    @PostMapping
    @ResponseBody
    public ResponseCertificateDTO addCertificate(@RequestBody RequestCertificateDTO certificateDTO) {
        return certificateService.addCertificate(certificateDTO);
    }

    @PutMapping
    @ResponseBody
    public ResponseCertificateDTO updateCertificate(@RequestBody RequestCertificateDTO certificateDTO){
        return certificateService.updateCertificate(certificateDTO);
    }

    @DeleteMapping
    @ResponseBody
    public boolean deleteCertificateById(@RequestParam("id") long id){
        return certificateService.deleteCertificateById(id);
    }
}
