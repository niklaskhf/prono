package com.team16.sopra.sopra16team16.Controller;

import android.util.Log;

/**
 * Contains methods for building SQL queries
 */
public class QueryBuilder {
    private String[] cols;

    /**
     * Initialize with the table columns
     * @param cols - columns of interest
     */
    public QueryBuilder(String[] cols) {
        this.cols = cols;
    }

    /**
     * Constructs a search query that finds all rows containing every word in searchWords as substring
     *
     * @param search search query from search bar - String
     * @param filter Filter object
     * @param sorter Sorter object
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
        String sorterExpression = buildSorterExpression();
        query_EXPRESSION = query_EXPRESSION + " ORDER BY " + sorterExpression;

        // finish query
        query_EXPRESSION = query_EXPRESSION + ";";

        return query_EXPRESSION;
    }

    /**
     * if there is a filter, it adds an expression for it
     *
     * @param filter Filter object
     * @return returns the filter WHERE part of the SQL query
     */
    private String buildFilterExpression(Filter filter) {
        String result = " AND " + ContactManager.COLUMN_DELETED + " = 0";
        if(filter.getCountry() != null) {
            result += " AND " + ContactManager.COLUMN_COUNTRY + " = '" + filter.getCountry() + "'";
        }

        if(filter.getGenderList().size() != 0) {

            result += " AND (" + ContactManager.COLUMN_GENDER + " = '" + filter.getGenderList().get(0) + "'";

            for(int i = 1; i < filter.getGenderList().size(); ++i) {
                result += " OR " + ContactManager.COLUMN_GENDER + " = '" + filter.getGenderList().get(i) + "'";
            }
            result += ")";
        }

        return result;
    }


    /**
     * Builds the WHERE segment of a default query, asking for everything that is not marked for deletion
     *
     * @return Returns the WHERE part of a default SQL query (adds filter arguments)
     */
    public String defaultWhere() {
        Filter filter = Filter.getCurrentInstance();

        String deleted = ContactManager.COLUMN_DELETED + " = 0";

        //country filter
        String country = "";
        if (filter.getCountry() != null) {
            country = " AND " + ContactManager.COLUMN_COUNTRY + " = '" + filter.getCountry() + "'";
        }

        //gender filter
        String gender = "";
        if(filter.getGenderList().size() != 0) {

            gender += " AND (" + ContactManager.COLUMN_GENDER + " = '" + filter.getGenderList().get(0) + "'";

            for(int i = 1; i < filter.getGenderList().size(); ++i) {
                gender += " OR " + ContactManager.COLUMN_GENDER + " = '" + filter.getGenderList().get(i) + "'";
            }
            gender += ")";
        }

        Log.d("whereDefault", deleted + country + gender);
        return deleted + country + gender;
    }

    /**
     * Builds the WHERE segment of a favorite query, asking for everything that is not marked for deletion and marked as favorite
     *
     * @return returns the WHERE part of a favorite SQL query (considers filter and favorite arguments)
     */
    public String favoriteWhere() {
        Filter filter = Filter.getCurrentInstance();

        String deleted = ContactManager.COLUMN_DELETED + " = 0 AND " + ContactManager.COLUMN_FAVORITE + " = 1";

        String country = "";
        if (filter.getCountry() != null) {
            country = " AND " + ContactManager.COLUMN_COUNTRY + " = '" + filter.getCountry() + "'";
        }

        String gender = "";

        if(filter.getGenderList().size() != 0) {

            gender += " AND (" + ContactManager.COLUMN_GENDER + " = '" + filter.getGenderList().get(0) + "'";

            for(int i = 1; i < filter.getGenderList().size(); ++i) {
                gender += " OR " + ContactManager.COLUMN_GENDER + " = '" + filter.getGenderList().get(i) + "'";
            }
            gender += ")";
        }

        Log.d("whereFavorite", deleted + country + gender);
        return deleted + country + gender;
    }


    /**
     * Builds the sorting part for SQL queries
     *
     * @return sorting part of SQL query (based on Sorter object status)
     */
    public String buildSorterExpression() {
        Sorter sorter = Sorter.getCurrentInstance();
        String result;
        result = sorter.getSortedBy() + " " + sorter.getDirection();
        return result;
    }
}
