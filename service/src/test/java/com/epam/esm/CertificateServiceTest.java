package com.epam.esm;

import com.epam.esm.dto.ResponseCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.repositoty.CertificateRepository;
import com.epam.esm.repositoty.TagRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.CertificateServiceImpl;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CertificateServiceTest {
    private CertificateRepository certificateRepository;
    private CertificateValidator certificateValidator;
    private CertificateMapper certificateMapper;
    private CertificateService certificateService;
    private TagService tagService;
    private TagValidator tagValidator;
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        certificateRepository = mock(CertificateRepository.class);
        certificateValidator = mock(CertificateValidator.class);
        tagService = mock(TagService.class);
        tagValidator = mock(TagValidator.class);
        tagRepository = mock(TagRepository.class);
        certificateMapper = mock(CertificateMapper.class);
        certificateService = new CertificateServiceImpl(certificateValidator, tagValidator, certificateRepository,
                certificateMapper, tagService, tagRepository);
    }

    @AfterEach
    void tearDown() {
        certificateRepository = null;
        certificateValidator = null;
        tagService = null;
        tagValidator = null;
        tagRepository = null;
        certificateMapper = null;
        certificateService = null;
    }

    @Test
    void findAllCorrectDataShouldReturnListWithCertificateDTO() {
        GiftCertificate giftCertificate1 = new GiftCertificate();
        giftCertificate1.setId(10L);
        giftCertificate1.setName("FirstCertificate");
        giftCertificate1.setDescription("Cafe certificate");
        giftCertificate1.setPrice(BigDecimal.valueOf(1000));
        giftCertificate1.setDuration(90);
        giftCertificate1.setCertificateTags(new ArrayList<>());
        GiftCertificate giftCertificate2 = new GiftCertificate();
        giftCertificate2.setId(12L);
        giftCertificate2.setName("SecondCertificate");
        giftCertificate2.setDescription("Gym certificate");
        giftCertificate2.setPrice(BigDecimal.valueOf(1500));
        giftCertificate2.setDuration(30);
        giftCertificate2.setCertificateTags(new ArrayList<>());
        when(certificateRepository.getAll()).thenReturn(Arrays.asList(giftCertificate1, giftCertificate2));
        when(tagService.getTagsByCertificateId(any(Long.class))).thenReturn(new ArrayList<>());
        int expectedSize = 2;
        int actualSize = certificateService.getCertificates().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void findAllNoCertificatesShouldThrowException() {
        when(certificateRepository.getAll()).thenReturn(null);
        when(tagService.getTagsByCertificateId(any(Long.class))).thenReturn(new ArrayList<>());
        assertThrows(ServiceException.class, () -> certificateService.getCertificates());
    }

    @Test
    void findByIdCorrectDataShouldReturnGiftCertificate() {
        GiftCertificate giftCertificate1 = new GiftCertificate();
        giftCertificate1.setId(10L);
        giftCertificate1.setName("FirstCertificate");
        giftCertificate1.setDescription("Cafe certificate");
        giftCertificate1.setPrice(BigDecimal.valueOf(1000));
        giftCertificate1.setDuration(90);
        giftCertificate1.setCertificateTags(new ArrayList<>());
        ResponseCertificateDto expectedCertificate = new ResponseCertificateDto();
        expectedCertificate.setId(10L);
        expectedCertificate.setName("FirstCertificate");
        expectedCertificate.setDescription("Cafe certificate");
        expectedCertificate.setPrice(BigDecimal.valueOf(1000));
        expectedCertificate.setDuration(90);
        expectedCertificate.setCertificateTags(new ArrayList<>());

        when(certificateRepository.getById(any(Long.class))).thenReturn(giftCertificate1);
        when(certificateMapper.convertToDto(any(GiftCertificate.class))).thenReturn(expectedCertificate);
        when(tagService.getTagsByCertificateId(any(Long.class))).thenReturn(new ArrayList<>());

        ResponseCertificateDto actualCertificateDTO = certificateService.getCertificateById(10L);

        assertEquals(expectedCertificate, actualCertificateDTO);
    }

    @Test
    void findByIdNotExistIdShouldThrowException() {
        when(certificateRepository.getById(any(Long.class))).thenReturn(null);
        when(certificateMapper.convertToDto(any(GiftCertificate.class))).thenReturn(null);
        when(tagService.getTagsByCertificateId(any(Long.class))).thenReturn(new ArrayList<>());
        assertThrows(ServiceException.class, () -> certificateService.getCertificateById(-1L));
    }
}
