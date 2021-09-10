package com.epam.esm.dto;

import java.time.LocalDateTime;

public class ResponseCertificateDto extends RequestCertificateDto {

    private LocalDateTime created;
    private LocalDateTime updated;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }


}
