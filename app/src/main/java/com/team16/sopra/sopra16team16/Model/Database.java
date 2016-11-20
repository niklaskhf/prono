package com.team16.sopra.sopra16team16.Model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Contains all permanent data relevant to the application
 */
public class Database {
    private static Database ourInstance = null;
    private static ArrayList<Contact> contactList;
    private Gson gson = new Gson();
    private Context context;
    private Storage storage;

    public static Database getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new Database(context);
            return ourInstance;
        } else {
            return ourInstance;
        }
    }

    private Database(Context context) {
        this.context = context;
        this.storage = new Storage(context);
        this.initializeJson();
    }

    public ArrayList<Contact> getContact() {
        return contactList;
    }


    public void initializeJson() {
        contactList = storage.loadContactsJson();
    }

    public void addContactJson(Contact contact) {
        contactList.add(contact);
        Log.i("contactAdded", "added new contact, id: " + contact.getId());
        storage.saveContactsJson(contactList);
    }

    public void updateContacts() {
        storage.saveContactsJson(contactList);
    }
}
