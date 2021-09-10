package com.epam.esm.dto;

import java.math.BigDecimal;
import java.util.List;

public class RequestCertificateDto {

    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private List<TagDto> tags;

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

    public List<TagDto> getCertificateTags() {
        return tags;
    }

    public void setCertificateTags(List<TagDto> certificateTags) {
        this.tags = certificateTags;
    }
}
