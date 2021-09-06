package com.epam.esm.controller;

import com.epam.esm.dto.RequestCertificateDto;
import com.epam.esm.dto.ResponseCertificateDto;
import com.epam.esm.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Certificate controller with CRUD methods
 *
 * @author Lukyanau I.M.
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("certificates")
public class CertificateController {

    private final CertificateService certificateService;

    /**
     * method founds all certificate with or without searchParameters
     *
     * @param searchParams is getting from url
     * @return List of ResponseCertificateDto objects
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseCertificateDto> getCertificates(@RequestParam(required = false) Map<String, String> searchParams) {
        return certificateService.getCertificates(searchParams);
    }

    /**
     * method founds certificate by id
     *
     * @param id is getting from url
     * @return ResponseCertificateDto if it's founded or null if not
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    // check if string input
    public ResponseCertificateDto getCertificateById(@PathVariable("id") long id) {
        return certificateService.getCertificateById(id);
    }

    /**
     * method add certificate
     *
     * @param certificateDto is getting by json message from request body
     * @return Added object? if it was added or null
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseCertificateDto addCertificate(@RequestBody RequestCertificateDto certificateDto) {
        return certificateService.addCertificate(certificateDto);
    }

    /**
     * method update existing certificate
     *
     * @param certificateDto is getting by json message from request body
     * @return ResponseCertificateDto if it's updated
     */
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    // check if string input
    public ResponseCertificateDto updateCertificate(@PathVariable("id") long id,
                                                    @RequestBody RequestCertificateDto certificateDto) {
        return certificateService.updateCertificate(id, certificateDto);
    }

    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseCertificateDto patchUpdateCertificate(@PathVariable("id") long id,
                                                         @RequestBody RequestCertificateDto requestCertificateDto) {
        return certificateService.patchUpdateCertificate(id, requestCertificateDto);
    }

    /**
     * method delete certificate
     *
     * @param id is getting from url
     * @return String message
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteCertificateById(@PathVariable("id") long id) {
        certificateService.deleteCertificateById(id);
        return "Successfully deleted certificate with id:" + id;
    }
}
