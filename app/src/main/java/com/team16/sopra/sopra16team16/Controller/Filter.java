package com.team16.sopra.sopra16team16.Controller;

import java.util.ArrayList;

/**
 * help to filter contacts
 */

public class Filter {
    private static String country = null;
    private static ArrayList<String> gender = new ArrayList<String>();
    private static Filter currentInstance;

    public static Filter getCurrentInstance() {
        if (currentInstance == null) {
            currentInstance = new Filter();
            return currentInstance;
        } else {
            return currentInstance;
        }
    }

    private Filter() {
        //show all genders
        gender.add("MALE");
        gender.add("FEMALE");
        gender.add("UNKNOWN");
    }

    public static String getCountry() {
        return country;
    }

    /**
     * @return an ArrayList with all filtered genders
     */
    public static ArrayList<String> getGenderList() {
        return gender;
    }

    public static void setCountry(String newCountry) {
        country = newCountry;
    }

    /**
     * Sets or deletes a gender from the filterlist
     * @param newGender has to be "MALE, "FEMALE" or "UNKNOWN"
     * @return true if a gender was added to the filterlist, otherwise false
     */
    public static boolean setGender(String newGender) {
        if((!(newGender.equals("UNKNOWN"))) && (!(newGender.equals("FEMALE")))  &&
                (!(newGender.equals("MALE")))) {
            return false;
        }

        //add or delete gender from the array
        for(int i = 0; i < gender.size(); ++i) {
            if (gender.get(i).equals(newGender)) {
                gender.remove(i);
                return false;
            }
        }

        //gender wasn't in the filter list?
        gender.add(gender.size(), newGender);
        return true;
    }
}
