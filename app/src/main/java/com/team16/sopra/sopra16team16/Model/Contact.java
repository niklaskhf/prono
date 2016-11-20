package com.team16.sopra.sopra16team16.Model;


/**
 * Contact class containing all the information about the contact
 */

public class Contact {
    private int id;
    private String firstName;
    private String lastName;
    private String title;

    // TODO implement some kind of java.util.Locale?
    private String country;
    private Gender gender;

    private boolean favoriteFlag;
    private boolean deletedFlag;

    /**
     * Constructor
     * @param first
     * @param last
     * @param title
     * @param country
     * @param gender
     * @param id
     */
    public Contact(String first, String last, String title, String country, Gender gender,  int id) {
        this.id = id;
        this.firstName = first;
        this.lastName = last;
        this.title = title;
        this.country = country;
        this.gender = gender;
        this.favoriteFlag = false;
        this.deletedFlag = false;
    }


    /**
     * Returns the firstName attribute of the Contact object
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the lastName attribute of the Contact object
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the title attribute of the Contact object
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the country attribute of the Contact object
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Returns the gender attribute of the Contact object
     * @return gender
     */
    public Gender getGender() { return gender;}

    /**
     * Returns the favoriteFlag attribute of the Contact object
     * @return favoriteFlag
     */
    public boolean getFavorite() { return favoriteFlag;}

    /**
     * Returns the id attribute of the Contact object
     * @return id
     */
    public int getId() { return id;}

    /**
     * Returns the deletedFlag attribute of the Contact object
     * @return deletedFlag
     */
    public boolean getDeleted() {
        return deletedFlag;
    }

    /**
     * Sets the firstName attribute
     * @param first
     */
    public void setFirstName(String first) {
        this.firstName = first;
    }

    /**
     * Sets the lastName attribute
     * @param last
     */
    public void setLastName(String last) {
        this.lastName = last;
    }

    /**
     * Sets the title attribute
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the country attribute
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Sets the gender attribute
     * @param gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Sets the favoriteFlag attribute
     * @param bool
     */
    public void setFavorite(boolean bool) {
        this.favoriteFlag = bool;
    }

    /**
     * Sets the deletedFlag attribute
     * @param bool
     */
    public void setDeleted(boolean bool) {
        this.deletedFlag = bool;
    }
}

