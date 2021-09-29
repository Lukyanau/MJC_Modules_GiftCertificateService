package com.epam.esm.repository.impl.query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

import static com.epam.esm.utils.CertificateSearchParameters.*;
import static com.epam.esm.utils.GlobalCustomConstants.NAME;

public class SqlQueryCreator {

  private static final Logger LOGGER = LogManager.getLogger();

  public static String createQueryFromCertificateSearchParameters(
      Map<String, String> searchParams) {
    StringBuilder preparedQuery = new StringBuilder("SELECT c FROM GiftCertificate c");
    createFromSearchParams(searchParams, preparedQuery);
    createFromSortParams(searchParams, preparedQuery);
    LOGGER.info("Query created: " + preparedQuery);
    return preparedQuery.toString();
  }

  public static String createQueryForSearchCertificatesByTags(List<String> tagNames) {
    StringBuilder preparedQuery =
        new StringBuilder(
            "SELECT * from gift_certificate WHERE id in "
                + "(SELECT gift_id FROM gift_tag JOIN tag t on gift_tag.tag_id = t.id WHERE name IN (");
    tagNames.forEach(tagName -> preparedQuery.append("'").append(tagName).append("',"));
    preparedQuery.delete(preparedQuery.length() - 1, preparedQuery.length());
    preparedQuery.append(") GROUP BY gift_id HAVING count(*)=").append(tagNames.size()).append(")");
    return preparedQuery.toString();
  }

  public static String createQueryFromTagSearchParameters(Map<String, String> searchParams) {
    StringBuilder preparedQuery = new StringBuilder("SELECT t FROM Tag t");
    if (searchParams.containsKey(NAME)) {
      preparedQuery.append(" WHERE t.name = '").append(searchParams.get(NAME)).append("'");
    }
    preparedQuery.append(" ORDER BY t.id ASC");
    return preparedQuery.toString();
  }

  private static void createFromSearchParams(
      Map<String, String> searchParams, StringBuilder preparedQuery) {
    if (searchParams.containsKey(NAME) || searchParams.containsKey(DESCRIPTION)) {
      preparedQuery.append(" WHERE ");
      if (searchParams.containsKey(NAME)) {
        preparedQuery
            .append(" c.name LIKE '%")
            .append(searchParams.get(NAME).trim())
            .append("%' AND");
      }
      if (searchParams.containsKey(DESCRIPTION)) {
        preparedQuery
            .append(" c.description LIKE '%")
            .append(searchParams.get(DESCRIPTION).trim())
            .append("%' ");
      }
      if (preparedQuery.substring(preparedQuery.length() - 4).equalsIgnoreCase(" AND")) {
        preparedQuery.delete(preparedQuery.length() - 3, preparedQuery.length());
      }
    }
  }

  private static void createFromSortParams(
      Map<String, String> searchParams, StringBuilder preparedQuery) {
    if (searchParams.containsKey(ORDER_BY)) {
      preparedQuery.append(" ORDER BY ").append(searchParams.get(ORDER_BY).trim());
    }
    if (searchParams.containsKey(SORT)) {
      preparedQuery.append(" ").append(searchParams.get(SORT).trim());
    }
  }
}
