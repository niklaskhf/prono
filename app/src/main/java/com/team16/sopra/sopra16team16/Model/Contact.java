package com.team16.sopra.sopra16team16.Model;


/**
 * Created by moo on 11/11/2016.
 */

public class Contact {
    private String firstName;
    private String lastName;
    private String title;
    private String country;
    private String gender;


    public Contact(String f, String l, String t, String c, String g) {
        this.firstName = f;
        this.lastName = l;
        this.title = t;
        this.country = c;
        this.gender = g;
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

    public String getGender() { return gender;}
}

