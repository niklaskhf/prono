package com.team16.sopra.sopra16team16.Controller;

/**
 * Sorts the results of a query
 */

public class Sorter {
    private static String direction = "ASC";
    private static String sortedBy = ContactManager.COLUMN_LASTNAME;
    private static Sorter currentInstance;

    public static Sorter getCurrentInstance() {
        if (currentInstance == null) {
            currentInstance = new Sorter();
            return currentInstance;
        } else {
            return currentInstance;
        }
    }

    private Sorter() {

    }

    public static String getDirection() {
        return direction;
    }

    public static String getSortedBy() {
        return sortedBy;
    }

    /**
     * Sets the direction for sorting the results. Only ASC or DESC
     */
    public static void setDirection(String newDirection) {
        if((!(newDirection.equals("ASC"))) && (!(newDirection.equals("DESC")))) {
            return;
        }
        direction = newDirection;
    }

    /**
     * Sorts the results by first or lastname
     */
    public static void setSortedBy(String newSortedBy) {
        if((!(newSortedBy.equals(ContactManager.COLUMN_LASTNAME))) &&
                (!(newSortedBy.equals(ContactManager.COLUMN_FIRSTNAME)))) {
            return;
        }
        sortedBy = newSortedBy;
    }
}
