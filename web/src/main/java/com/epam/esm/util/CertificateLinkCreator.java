package com.epam.esm.util;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.dto.ResponseCertificateDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.epam.esm.utils.GlobalCustomConstants.PAGE_DEFAULT;
import static com.epam.esm.utils.GlobalCustomConstants.SIZE_DEFAULT;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CertificateLinkCreator {

  public CollectionModel<ResponseCertificateDto> getCertificatesWithLinks(
      List<ResponseCertificateDto> allCertificates) {
    allCertificates.forEach(this::addLinkToCertificate);
    Link selfLink = linkTo(CertificateController.class).withSelfRel();
    return CollectionModel.of(allCertificates, selfLink);
  }

  public EntityModel<ResponseCertificateDto> getCertificateWithLinks(
      ResponseCertificateDto certificate) {
    addLinkToCertificate(certificate);
    return EntityModel.of(certificate);
  }

  private void addLinkToCertificate(ResponseCertificateDto certificate) {
    Link getAllLink =
        linkTo(
                methodOn(CertificateController.class)
                    .getCertificates(PAGE_DEFAULT, SIZE_DEFAULT, null))
            .withRel("get all");
    Link getByTagsLink =
        linkTo(
                methodOn(CertificateController.class)
                    .getCertificatesByTags(PAGE_DEFAULT, SIZE_DEFAULT, Collections.emptyMap()))
            .withRel("get by tags");
    Link getById =
        linkTo(methodOn(CertificateController.class).getCertificateById(certificate.getId()))
            .withRel("get by id");
    Link addLink =
        linkTo(methodOn(CertificateController.class).addCertificate(certificate)).withRel("add");
    Link updateLink =
        linkTo(
                methodOn(CertificateController.class)
                    .updateCertificate(certificate.getId(), certificate))
            .withRel("update");
    Link deleteLink =
        linkTo(CertificateController.class).slash(certificate.getId()).withRel("delete");
    certificate.add(getAllLink, getById, getByTagsLink, addLink, updateLink, deleteLink);
  }
}
