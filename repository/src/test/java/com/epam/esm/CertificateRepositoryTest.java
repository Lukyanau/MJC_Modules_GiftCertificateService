package com.epam.esm;

import com.epam.esm.configuration.TestRepositoryConfiguration;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestRepositoryConfiguration.class)
@ActiveProfiles("dev")
public class CertificateRepositoryTest {

    private final CertificateRepositoryImpl certificateRepository;

    @Autowired
    public CertificateRepositoryTest(CertificateRepositoryImpl certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @Test
    void findAllShouldReturnCorrectListSizeOfGiftCertificates() {
        int expectedResult = 3;

        int actualResult = certificateRepository.getAll(Collections.emptyMap(), 1, 3).get().size();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void findAllShouldReturnIncorrectListSizeOfGiftCertificates() {
        int expectedResult = 0;

        int actualResult = certificateRepository.getAll(Collections.emptyMap(), 0, 0).get().size();

        assertNotEquals(expectedResult, actualResult);
    }

    @Test
    void findByIdIncorrectIdShouldReturnEmptyOptional() {
        Optional<GiftCertificate> expectedOptional = Optional.empty();

        Optional<GiftCertificate> actualOptional = certificateRepository.getById(-1L);

        assertEquals(expectedOptional, actualOptional);
    }

    @Test
    void findIdByNameCorrectNameShouldReturnCorrectId() {
        long expectedId = 1;

        long actualId = certificateRepository.getCertificateIdByName("first");

        assertEquals(expectedId, actualId);
    }

    @Test
    void addCorrectDataShouldReturnGiftCertificate() {
        GiftCertificate expectedGiftCertificate = new GiftCertificate();
        expectedGiftCertificate.setName("New certificate");
        expectedGiftCertificate.setDescription("Just a free");
        expectedGiftCertificate.setPrice(new BigDecimal("1"));
        expectedGiftCertificate.setDuration(3);
        expectedGiftCertificate.setCreated(LocalDateTime
                .of(2020, 9, 12, 15, 0, 0));
        expectedGiftCertificate.setUpdated(LocalDateTime
                .of(2021, 1, 1, 17, 0, 0));
        expectedGiftCertificate.setTags(new ArrayList<>());

        GiftCertificate actualGiftCertificate = certificateRepository.add(expectedGiftCertificate);

        assertNotNull(actualGiftCertificate);
    }

    @Test
    void addIncorrectDataShouldThrowException() {
        GiftCertificate expectedGiftCertificate = new GiftCertificate();
        expectedGiftCertificate.setName("New certificate name incorrect because its can be less than 45 symbols USA ATALANTA");
        expectedGiftCertificate.setDescription("Just a free");
        expectedGiftCertificate.setPrice(new BigDecimal("100"));
        expectedGiftCertificate.setDuration(3);
        expectedGiftCertificate.setCreated(LocalDateTime
                .of(2020, 9, 12, 15, 0, 0));
        expectedGiftCertificate.setUpdated(LocalDateTime
                .of(2021, 1, 1, 17, 0, 0));
        expectedGiftCertificate.setTags(new ArrayList<>());

        assertThrows(DataIntegrityViolationException.class, () -> certificateRepository.add(expectedGiftCertificate));
    }

    @Test
    void updateCorrectDataShouldReturnUpdatedGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(4L);
        giftCertificate.setName("Marker");
        giftCertificate.setDescription("Spend money");
        giftCertificate.setPrice(new BigDecimal("175"));
        giftCertificate.setDuration(1);
        giftCertificate.setCreated(LocalDateTime
                .of(2020, 9, 12, 15, 0, 0));
        giftCertificate.setUpdated(LocalDateTime
                .of(2021, 1, 1, 17, 0, 0));
        giftCertificate.setTags(new ArrayList<>());

        GiftCertificate expectedGiftCertificate = new GiftCertificate();
        expectedGiftCertificate.setId(4L);
        expectedGiftCertificate.setName("Marker");
        expectedGiftCertificate.setDescription("Spend money");
        expectedGiftCertificate.setPrice(new BigDecimal("175"));
        expectedGiftCertificate.setDuration(1);
        expectedGiftCertificate.setCreated(LocalDateTime
                .of(2020, 9, 12, 15, 0, 0));
        expectedGiftCertificate.setUpdated(LocalDateTime
                .of(2021, 1, 1, 17, 0, 0));
        expectedGiftCertificate.setTags(new ArrayList<>());

        assertDoesNotThrow(() -> certificateRepository.updateCertificate(4L, giftCertificate));
    }

    @Test
    void updateIncorrectDataShouldThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1L);
        giftCertificate.setName("New certificate name incorrect because its can be less than 45 symbols USA ATALANTA");
        giftCertificate.setDescription("Spend money");
        giftCertificate.setPrice(new BigDecimal("175"));
        giftCertificate.setDuration(1);
        giftCertificate.setCreated(LocalDateTime
                .of(2020, 9, 12, 15, 0, 0));
        giftCertificate.setUpdated(LocalDateTime
                .of(2021, 1, 1, 17, 0, 0));
        giftCertificate.setTags(new ArrayList<>());

        assertThrows(DataIntegrityViolationException.class, () -> certificateRepository.updateCertificate(1, giftCertificate));
    }

    @Test
    void removeCorrectIdShouldNotThrowException() {
        assertDoesNotThrow(() -> certificateRepository.delete(1L));
    }

}
