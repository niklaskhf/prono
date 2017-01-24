package com.team16.sopra.sopra16team16.Controller;

import android.util.Log;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the QueryBuilder class
 */
public class QueryBuilderTest {

    @Test
    public void buildSearchQueryTest() {
        String[] cols = new String[]{"test"};
        QueryBuilder queryBuilder = new QueryBuilder(cols);
        Filter filter = Filter.getCurrentInstance();
        Sorter sorter = Sorter.getCurrentInstance();

        String testQueryNo = queryBuilder.buildSearchQuery("");
        String testQueryOne = queryBuilder.buildSearchQuery("hello");
        String testQueryMultiple = queryBuilder.buildSearchQuery("how are you");


        String expectedQueryNo =
                "SELECT test FROM " + ContactManager.TABLE_NAME + " WHERE (" +
                        ContactManager.COLUMN_FIRSTNAME + " LIKE '%%' OR " +
                        ContactManager.COLUMN_LASTNAME + " LIKE '%%' OR " +
                        ContactManager.COLUMN_COUNTRY + " LIKE '%%' OR " +
                        ContactManager.COLUMN_TITLE + " LIKE '%%') " +
                        "AND " + ContactManager.COLUMN_DELETED + " = 0 " +
                        "AND (" + ContactManager.COLUMN_GENDER + " = 'MALE' OR " +
                        ContactManager.COLUMN_GENDER + " = 'FEMALE' OR " +
                        ContactManager.COLUMN_GENDER + " = 'UNKNOWN') " +
                        "ORDER BY last ASC;";
        String expectedQueryOne =
                "SELECT test FROM " + ContactManager.TABLE_NAME + " WHERE (" +
                        ContactManager.COLUMN_FIRSTNAME + " LIKE '%hello%' OR " +
                        ContactManager.COLUMN_LASTNAME + " LIKE '%hello%' OR " +
                        ContactManager.COLUMN_COUNTRY + " LIKE '%hello%' OR " +
                        ContactManager.COLUMN_TITLE + " LIKE '%hello%') " +
                        "AND " + ContactManager.COLUMN_DELETED + " = 0 " +
                        "AND (" + ContactManager.COLUMN_GENDER + " = 'MALE' OR " +
                        ContactManager.COLUMN_GENDER + " = 'FEMALE' OR " +
                        ContactManager.COLUMN_GENDER + " = 'UNKNOWN') " +
                        "ORDER BY last ASC;";

        String expectedQueryMultiple =
                "SELECT test FROM " + ContactManager.TABLE_NAME + " WHERE (" +
                        ContactManager.COLUMN_FIRSTNAME + " LIKE '%how%' OR " +
                        ContactManager.COLUMN_LASTNAME + " LIKE '%how%' OR " +
                        ContactManager.COLUMN_COUNTRY + " LIKE '%how%' OR " +
                        ContactManager.COLUMN_TITLE + " LIKE '%how%') " +
                        "AND " + ContactManager.COLUMN_DELETED + " = 0 " +
                        "AND (" + ContactManager.COLUMN_GENDER + " = 'MALE' OR " +
                        ContactManager.COLUMN_GENDER + " = 'FEMALE' OR " +
                        ContactManager.COLUMN_GENDER + " = 'UNKNOWN')" +
                " INTERSECT " +
                        "SELECT test FROM " + ContactManager.TABLE_NAME + " WHERE (" +
                        ContactManager.COLUMN_FIRSTNAME + " LIKE '%are%' OR " +
                        ContactManager.COLUMN_LASTNAME + " LIKE '%are%' OR " +
                        ContactManager.COLUMN_COUNTRY + " LIKE '%are%' OR " +
                        ContactManager.COLUMN_TITLE + " LIKE '%are%') " +
                        "AND " + ContactManager.COLUMN_DELETED + " = 0 " +
                        "AND (" + ContactManager.COLUMN_GENDER + " = 'MALE' OR " +
                        ContactManager.COLUMN_GENDER + " = 'FEMALE' OR " +
                        ContactManager.COLUMN_GENDER + " = 'UNKNOWN')" +
                " INTERSECT " +
                        "SELECT test FROM " + ContactManager.TABLE_NAME + " WHERE (" +
                        ContactManager.COLUMN_FIRSTNAME + " LIKE '%you%' OR " +
                        ContactManager.COLUMN_LASTNAME + " LIKE '%you%' OR " +
                        ContactManager.COLUMN_COUNTRY + " LIKE '%you%' OR " +
                        ContactManager.COLUMN_TITLE + " LIKE '%you%') " +
                        "AND " + ContactManager.COLUMN_DELETED + " = 0 " +
                        "AND (" + ContactManager.COLUMN_GENDER + " = 'MALE' OR " +
                        ContactManager.COLUMN_GENDER + " = 'FEMALE' OR " +
                        ContactManager.COLUMN_GENDER + " = 'UNKNOWN') " +
                        "ORDER BY last ASC;";

        //some filter and sorter tests
        filter.setGender("MALE");
        sorter.setSortedBy("first");
        sorter.setDirection("DESC");
        String testQueryFilter = queryBuilder.buildSearchQuery("filter");

        String expectedQueryFilter =
                "SELECT test FROM " + ContactManager.TABLE_NAME + " WHERE (" +
                        ContactManager.COLUMN_FIRSTNAME + " LIKE '%filter%' OR " +
                        ContactManager.COLUMN_LASTNAME + " LIKE '%filter%' OR " +
                        ContactManager.COLUMN_COUNTRY + " LIKE '%filter%' OR " +
                        ContactManager.COLUMN_TITLE + " LIKE '%filter%') " +
                        "AND " + ContactManager.COLUMN_DELETED + " = 0 " +
                        "AND (" +
                        ContactManager.COLUMN_GENDER + " = 'FEMALE' OR " +
                        ContactManager.COLUMN_GENDER + " = 'UNKNOWN') " +
                        "ORDER BY first DESC;";

        filter.setGender("FEMALE");
        filter.setGender("UNKNOWN");
        String testQueryNoFilter = queryBuilder.buildSearchQuery("nofilter");

        String expectedQueryNoFilter =
                "SELECT test FROM " + ContactManager.TABLE_NAME + " WHERE (" +
                        ContactManager.COLUMN_FIRSTNAME + " LIKE '%nofilter%' OR " +
                        ContactManager.COLUMN_LASTNAME + " LIKE '%nofilter%' OR " +
                        ContactManager.COLUMN_COUNTRY + " LIKE '%nofilter%' OR " +
                        ContactManager.COLUMN_TITLE + " LIKE '%nofilter%') " +
                        "AND " + ContactManager.COLUMN_DELETED + " = 0 " +
                        "ORDER BY first DESC;";

        assertTrue("No words query is false", expectedQueryNo.equals(testQueryNo));
        assertTrue("One word query is false", expectedQueryOne.equals(testQueryOne));
        assertTrue("Multiple word query is false", expectedQueryMultiple.equals(testQueryMultiple));
        assertTrue("Filter is false", expectedQueryFilter.equals(testQueryFilter));
        assertTrue("No filter is false", expectedQueryNoFilter.equals(testQueryNoFilter));

        filter.resetFilter();

        filter.setCountry("ger");
        String testQueryCountry = queryBuilder.buildSearchQuery("country");

        String expectedQueryCountry =
                "SELECT test FROM " + ContactManager.TABLE_NAME + " WHERE (" +
                        ContactManager.COLUMN_FIRSTNAME + " LIKE '%country%' OR " +
                        ContactManager.COLUMN_LASTNAME + " LIKE '%country%' OR " +
                        ContactManager.COLUMN_COUNTRY + " LIKE '%country%' OR " +
                        ContactManager.COLUMN_TITLE + " LIKE '%country%') " +
                        "AND " + ContactManager.COLUMN_DELETED + " = 0 " +
                        "AND " + ContactManager.COLUMN_COUNTRY + " = 'ger' " +
                        "AND (" + ContactManager.COLUMN_GENDER + " = 'MALE' OR " +
                        ContactManager.COLUMN_GENDER + " = 'FEMALE' OR " +
                        ContactManager.COLUMN_GENDER + " = 'UNKNOWN') " +
                        "ORDER BY last ASC;";

        assertTrue("country query is false", expectedQueryCountry.equals(testQueryCountry));

        filter.resetFilter();
    }
}
