package com.epam.esm.repositoty.impl.query;

public class NewSqlQuery {

    //Certificates
    public static final String DELETE_CERTIFICATE_BY_ID = "DELETE FROM GiftCertificate c WHERE c.id = :id";
    public static final String GET_CERTIFICATE_ID_BY_NAME = "SELECT c.id FROM GiftCertificate c WHERE c.name = :name";

    //Tags
    public static final String SELECT_ALL_TAGS = "SELECT t FROM Tag t ORDER BY t.id ASC";
    public static final String GET_TAG_BY_NAME = "SELECT t FROM Tag t WHERE t.name = :name";
    public static final String GET_TAG_ID_BY_NAME = "SELECT t.id FROM Tag t WHERE t.name = :name";
    public static final String DELETE_TAG_BY_ID = "DELETE FROM Tag t WHERE t.id = :id";

    //Cross Table certificate_tag
    public static final String CHECK_USED_TAGS = "SELECT count(*) FROM gift_tag WHERE tag_id= :id";

    //Users
    public static final String SELECT_ALL_USERS = "SELECT u FROM User u";
    public static final String UPDATE_BALANCE = "UPDATE USER set balance = :balance WHERE id = :id";
    public static final String FIND_MOST_USED_TAG_ID = "SELECT tag_id FROM gift_tag WHERE gift_id in " +
            "    (SELECT certificate_id FROM user_order WHERE user_id = " +
            "            (SELECT user_id FROM user_order GROUP BY user_id ORDER BY sum(cost) DESC LIMIT 1)) " +
            "                   GROUP BY tag_id ORDER BY count(*) DESC LIMIT 1";

    //Orders
    public static final String SELECT_ALL_ORDERS = "SELECT o FROM Order o";
    public static final String SELECT_ALL_ORDERS_BY_USER_ID = "SELECT o FROM Order o WHERE o.userId = :userId";

    private NewSqlQuery(){

    }
}
