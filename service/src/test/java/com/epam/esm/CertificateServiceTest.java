package com.epam.esm;

import com.epam.esm.dto.RequestCertificateDto;
import com.epam.esm.dto.ResponseCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.impl.CertificateServiceImpl;
import com.epam.esm.validator.BaseValidator;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.SearchParamsValidator;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CertificateServiceTest.class)
public class CertificateServiceTest {

    private CertificateRepositoryImpl certificateRepository;
    private CertificateValidator certificateValidator;
    private CertificateMapper certificateMapper;
    private SearchParamsValidator searchValidator;
    private BaseValidator baseValidator;
    private TagRepositoryImpl tagRepository;
    private TagValidator tagValidator;
    private CertificateService certificateService;

    @BeforeEach
    void setUp() {
        certificateRepository = mock(CertificateRepositoryImpl.class);
        certificateValidator = new CertificateValidator();
        tagValidator = new TagValidator();
        tagRepository = mock(TagRepositoryImpl.class);
        certificateMapper = mock(CertificateMapper.class);
        searchValidator = mock(SearchParamsValidator.class);
        baseValidator = new BaseValidator();
        certificateService = new CertificateServiceImpl(certificateRepository, certificateValidator,
                certificateMapper, searchValidator, baseValidator, tagRepository, tagValidator) {
        };
    }

    @AfterEach
    void tearDown() {
        certificateRepository = null;
        certificateValidator = null;
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
        giftCertificate1.setTags(new ArrayList<>());
        GiftCertificate giftCertificate2 = new GiftCertificate();
        giftCertificate2.setId(12L);
        giftCertificate2.setName("SecondCertificate");
        giftCertificate2.setDescription("Gym certificate");
        giftCertificate2.setPrice(BigDecimal.valueOf(1500));
        giftCertificate2.setDuration(30);
        giftCertificate2.setTags(new ArrayList<>());
        when(certificateRepository.getAll(Collections.emptyMap(), 0, 0)).thenReturn(Optional.of
                (Arrays.asList(giftCertificate1, giftCertificate2)));
        int expectedSize = 2;
        int actualSize = certificateService.getCertificates(Collections.emptyMap(), 0, 0).size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void findAllNoCertificatesShouldThrowException() {
        when(certificateRepository.getAll(Collections.emptyMap(), 0, 0)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> certificateService.getCertificates(Collections.emptyMap(), 0, 0));
    }

    @Test
    void findByIdCorrectDataShouldReturnGiftCertificate() {
        GiftCertificate giftCertificate1 = new GiftCertificate();
        giftCertificate1.setId(10L);
        giftCertificate1.setName("FirstCertificate");
        giftCertificate1.setDescription("Cafe certificate");
        giftCertificate1.setPrice(BigDecimal.valueOf(1000));
        giftCertificate1.setDuration(90);
        giftCertificate1.setTags(new ArrayList<>());
        ResponseCertificateDto expectedCertificate = new ResponseCertificateDto();
        expectedCertificate.setId(10L);
        expectedCertificate.setName("FirstCertificate");
        expectedCertificate.setDescription("Cafe certificate");
        expectedCertificate.setPrice(BigDecimal.valueOf(1000));
        expectedCertificate.setDuration(90);
        expectedCertificate.setTags(new ArrayList<>());

        when(certificateRepository.getById(any(Long.class))).thenReturn(Optional.of(giftCertificate1));
        when(certificateMapper.convertToDto(any(GiftCertificate.class))).thenReturn(expectedCertificate);

        ResponseCertificateDto actualCertificateDTO = certificateService.getCertificateById(10L);

        assertEquals(expectedCertificate, actualCertificateDTO);
    }

    @Test
    void findByIdNotExistIdShouldThrowException() {
        when(certificateRepository.getById(any(Long.class))).thenReturn(Optional.empty());
        when(certificateMapper.convertToDto(any(GiftCertificate.class))).thenReturn(null);
        assertThrows(ServiceException.class, () -> certificateService.getCertificateById(-1L));
    }

    @Test
    void updateCertificateCorrectDataShouldReturnResponseCertificateDto() {
        long certificateId = 10L;
        RequestCertificateDto requestCertificate = new RequestCertificateDto();
        requestCertificate.setId(10L);
        requestCertificate.setName("FirstCertificate");
        requestCertificate.setDescription("Cafe certificate");
        requestCertificate.setPrice(BigDecimal.valueOf(1000));
        requestCertificate.setDuration(90);
        requestCertificate.setTags(Collections.emptyList());

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(10L);
        giftCertificate.setName("FirstCertificate");
        giftCertificate.setDescription("Cafe certificate");
        giftCertificate.setPrice(BigDecimal.valueOf(1000));
        giftCertificate.setDuration(90);
        giftCertificate.setCreated(LocalDateTime.now());
        giftCertificate.setUpdated(LocalDateTime.now());
        giftCertificate.setTags(Collections.emptyList());

        ResponseCertificateDto expectedCertificate = new ResponseCertificateDto();
        expectedCertificate.setId(10L);
        expectedCertificate.setName("FirstCertificate");
        expectedCertificate.setDescription("Cafe certificate");
        expectedCertificate.setPrice(BigDecimal.valueOf(1000));
        expectedCertificate.setDuration(90);
        expectedCertificate.setCreated(LocalDateTime.now());
        expectedCertificate.setUpdated(LocalDateTime.now());
        expectedCertificate.setTags(Collections.emptyList());

        when(certificateRepository.getById(anyLong())).thenReturn(Optional.of(giftCertificate));
        when(certificateMapper.convertToEntity(any(RequestCertificateDto.class))).thenReturn(giftCertificate);
        when(certificateRepository.getCertificateIdByName(anyString())).thenReturn(certificateId);
        when(certificateMapper.convertToDto(any(GiftCertificate.class))).thenReturn(expectedCertificate);
        ResponseCertificateDto actualCertificate = certificateService.updateCertificate(certificateId, requestCertificate);
        assertEquals(expectedCertificate, actualCertificate);

    }

    @Test
    void updateCertificateIncorrectDataShouldThrowException() {
        long certificateId = -10L;
        RequestCertificateDto requestCertificate = new RequestCertificateDto();
        requestCertificate.setId(10L);
        requestCertificate.setName("FirstCertificate");
        requestCertificate.setDescription("Cafe certificate");
        requestCertificate.setPrice(BigDecimal.valueOf(1000));
        requestCertificate.setDuration(90);
        requestCertificate.setTags(Collections.emptyList());
        assertThrows(ServiceException.class, () -> certificateService.updateCertificate(certificateId, requestCertificate));
    }

    @Test
    void deleteCertificateByIdExistsCertificateShouldReturnTrue() {
        long certificateId = 9;
        when(certificateRepository.delete(anyLong())).thenReturn(true);
        boolean actualResult = certificateService.deleteCertificateById(certificateId);
        assertTrue(actualResult);
    }

    @Test
    void deleteCertificateByIdNotExistsCertificateShouldThrowException() {
        long certificateId = 9;
        when(certificateRepository.delete(anyLong())).thenReturn(false);
        assertThrows(ServiceException.class, () -> certificateService.deleteCertificateById(certificateId));
    }

    @Test
    void deleteCertificateByIdIncorrectIdCertificateShouldThrowException() {
        long incorrectCertificateId = -9;
        when(certificateRepository.delete(anyLong())).thenReturn(false);
        assertThrows(ServiceException.class, () -> certificateService.deleteCertificateById(incorrectCertificateId));
    }
}
