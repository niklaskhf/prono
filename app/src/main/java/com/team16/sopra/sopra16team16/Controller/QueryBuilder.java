package com.team16.sopra.sopra16team16.Controller;

/**
 * Contains methods returning SQL queries
 */

public class QueryBuilder {
    String[] cols;

    public QueryBuilder(String[] cols) {
        this.cols = cols;
    }
    /**
     * Constructs a search query that finds all rows containing every word in searchWords as substring
     *
     * @param searchWords String[], rows will be filtered based on every element
     * @return complete SELECT FROM WHEN query
     */
    public String buildSearchQuery(String[] searchWords) {
        String query_COLUMNS =
                "SELECT ";

        for (String s : cols) {
            query_COLUMNS = query_COLUMNS + s + ", ";
        }
        query_COLUMNS = query_COLUMNS.substring(0, query_COLUMNS.length() - 2);
        String query_TABLE = " FROM " + ContactManager.TABLE_NAME + " WHERE ";
        String query_EXPRESSION = "";
        for (int i = 0; i < searchWords.length; i++) {
            query_EXPRESSION = query_EXPRESSION +
                    query_COLUMNS + query_TABLE +
                    ContactManager.COLUMN_FIRSTNAME + " LIKE '" + searchWords[i] + "' OR " +
                    ContactManager.COLUMN_LASTNAME + " LIKE '" + searchWords[i] + "' OR " +
                    ContactManager.COLUMN_COUNTRY + " LIKE '" + searchWords[i] + "' OR " +
                    ContactManager.COLUMN_TITLE + " LIKE '" + searchWords[i] + "' OR " +
                    ContactManager.COLUMN_GENDER + " LIKE '" + searchWords[i] + "'" +
                    " INTERSECT ";
        }

        query_EXPRESSION = query_EXPRESSION.substring(0, query_EXPRESSION.length() - 11);
        query_EXPRESSION = query_EXPRESSION + ";";

        return query_EXPRESSION;
    }
}
