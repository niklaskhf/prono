package com.team16.sopra.sopra16team16.Controller;

/**
 * help to filter contacts
 */

public class Filter {
    private static String country = null;
    private static String gender = null;
    private static Filter currentInstance;

    public static Filter getCurrentInstance() {
        if (currentInstance == null) {
            currentInstance = new Filter();
            return currentInstance;
        } else {
            return currentInstance;
        }
    }

    public static String getCountry() {
        return country;
    }

    public static String getGender() {
        return gender;
    }

    public static void setCountry(String newCountry) {
        country = newCountry;
    }

    public static void setGender(String newGender) {
        if((!(newGender.equals("UNKNOWN"))) && (!(newGender.equals("FEMALE")))  &&
                (!(newGender.equals("MALE")))) {
            return;
        }
        gender = newGender;
    }
}
