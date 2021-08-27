package com.epam.esm.dto;

import com.epam.esm.entity.Tag;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ResponseCertificateDTO {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private LocalDateTime created;
    private LocalDateTime updated;
    private List<TagDTO> certificateTags;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

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

    public List<TagDTO> getCertificateTags() {
        return certificateTags;
    }

    public void setCertificateTags(List<TagDTO> certificateTags) {
        this.certificateTags = certificateTags;
    }
}
