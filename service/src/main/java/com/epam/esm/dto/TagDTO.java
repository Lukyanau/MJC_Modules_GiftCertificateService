package com.epam.esm.dto;

public class TagDTO {

    public TagDTO() {
    }

    public TagDTO(String name) {
        this.name = name;
    }

    public TagDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    private long id;
    private String name;

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

}
