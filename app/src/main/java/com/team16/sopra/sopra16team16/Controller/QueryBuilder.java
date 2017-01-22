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
    public String buildSearchQuery(String search, Filter filter, Sorter sorter) {
        // get individual search words
        String[] searchWords = search.split(" ");

        // add wildcards to match if substring
        for (int i = 0; i < searchWords.length; i++) {
            searchWords[i] = "%" + searchWords[i] + "%";
        }
        for (String s : searchWords) {
            Log.i("searchWords", s);

        }

        //Expression for the filter, empty if there is no filter
        String filterExpression = buildFilterExpression(filter);

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
                    query_COLUMNS + query_TABLE + "(" +
                    ContactManager.COLUMN_FIRSTNAME + " LIKE '" + searchWords[i] + "' OR " +
                    ContactManager.COLUMN_LASTNAME + " LIKE '" + searchWords[i] + "' OR " +
                    ContactManager.COLUMN_COUNTRY + " LIKE '" + searchWords[i] + "' OR " +
                    ContactManager.COLUMN_TITLE + " LIKE '" + searchWords[i] + "')" +
                    //ContactManager.COLUMN_GENDER + " LIKE '" + searchWords[i] + "')" +
                    filterExpression +
                    " INTERSECT ";
        }

        // cut out the last INTERSECT
        // should probably be using a StringBuilder
        query_EXPRESSION = query_EXPRESSION.substring(0, query_EXPRESSION.length() - 11);

        //expression for sorting
        String sorterExpression = buildSorterExpression(sorter);
        query_EXPRESSION = query_EXPRESSION + sorterExpression;

        // finish query
        query_EXPRESSION = query_EXPRESSION + ";";

        return query_EXPRESSION;
    }

    /*
     * if there is a filter, it adds an expression for it
     */
    private String buildFilterExpression(Filter filter) {
        String result = "";
        if(filter.getCountry() != null) {
            result += " AND " + ContactManager.COLUMN_COUNTRY + " = '" + filter.getCountry() + "' AND ";
        }

        if(filter.getGenderList().size() != 0) {

            result += "(" + ContactManager.COLUMN_GENDER + " = '" + filter.getGenderList().get(0) + "'";

            for(int i = 1; i < filter.getGenderList().size(); ++i) {
                result += " OR " + ContactManager.COLUMN_GENDER + " = '" + filter.getGenderList().get(i) + "'";
            }
        }

        return result;
    }

    /**
     *
     * @param filter
     * @return
     */
    private String buildDefaultFilter(Filter filter) {
        String result = "";
        if(filter.getCountry() != null) {
            result += ContactManager.COLUMN_COUNTRY + " = '" + filter.getCountry() + "' AND ";
        }

        if(filter.getGenderList().size() != 0) {

            result += "(" + ContactManager.COLUMN_GENDER + " = '" + filter.getGenderList().get(0) + "'";

            for(int i = 1; i < filter.getGenderList().size(); ++i) {
                result += " OR " + ContactManager.COLUMN_GENDER + " = '" + filter.getGenderList().get(i) + "'";
            }
            result += ") AND ";
        }
        else {
            result = result.substring(result.length() - 4);
        }
        result += ContactManager.COLUMN_DELETED + " = 0";

        return result;
    }

    /*
     * if there is a filter, it adds an expression for it
     */
    private String buildFavoriteFilter(Filter filter) {
        String result = "";
        if(filter.getCountry() != null) {
            result += ContactManager.COLUMN_COUNTRY + " = '" + filter.getCountry() + "' AND ";
        }

        if(filter.getGenderList().size() != 0) {

            result += "(" + ContactManager.COLUMN_GENDER + " = '" + filter.getGenderList().get(0) + "'";

            for(int i = 1; i < filter.getGenderList().size(); ++i) {
                result += " OR " + ContactManager.COLUMN_GENDER + " = '" + filter.getGenderList().get(i) + "'";
            }
            result += ") AND ";
        }
        else {
            result = result.substring(result.length() - 4);
        }
        result += ContactManager.COLUMN_DELETED + " = 0 AND " + ContactManager.COLUMN_FAVORITE + " = 1";

        return result;
    }


    /**
     * Builds a default SQL query using the filter/sorter and returning all rows not marked as deleted.
     * @return
     */
    public String defaultExpression(Filter filter, Sorter sorter) {
        String query_COLUMNS =
                "SELECT ";
        // construct column String SELECT column column ...
        for (String s : cols) {
            query_COLUMNS = query_COLUMNS + s + ", ";
        }
        // cut out the last comma
        query_COLUMNS = query_COLUMNS.substring(0, query_COLUMNS.length() - 2);
        // FROM TABLE_NAME WHERE
        String query_TABLE = query_COLUMNS + " FROM " + ContactManager.TABLE_NAME + " WHERE ";

        query_TABLE = query_TABLE + buildDefaultFilter(filter);
        query_TABLE = query_TABLE + buildSorterExpression(sorter);

        return query_TABLE + ";";
    }

    /**
     * Builds a SQL query using the filter/sorter and returning all rows
     * not marked as deleted and marked as favorite.
     * @return
     */
    public String favoriteExpression(Filter filter, Sorter sorter) {
        String query_COLUMNS =
                "SELECT ";
        // construct column String SELECT column column ...
        for (String s : cols) {
            query_COLUMNS = query_COLUMNS + s + ", ";
        }
        // cut out the last comma
        query_COLUMNS = query_COLUMNS.substring(0, query_COLUMNS.length() - 2);
        // FROM TABLE_NAME WHERE
        String query_TABLE = query_COLUMNS + " FROM " + ContactManager.TABLE_NAME + " WHERE ";

        query_TABLE = query_TABLE + buildFavoriteFilter(filter);
        query_TABLE = query_TABLE + buildSorterExpression(sorter);

        return query_TABLE + ";";
    }

    private String buildSorterExpression(Sorter sorter) {
        String result;
        result = " ORDER BY " + sorter.getSortedBy() + " " + sorter.getDirection();
        return result;
    }
}
