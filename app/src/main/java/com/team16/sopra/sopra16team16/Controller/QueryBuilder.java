package com.team16.sopra.sopra16team16.Controller;

import android.util.Log;

/**
 * Contains methods returning SQL queries
 */

public class QueryBuilder {
    private String[] cols;

    public QueryBuilder(String[] cols) {
        this.cols = cols;
    }
    /**
     * Constructs a search query that finds all rows containing every word in searchWords as substring
     *
     * @param search search query from search bar - String
     * @return complete SELECT FROM WHEN query
     */
    public String buildSearchQuery(String search) {
        // get individual search words
        String[] searchWords = search.split(" ");

        // add wildcards to match if substring
        for (int i = 0; i < searchWords.length; i++) {
            searchWords[i] = "%" + searchWords[i] + "%";
        }
        for (String s : searchWords) {
            Log.i("searchWords", s);

        }

        // construct query

        String query_COLUMNS =
                "SELECT ";
        // construct column String SELECT column column ...
        for (String s : cols) {
            query_COLUMNS = query_COLUMNS + s + ", ";
        }
        // cut out the last comma
        query_COLUMNS = query_COLUMNS.substring(0, query_COLUMNS.length() - 2);
        // FROM TABLE_NAME WHERE
        String query_TABLE = " FROM " + ContactManager.TABLE_NAME + " WHERE ";
        String query_EXPRESSION = "";
        // expressions INTERSECT SELECT ... FROM ... WHERE expression INTERSECT ...
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

        // cut out the last INTERSECT
        // should probably be using a StringBuilder
        query_EXPRESSION = query_EXPRESSION.substring(0, query_EXPRESSION.length() - 11);
        // finish query
        query_EXPRESSION = query_EXPRESSION + ";";

        return query_EXPRESSION;
    }
}
