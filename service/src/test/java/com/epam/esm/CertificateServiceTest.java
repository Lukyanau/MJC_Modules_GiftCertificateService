package com.epam.esm;

import com.epam.esm.dto.RequestCertificateDto;
import com.epam.esm.dto.ResponseCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.repository.CertificateRepository;
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
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CertificateServiceTest.class)
public class CertificateServiceTest {

  private CertificateRepository certificateRepository;
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
    searchValidator = new SearchParamsValidator();
    baseValidator = new BaseValidator();
    certificateService =
        new CertificateServiceImpl(
            certificateRepository,
            certificateValidator,
            certificateMapper,
            searchValidator,
            baseValidator,
            tagRepository,
            tagValidator) {};
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
    when(certificateRepository.getAll(Collections.emptyMap(), 1, 5))
        .thenReturn(Optional.of(Arrays.asList(giftCertificate1, giftCertificate2)));
    int expectedSize = 2;
    int actualSize = certificateService.getCertificates(Collections.emptyMap(), 1, 5).size();
    assertEquals(expectedSize, actualSize);
  }

  @Test
  void findAllNoCertificatesShouldThrowException() {
    when(certificateRepository.getAll(Collections.emptyMap(), 0, 0)).thenReturn(Optional.empty());
    assertThrows(
        ServiceException.class,
        () -> certificateService.getCertificates(Collections.emptyMap(), 0, 0));
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
    when(certificateMapper.convertToDto(any(GiftCertificate.class)))
        .thenReturn(expectedCertificate);

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
  void addCertificateCorrectDataShouldReturnCertificate() {
    RequestCertificateDto requestCertificate =
        new RequestCertificateDto(
            1, "name", "description", BigDecimal.valueOf(200), 66, Collections.emptyList());
    GiftCertificate giftCertificate =
        new GiftCertificate(
            1L,
            "name",
            "description",
            BigDecimal.valueOf(200),
            66,
            LocalDateTime.now(),
            LocalDateTime.now(),
            Collections.emptyList());
    ResponseCertificateDto responseCertificate =
        new ResponseCertificateDto(
            1L,
            "name",
            "description",
            BigDecimal.valueOf(200),
            66,
            Collections.emptyList(),
            LocalDateTime.now(),
            LocalDateTime.now());
    when(certificateMapper.convertToDto(any())).thenReturn(responseCertificate);
    when(certificateMapper.convertToEntity(any())).thenReturn(giftCertificate);
    when(certificateRepository.add(any())).thenReturn(giftCertificate);
    when(certificateRepository.getCertificateIdByName(anyString()))
        .thenReturn(giftCertificate.getId());

    ResponseCertificateDto actualCertificate =
        certificateService.addCertificate(requestCertificate);

    assertEquals(actualCertificate, responseCertificate);
  }

  @Test
  void addCertificateIncorrectDataShouldThrowException() {
    RequestCertificateDto requestCertificate =
        new RequestCertificateDto(
            1, ".", "description", BigDecimal.valueOf(200), 999, Collections.emptyList());
    assertThrows(
        ServiceException.class, () -> certificateService.addCertificate(requestCertificate));
  }

  @Test
  void getCertificateByTagsCorrectDataShouldReturnCorrectList() {
    Map<String, String> tags = new HashMap<>();
    tags.put("tag", "aaaa");
    ResponseCertificateDto responseCertificate =
        new ResponseCertificateDto(
            1L,
            "name",
            "description",
            BigDecimal.valueOf(200),
            66,
            Collections.emptyList(),
            LocalDateTime.now(),
            LocalDateTime.now());
    List<String> tagName = new ArrayList<>(List.of("aaaa"));
    List<GiftCertificate> listCertificates =
        new ArrayList<>(
            Arrays.asList(
                new GiftCertificate(
                    1L,
                    "name1",
                    "description",
                    BigDecimal.valueOf(200),
                    66,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    Collections.emptyList()),
                new GiftCertificate(
                    2L,
                    "name2",
                    "description",
                    BigDecimal.valueOf(222),
                    55,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    Collections.emptyList())));
    when(certificateRepository.getByTags(tagName, 1, 5)).thenReturn(Optional.of(listCertificates));
    when(certificateMapper.convertToDto(any())).thenReturn(responseCertificate);
    int expectedSize = 2;

    int actualSize = certificateService.getCertificatesByTags(tags, 1, 5).size();

    assertEquals(expectedSize, actualSize);
  }

  @Test
  void getCertificateByTagsIncorrectMapDataShouldThrowException() {
    assertThrows(
        ServiceException.class,
        () -> certificateService.getCertificates(Collections.emptyMap(), 1, 5));
  }

  @Test
  void getCertificateByTagsIncorrectPaginationDataShouldThrowException() {
    Map<String, String> tags = new HashMap<>();
    tags.put("tag", "aaaa");
    assertThrows(ServiceException.class, () -> certificateService.getCertificates(tags, 0, 0));
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
    when(certificateMapper.convertToEntity(any(RequestCertificateDto.class)))
        .thenReturn(giftCertificate);
    when(certificateRepository.getCertificateIdByName(anyString())).thenReturn(certificateId);
    when(certificateMapper.convertToDto(any(GiftCertificate.class)))
        .thenReturn(expectedCertificate);
    ResponseCertificateDto actualCertificate =
        certificateService.updateCertificate(certificateId, requestCertificate);
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
    assertThrows(
        ServiceException.class,
        () -> certificateService.updateCertificate(certificateId, requestCertificate));
  }

  @Test
  void patchUpdateCertificateCorrectDataShouldReturnResponseCertificateDto() {
    RequestCertificateDto requestCertificate = new RequestCertificateDto();
    requestCertificate.setId(10L);
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
    when(certificateMapper.convertToEntity(any(RequestCertificateDto.class)))
        .thenReturn(giftCertificate);
    when(certificateMapper.convertToDto(any(GiftCertificate.class)))
        .thenReturn(expectedCertificate);
    doNothing().when(certificateRepository).updateCertificate(giftCertificate);

    ResponseCertificateDto actualCertificate =
        certificateService.patchUpdateCertificate(10, requestCertificate);

    assertEquals(actualCertificate, expectedCertificate);
  }

  @Test
  void patchUpdateCertificateIncorrectDataShouldThrowException() {
    long certificateId = 10L;
    RequestCertificateDto requestCertificate = new RequestCertificateDto();
    requestCertificate.setId(10L);
    requestCertificate.setName("FirstCertificate");
    requestCertificate.setDescription("Cafe certificate");
    requestCertificate.setPrice(BigDecimal.valueOf(1000));
    requestCertificate.setDuration(90);
    requestCertificate.setTags(Collections.emptyList());
    assertThrows(
        ServiceException.class,
        () -> certificateService.patchUpdateCertificate(certificateId, requestCertificate));
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
    assertThrows(
        ServiceException.class, () -> certificateService.deleteCertificateById(certificateId));
  }

  @Test
  void deleteCertificateByIdIncorrectIdCertificateShouldThrowException() {
    long incorrectCertificateId = -9;
    when(certificateRepository.delete(anyLong())).thenReturn(false);
    assertThrows(
        ServiceException.class,
        () -> certificateService.deleteCertificateById(incorrectCertificateId));
  }
}
