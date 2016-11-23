package com.team16.sopra.sopra16team16;

import com.team16.sopra.sopra16team16.Model.Contact;
import com.team16.sopra.sopra16team16.Model.Gender;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 *  Tests the Contact class
 */

public class ContactTest {

    @Test
    public void gettersSetters_test() {
        Contact contact = new Contact("first", "last", "title", "country", Gender.FEMALE, 0);

        assertTrue("getFirstGet", contact.getFirstName().equals("first"));
        assertTrue("getLastGet", contact.getLastName().equals("last"));
        assertTrue("getCountryGet", contact.getCountry().equals("country"));
        assertTrue("getTitleGet", contact.getTitle().equals("title"));
        assertTrue("getGenderGet", contact.getGender() == Gender.FEMALE);
        assertTrue("getIdGet", contact.getId() == 0);
        assertTrue("getFavoriteGet", contact.getFavorite() == false);
        assertTrue("getDeletedGet", contact.getDeleted() == false);


        contact.setFirstName("first2");
        contact.setLastName("last2");
        contact.setCountry("country2");
        contact.setGender(Gender.MALE);
        contact.setTitle("title2");
        contact.setFavorite(true);
        contact.setDeleted(true);

        assertTrue("getFirstSet", contact.getFirstName().equals("first2"));
        assertTrue("getLastSet", contact.getLastName().equals("last2"));
        assertTrue("getCountrySet", contact.getCountry().equals("country2"));
        assertTrue("getTitleSet", contact.getTitle().equals("title2"));
        assertTrue("getGenderSet", contact.getGender() == Gender.MALE);
        assertTrue("getFavoriteSet", contact.getFavorite() == true);
        assertTrue("getDeletedSet", contact.getDeleted() == true);
    }
}
