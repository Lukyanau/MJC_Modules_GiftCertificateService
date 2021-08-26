package com.epam.esm.service.impl;

import com.epam.esm.dto.RequestCertificateDTO;
import com.epam.esm.dto.ResponseCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectInputParametersException;
import com.epam.esm.exception.InvalidIdException;
import com.epam.esm.exception.InvalidNameException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.exception_code.ExceptionWithCode;
import com.epam.esm.model_mapper.CertificateMapper;
import com.epam.esm.repositoty.impl.CertificateRepositoryImpl;
import com.epam.esm.service.CertificateService;
import com.epam.esm.validator.CertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {
    private static final CertificateServiceImpl instance = new CertificateServiceImpl();

    private CertificateServiceImpl() {
    }

    public static CertificateServiceImpl getInstance() {
        return instance;
    }

    CertificateValidator certificateValidator = CertificateValidator.getInstance();
    TagValidator tagValidator = TagValidator.getInstance();
    CertificateRepositoryImpl certificateRepository = CertificateRepositoryImpl.getInstance();
    CertificateMapper certificateMapper = new CertificateMapper();

    @Override
    public List<ResponseCertificateDTO> getCertificates() throws NotFoundException {
        List<GiftCertificate> allCertificates = certificateRepository.getAll();
        List<ResponseCertificateDTO> allResponseCertificateDTOs = new ArrayList<>();
        if (allCertificates != null) {
            for (GiftCertificate giftCertificate : allCertificates) {
                allResponseCertificateDTOs.add(certificateMapper.convertToDTO(giftCertificate));
            }
            return allResponseCertificateDTOs;
        }
        throw new NotFoundException(ExceptionWithCode.NOT_FOUND_CERTIFICATES.toString());
    }

    @Override
    public ResponseCertificateDTO getCertificateById(long id) throws InvalidIdException, NotFoundException {
        certificateValidator.checkCertificateDTOId(id);
        GiftCertificate giftCertificate = certificateRepository.getById(id);
        if (giftCertificate == null) {
            throw new NotFoundException(ExceptionWithCode.CERTIFICATE_WITH_ID_NOT_FOUND.toString());
        }
        return certificateMapper.convertToDTO(giftCertificate);
    }


    @Override
    public ResponseCertificateDTO getCertificateByName(String name) throws InvalidNameException, NotFoundException {
        certificateValidator.checkCertificateDTOName(name);
        GiftCertificate giftCertificate = certificateRepository.getByName(name);
        if (giftCertificate == null) {
            throw new NotFoundException(ExceptionWithCode.CERTIFICATE_WITH_NAME_NOT_FOUND.toString());
        }
        return certificateMapper.convertToDTO(giftCertificate);
    }

    @Override
    public ResponseCertificateDTO addCertificate(RequestCertificateDTO certificateDTO)
            throws IncorrectInputParametersException, InvalidNameException {
        certificateValidator.validateCertificateDTO(certificateDTO);
        for (Tag tag: certificateDTO.getCertificateTags()) {
            tagValidator.checkTagDTOName(tag.getName());
        }
        GiftCertificate currentCertificate = certificateMapper.convertToEntity(certificateDTO);
        LocalDateTime currentDataTime = LocalDateTime.now();
        currentCertificate.setCreated(currentDataTime);
        currentCertificate.setUpdated(currentDataTime);
        GiftCertificate addedCertificate = certificateRepository.add(currentCertificate);
        return certificateMapper.convertToDTO(addedCertificate);
    }

    @Override
    public ResponseCertificateDTO updateCertificate(RequestCertificateDTO certificateDTO) {
        return null;
    }

    @Override
    public boolean deleteCertificateById(long id) throws NotFoundException, InvalidIdException {
        GiftCertificate currentCertificate = certificateMapper.convertToEntity(getCertificateById(id));
        return certificateRepository.delete(currentCertificate.getId());
    }
}
