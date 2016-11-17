package com.team16.sopra.sopra16team16.Model;


/**
 * Created by moo on 11/11/2016.
 */

public class Contact {
    private int id;
    private String firstName;
    private String lastName;
    private String title;
    private String country;
    private String gender;
    private boolean favoriteFlag;
// private boolean deleteFlag;

    public Contact(String first, String last, String title, String country, String gender, String favorite, int id) {
        this.id = id;
        this.firstName = first;
        this.lastName = last;
        this.title = title;
        this.country = country;
        this.gender = gender;
        this.favoriteFlag = Boolean.getBoolean(favorite);
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

    public boolean getFavorite() { return favoriteFlag;}

    public int getId() { return id;}
}

