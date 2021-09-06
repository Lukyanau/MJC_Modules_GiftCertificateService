package com.epam.esm.repositoty.impl.query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static com.epam.esm.utils.CertificateSearchParameters.*;

public class SqlQueryCreator {

    private static final Logger LOGGER = LogManager.getLogger();


    public static String createQueryFromSearchParameters(Map<String, String> searchParams) {
        StringBuilder preparedQuery = new StringBuilder("SELECT id, name, description, price, duration, created, updated" +
                " FROM gift_certificate");
        createFromSearchParams(searchParams, preparedQuery);
        createFromSortParams(searchParams, preparedQuery);
        preparedQuery.append(";");
        LOGGER.info("Query created: " + preparedQuery);
        return preparedQuery.toString();
    }

    private static void createFromSearchParams(Map<String, String> searchParams, StringBuilder preparedQuery) {
        if (searchParams.containsKey(PART_OF_NAME) || searchParams.containsKey(PART_OF_DESCRIPTION)) {
            preparedQuery.append(" WHERE ");
            if (searchParams.containsKey(PART_OF_NAME)) {
                preparedQuery.append(" name LIKE '%").append(searchParams.get(PART_OF_NAME)).append("%' AND");
            }
            if (searchParams.containsKey(PART_OF_DESCRIPTION)) {
                preparedQuery.append(" description LIKE '%").append(searchParams.get(PART_OF_DESCRIPTION)).append("%' ");
            }
            if (preparedQuery.substring(preparedQuery.length() - 4).equalsIgnoreCase(" AND")) {
                preparedQuery.delete(preparedQuery.length() - 3, preparedQuery.length());
            }
        }
    }

    private static void createFromSortParams(Map<String, String> searchParams, StringBuilder preparedQuery) {
        if (searchParams.containsKey(SORT_BY)) {
            preparedQuery.append(" ORDER BY ").append(searchParams.get(SORT_BY));
        }
        if (searchParams.containsKey(SORT_TYPE)) {
            preparedQuery.append(" ").append(searchParams.get(SORT_TYPE));
        }
    }
}
