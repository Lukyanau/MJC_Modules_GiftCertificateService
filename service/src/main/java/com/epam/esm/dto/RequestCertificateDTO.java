package com.epam.esm.dto;

import com.epam.esm.entity.Tag;

import java.math.BigDecimal;
import java.util.List;

public class RequestCertificateDTO {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private List<Tag> certificateTags;

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

    public List<Tag> getCertificateTags() {
        return certificateTags;
    }

    public void setCertificateTags(List<Tag> certificateTags) {
        this.certificateTags = certificateTags;
    }
}
