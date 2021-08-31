package com.epam.esm.repositoty;

public class SqlQuery {
    //Certificate table
    public static final String ADD_CERTIFICATE = "INSERT INTO gift_certificate(name,description,price,duration,created,updated)" +
            " VALUES(?,?,?,?,?,?)";
    public static final String GET_ALL_CERTIFICATES = "SELECT * FROM gift_certificate";
    public static final String GET_CERTIFICATE_ID = "SELECT id FROM gift_certificate WHERE name = ?";
    public static final String GET_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id = ?";
    public static final String GET_CERTIFICATE_BY_NAME = "SELECT * FROM gift_certificate WHERE name = ?";
    public static final String DELETE_CERTIFICATE_BY_ID = "DELETE FROM gift_certificate WHERE id = ?";
    public static final String UPDATE_CERTIFICATE_BY_NAME = "UPDATE gift_certificate SET description = ?, price = ?," +
            " duration = ?, updated = ? WHERE name = ?";

    //Cross table
    public static final String ADD_CERTIFICATE_AND_TAG_IDS = "INSERT INTO gift_tag(gift_id, tag_id) VALUES(?,?)";

    //Tag table
    public static final String ADD_TAG = "INSERT INTO tag(name) VALUES (?)";
    public static final String GET_ALL_TAGS = "SELECT * FROM tag";
    public static final String GET_TAG_ID = "SELECT id FROM tag WHERE name=?";
    public static final String GET_TAG_BY_ID = "SELECT * FROM tag WHERE id=?";
    public static final String GET_TAG_BY_NAME = "SELECT * FROM tag WHERE name=?";
    public static final String GET_TAGS_BY_CERTIFICATE_ID = "SELECT tag_id FROM gift_tag WHERE gift_id=?";
    public static final String DELETE_TAG_BY_ID = "DELETE FROM tag WHERE id=?";

    private SqlQuery() {
    }
}
