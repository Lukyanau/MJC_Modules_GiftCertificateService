package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;

public class GiftCertificate extends Entity {

    private String description;
    private BigDecimal price;
    private int duration;
    private LocalDateTime created;
    private LocalDateTime updated;
    private List<Tag> tags;

    public GiftCertificate() {
        super();
    }

    public GiftCertificate(long id, String name, String description, BigDecimal price, int duration,
                           LocalDateTime created, LocalDateTime updated, List<Tag> tags) {
        super(id, name);
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.created = created;
        this.updated = updated;
        this.tags = tags;
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

    public List<Tag> getCertificateTags() {
        return tags;
    }

    public void setCertificateTags(List<Tag> certificateTags) {
        this.tags = certificateTags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        GiftCertificate that = (GiftCertificate) o;

        if (duration != that.duration) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (updated != null ? !updated.equals(that.updated) : that.updated != null) return false;
        return tags != null ? tags.equals(that.tags) : that.tags == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + duration;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GiftCertificate: " + super.toString() +
                " description = " + description  +
                ", price = " + price +
                ", duration = " + duration +
                ", created = " + created +
                ", updated = " + updated +
                ", tags = " + tags;
    }
}
