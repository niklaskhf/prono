package com.team16.sopra.sopra16team16.Model;


/**
 * Contact class containing all the information about the contact
 */

public class Contact {
    private int id;
    private String firstName;
    private String lastName;
    private String title;

    // TODO change this to country enum?
    private String country;

    // TODO change this to gender enum?
    private String gender;

    private boolean favoriteFlag;
    private boolean deleteFlag;

    public Contact(String first, String last, String title, String country, String gender,  int id) {
        this.id = id;
        this.firstName = first;
        this.lastName = last;
        this.title = title;
        this.country = country;
        this.gender = gender;
        this.favoriteFlag = false;
        this.deleteFlag = false;
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

    public boolean getDelete() {
        return deleteFlag;
    }

    public void setFirstName(String first) {
        this.firstName = first;
    }

    public void setLastName(String last) {
        this.lastName = last;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFavorite(boolean bool) {
        this.favoriteFlag = bool;
    }

    public void setDelete(boolean bool) {
        this.deleteFlag = bool;
    }
}

