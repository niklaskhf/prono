package com.team16.sopra.sopra16team16.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prime on 17.11.16.
 */
public class Database {
    private static Database ourInstance = new Database();
    private ArrayList<Contact> contactList = new ArrayList<Contact>();

    public static Database getInstance() {
        return ourInstance;
    }

    private Database() {
    }

    public ArrayList<Contact> getContact() {
        return contactList;
    }
}
