package com.team16.sopra.sopra16team16;


/**
 * Created by moo on 11/11/2016.
 */

public class Item {
    private String firstName;
    private String lastName;
    private String title;
    private String country;


    public Item(String f, String l, String t, String c) {
        this.firstName = f;
        this.lastName = l;
        this.title = t;
        this.country = c;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTitle() {
        return title;
    }

    public String getCountry() {
        return country;
    }
}

