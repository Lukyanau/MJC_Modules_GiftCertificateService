package com.epam.esm.repositoty.impl.query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static com.epam.esm.utils.CertificateSearchParameters.*;

public class SqlQueryCreator {

    private static final Logger LOGGER = LogManager.getLogger();


    public static String createQueryFromSearchParameters(Map<String, String> searchParams) {
        StringBuilder preparedQuery = new StringBuilder("SELECT c FROM GiftCertificate c");
        createFromSearchParams(searchParams, preparedQuery);
        createFromSortParams(searchParams, preparedQuery);
        LOGGER.info("Query created: " + preparedQuery);
        return preparedQuery.toString();
    }

    private static void createFromSearchParams(Map<String, String> searchParams, StringBuilder preparedQuery) {
        if (searchParams.containsKey(NAME) || searchParams.containsKey(DESCRIPTION)) {
            preparedQuery.append(" WHERE ");
            if (searchParams.containsKey(NAME)) {
                preparedQuery.append(" c.name LIKE '%").append(searchParams.get(NAME).trim()).append("%' AND");
            }
            if (searchParams.containsKey(DESCRIPTION)) {
                preparedQuery.append(" c.description LIKE '%").append(searchParams.get(DESCRIPTION).trim()).append("%' ");
            }
            if (preparedQuery.substring(preparedQuery.length() - 4).equalsIgnoreCase(" AND")) {
                preparedQuery.delete(preparedQuery.length() - 3, preparedQuery.length());
            }
        }
    }

    private static void createFromSortParams(Map<String, String> searchParams, StringBuilder preparedQuery) {
        if (searchParams.containsKey(ORDER_BY)) {
            preparedQuery.append(" ORDER BY ").append(searchParams.get(ORDER_BY).trim());
        }
        if (searchParams.containsKey(SORT)) {
            preparedQuery.append(" ").append(searchParams.get(SORT).trim());
        }
    }
}
