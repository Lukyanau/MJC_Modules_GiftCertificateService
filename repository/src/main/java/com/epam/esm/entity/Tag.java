package com.epam.esm.entity;

public class Tag extends Entity{

    public Tag() {
    }

    public Tag(String name) {
        super(name);
    }

    public Tag(long id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Tag: " + super.toString();
    }
}
