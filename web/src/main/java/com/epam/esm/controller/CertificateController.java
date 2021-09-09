package com.epam.esm.controller;

import com.epam.esm.dto.RequestCertificateDto;
import com.epam.esm.dto.ResponseCertificateDto;
import com.epam.esm.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseCertificateDto updateCertificate(@PathVariable("id") long id,
                                                    @RequestBody RequestCertificateDto certificateDto) {
        return certificateService.updateCertificate(id, certificateDto);
    }

    /**
     * method do partial update of certificate
     *
     * @param id is certificate id
     * @param requestCertificateDto is certificate with updating fields
     * @return updated certificate
     */
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
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificateById(@PathVariable("id") long id) {
        certificateService.deleteCertificateById(id);
    }
}
