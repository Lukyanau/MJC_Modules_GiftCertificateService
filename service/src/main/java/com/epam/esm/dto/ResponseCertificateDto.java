package com.epam.esm.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseCertificateDto extends RequestCertificateDto {

    private LocalDateTime created;
    private LocalDateTime updated;

}
