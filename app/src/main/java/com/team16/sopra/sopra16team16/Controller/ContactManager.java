package com.team16.sopra.sopra16team16.Controller;

import com.team16.sopra.sopra16team16.Model.Contact;
import com.team16.sopra.sopra16team16.Model.Database;

import java.util.ArrayList;

/**
 * Created by moo on 11/17/16.
 */

public class ContactManager {
    private static int contId;
    Database db;

    public ContactManager() {
        this.contId = 0;
        db = Database.getInstance();
    }

    public void addContact(String first, String last, String country, String title, String gender) {
        Contact contact = new Contact(first, last, title, country, gender, "false", contId++);
        db.addContact(contact);

    }
}
