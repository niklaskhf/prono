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

        String testQueryNo = queryBuilder.buildSearchQuery("");
        String testQueryOne = queryBuilder.buildSearchQuery("hello");
        String testQueryMultiple = queryBuilder.buildSearchQuery("how are you");


        String expectedQueryNo =
                "SELECT test FROM " + ContactManager.TABLE_NAME + " WHERE " +
                        ContactManager.COLUMN_FIRSTNAME + " LIKE '%%' OR " +
                        ContactManager.COLUMN_LASTNAME + " LIKE '%%' OR " +
                        ContactManager.COLUMN_COUNTRY + " LIKE '%%' OR " +
                        ContactManager.COLUMN_TITLE + " LIKE '%%' OR " +
                        ContactManager.COLUMN_GENDER + " LIKE '%%';";
        String expectedQueryOne =
                "SELECT test FROM " + ContactManager.TABLE_NAME + " WHERE " +
                        ContactManager.COLUMN_FIRSTNAME + " LIKE '%hello%' OR " +
                        ContactManager.COLUMN_LASTNAME + " LIKE '%hello%' OR " +
                        ContactManager.COLUMN_COUNTRY + " LIKE '%hello%' OR " +
                        ContactManager.COLUMN_TITLE + " LIKE '%hello%' OR " +
                        ContactManager.COLUMN_GENDER + " LIKE '%hello%';";

        String expectedQueryMultiple =
                "SELECT test FROM " + ContactManager.TABLE_NAME + " WHERE " +
                        ContactManager.COLUMN_FIRSTNAME + " LIKE '%how%' OR " +
                        ContactManager.COLUMN_LASTNAME + " LIKE '%how%' OR " +
                        ContactManager.COLUMN_COUNTRY + " LIKE '%how%' OR " +
                        ContactManager.COLUMN_TITLE + " LIKE '%how%' OR " +
                        ContactManager.COLUMN_GENDER + " LIKE '%how%'" +
                " INTERSECT " +
                        "SELECT test FROM " + ContactManager.TABLE_NAME + " WHERE " +
                        ContactManager.COLUMN_FIRSTNAME + " LIKE '%are%' OR " +
                        ContactManager.COLUMN_LASTNAME + " LIKE '%are%' OR " +
                        ContactManager.COLUMN_COUNTRY + " LIKE '%are%' OR " +
                        ContactManager.COLUMN_TITLE + " LIKE '%are%' OR " +
                        ContactManager.COLUMN_GENDER + " LIKE '%are%'" +
                " INTERSECT " +
                        "SELECT test FROM " + ContactManager.TABLE_NAME + " WHERE " +
                        ContactManager.COLUMN_FIRSTNAME + " LIKE '%you%' OR " +
                        ContactManager.COLUMN_LASTNAME + " LIKE '%you%' OR " +
                        ContactManager.COLUMN_COUNTRY + " LIKE '%you%' OR " +
                        ContactManager.COLUMN_TITLE + " LIKE '%you%' OR " +
                        ContactManager.COLUMN_GENDER + " LIKE '%you%';";

        assertTrue("No words query is false", expectedQueryNo.equals(testQueryNo));
        assertTrue("One word query is false", expectedQueryOne.equals(testQueryOne));
        assertTrue("Multiple word query is false", expectedQueryMultiple.equals(testQueryMultiple));




    }
}
