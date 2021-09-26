package com.epam.esm.controller;

import com.epam.esm.dto.RequestCertificateDto;
import com.epam.esm.dto.ResponseCertificateDto;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.CertificateLinkCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Certificate controller with CRUD methods
 *
 * @author Lukyanau I.M.
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/certificates")
public class CertificateController {

    private final CertificateService certificateService;
    private final CertificateLinkCreator certificateLinkCreator;

    /**
     * method founds all certificate with or without searchParameters
     *
     * @param searchParams is getting from url
     * @return List of ResponseCertificateDto objects
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    //constant
    public CollectionModel<ResponseCertificateDto> getCertificates(@RequestParam(name = "page", required = false,
                                                                           defaultValue = "1") Integer page,
                                                                   @RequestParam(name = "size", required = false,
                                                                           defaultValue = "10") Integer size,
                                                                   @RequestParam(required = false) Map<String, String>
                                                                               searchParams) {
        return certificateLinkCreator.getCertificatesWithLinks(certificateService.getCertificates(searchParams, page, size));
    }

    /**
     * methods found certificates which contains tags
     *
     * @param tagName is list of searching tags
     * @return list of sorted certificates
     */
    @GetMapping(value = "/tags")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ResponseCertificateDto> getCertificatesByTags(@RequestParam(name = "page", required = false,
                                                                                 defaultValue = "1") Integer page,
                                                                         @RequestParam(name = "size", required = false,
                                                                                 defaultValue = "10") Integer size,
                                                                         @RequestParam(required = false) Map<String,String>
                                                                                     tagName) {
        return certificateLinkCreator.getCertificatesWithLinks(certificateService.getCertificatesByTags(tagName, page, size));
    }

    /**
     * method founds certificate by id
     *
     * @param id is getting from url
     * @return ResponseCertificateDto if it's founded or null if not
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<ResponseCertificateDto> getCertificateById(@PathVariable("id") long id) {
        return certificateLinkCreator.getCertificateWithLinks(certificateService.getCertificateById(id));
    }

    /**
     * method add certificate
     *
     * @param certificateDto is getting by json message from request body
     * @return Added object? if it was added or null
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<ResponseCertificateDto> addCertificate(@RequestBody RequestCertificateDto certificateDto) {
        return certificateLinkCreator.getCertificateWithLinks(certificateService.addCertificate(certificateDto));
    }

    /**
     * method update existing certificate
     *
     * @param requestCertificateDto is getting by json message from request body
     * @return ResponseCertificateDto if it's updated
     */
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<ResponseCertificateDto> updateCertificate(@PathVariable("id") long id,
                                                                 @RequestBody RequestCertificateDto requestCertificateDto) {
        return certificateLinkCreator.getCertificateWithLinks(certificateService.updateCertificate(id, requestCertificateDto));
    }

    /**
     * method do partial update of certificate
     *
     * @param id                    is certificate id
     * @param requestCertificateDto is certificate with updating fields
     * @return updated certificate
     */
    @PatchMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<ResponseCertificateDto> patchUpdateCertificate(@PathVariable("id") long id,
                                                                      @RequestBody RequestCertificateDto requestCertificateDto) {
        return certificateLinkCreator.getCertificateWithLinks(certificateService.
                patchUpdateCertificate(id, requestCertificateDto));
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
