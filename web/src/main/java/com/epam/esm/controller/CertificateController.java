package com.epam.esm.controller;

import com.epam.esm.dto.RequestCertificateDto;
import com.epam.esm.dto.ResponseCertificateDto;
import com.epam.esm.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    /**
     * method founds all certificate with or without searchParameters
     *
     * @param searchParams is getting from url
     * @return List of ResponseCertificateDto objects
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ResponseCertificateDto> getCertificates(@RequestParam(required = false)
                                                                           Map<String, String> searchParams) {
        List<ResponseCertificateDto> currentCertificates = certificateService.getCertificates(searchParams);
        currentCertificates.forEach(certificate -> {
            Link getByIdLink = linkTo(CertificateController.class).slash(certificate.getId()).withSelfRel();
            Link getByTagsLink = linkTo(methodOn(CertificateController.class).getCertificatesByTags(Collections.emptyList()))
                    .withRel("getByTags");
            Link updateLink = linkTo(CertificateController.class).slash(certificate.getId()).withRel("updateCertificate");
            Link addLink = linkTo(methodOn(CertificateController.class).addCertificate(certificate)).withRel("addCertificate");
            Link deleteLink = linkTo(CertificateController.class).slash(certificate.getId()).withRel("deleteCertificate");
            certificate.add(getByIdLink, getByTagsLink, updateLink, addLink, deleteLink);
        });
        Link selfLink = linkTo(CertificateController.class).withSelfRel();
        return CollectionModel.of(currentCertificates, selfLink);
    }

    /**
     * methods found certificates which contains tags
     *
     * @param tag is list of searching tags
     * @return list of sorted certificates
     */
    @GetMapping(value = "/tags")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ResponseCertificateDto> getCertificatesByTags(@RequestParam List<String> tag) {
        List<ResponseCertificateDto> currentCertificates = certificateService.getCertificatesByTags(tag);
        currentCertificates.forEach(certificate -> {
            Link getByIdLink = linkTo(methodOn(CertificateController.class).getCertificateById(certificate.getId()))
                    .withRel("getById");
            Link getAllLink = linkTo(methodOn(CertificateController.class).getCertificates(Collections.emptyMap()))
                    .withRel("getAllCertificates");
            Link updateLink = linkTo(methodOn(CertificateController.class).updateCertificate(certificate.getId(), certificate))
                    .withRel("updateCertificate");
            Link addLink = linkTo(methodOn(CertificateController.class).addCertificate(certificate)).withRel("addCertificate");
            Link deleteLink = linkTo(methodOn(CertificateController.class).getCertificateById(certificate.getId())).
                    withRel("deleteCertificate");
            certificate.add(getByIdLink, getAllLink, updateLink, addLink, deleteLink);
        });
        Link selfLink = linkTo(methodOn(CertificateController.class).getCertificatesByTags(Collections.emptyList())).
                withSelfRel();
        return CollectionModel.of(currentCertificates, selfLink);
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
        ResponseCertificateDto currentCertificate = certificateService.getCertificateById(id);
        Link getAllLink = linkTo(methodOn(CertificateController.class).getCertificates(Collections.emptyMap())).
                withRel("getAllCertificates");
        Link getByTagsLink = linkTo(methodOn(CertificateController.class).getCertificatesByTags(Collections.emptyList()))
                .withRel("getByTags");
        Link addLink = linkTo(methodOn(CertificateController.class).addCertificate(currentCertificate)).
                withRel("addCertificate");
        Link updateLink = linkTo(methodOn(CertificateController.class).updateCertificate(currentCertificate.getId(),
                currentCertificate)).withRel("updateCertificate");
        Link deleteLink = linkTo(CertificateController.class).slash(currentCertificate.getId()).
                withRel("deleteCertificate");
        Link selfLink = linkTo(CertificateController.class).slash(currentCertificate.getId()).withSelfRel();
        return EntityModel.of(currentCertificate, getAllLink, getByTagsLink, addLink, updateLink, deleteLink, selfLink);
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
        ResponseCertificateDto currentCertificate = certificateService.addCertificate(certificateDto);
        Link getAllLink = linkTo(methodOn(CertificateController.class).getCertificates(Collections.emptyMap())).
                withRel("getAllCertificates");
        Link getByTagsLink = linkTo(methodOn(CertificateController.class).getCertificatesByTags(Collections.emptyList()))
                .withRel("getByTags");
        Link getById = linkTo(methodOn(CertificateController.class).getCertificateById(currentCertificate.getId())).
                withRel("getById");
        Link updateLink = linkTo(methodOn(CertificateController.class).updateCertificate(currentCertificate.getId(),
                currentCertificate)).withRel("updateCertificate");
        Link deleteLink = linkTo(CertificateController.class).slash(currentCertificate.getId()).
                withRel("deleteCertificate");
        Link selfLink = linkTo(CertificateController.class).withSelfRel();
        return EntityModel.of(currentCertificate, getAllLink, getById, getByTagsLink, updateLink, deleteLink, selfLink);
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
        ResponseCertificateDto currentCertificate = certificateService.updateCertificate(id, requestCertificateDto);
        return getResponseCertificateDtoEntityModel(currentCertificate);
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
        ResponseCertificateDto currentCertificate = certificateService.patchUpdateCertificate(id, requestCertificateDto);
        return getResponseCertificateDtoEntityModel(currentCertificate);
    }

    private EntityModel<ResponseCertificateDto> getResponseCertificateDtoEntityModel(ResponseCertificateDto currentCertificate) {
        Link getAllLink = linkTo(methodOn(CertificateController.class).getCertificates(Collections.emptyMap())).
                withRel("getAllCertificates");
        Link getByTagsLink = linkTo(methodOn(CertificateController.class).getCertificatesByTags(Collections.emptyList()))
                .withRel("getByTags");
        Link getById = linkTo(methodOn(CertificateController.class).getCertificateById(currentCertificate.getId())).
                withRel("getById");
        Link addLink = linkTo(methodOn(CertificateController.class).addCertificate(currentCertificate)).withRel("addCertificate");
        Link deleteLink = linkTo(CertificateController.class).slash(currentCertificate.getId()).
                withRel("deleteCertificate");
        Link selfLink = linkTo(CertificateController.class).slash(currentCertificate.getId()).withSelfRel();
        return EntityModel.of(currentCertificate, getAllLink, getById, getByTagsLink, addLink, deleteLink, selfLink);
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
