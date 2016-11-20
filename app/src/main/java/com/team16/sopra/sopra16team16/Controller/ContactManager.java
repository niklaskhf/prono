package com.team16.sopra.sopra16team16.Controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.team16.sopra.sopra16team16.Model.Contact;
import com.team16.sopra.sopra16team16.Model.Database;
import com.team16.sopra.sopra16team16.Model.Gender;

/**
 * Contains methods for managing contacts.
 */

public class ContactManager {
    private int contId;
    private Database db;
    private Context context;
    private ContactListAdapter listAdapter = null;
    private SharedPreferences prefs;

    /**
     * Constructor
     *
     * @param context
     * @param adapter
     */
    public ContactManager(Context context, ContactListAdapter adapter) {
        //prefs = context.getSharedPreferences("contId", Context.MODE_PRIVATE);
        //this.contId = prefs.getInt("contId", 0);
        this.context = context;
        db = Database.getInstance(this.context);
        listAdapter = adapter;
    }

    /**
     * Constructor when parameters are not required
     */
    public ContactManager(Context context) {
        db = Database.getInstance(this.context);
        this.context = context;
    }


    /**
     * Adds a contact to the database
     * @param first
     * @param last
     * @param title
     * @param country
     * @param gender
     */
    public void addContact(String first, String last, String title, String country, Gender gender) {
        // TODO set object every time or just once in constructor and it updates?
        prefs = context.getSharedPreferences("contId", Context.MODE_PRIVATE);
        contId = prefs.getInt("contId", 0);
        db.addContactJson(new Contact(first, last, title, country, gender, contId++));
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }

        // update contId in SharedPreferences
        SharedPreferences myPrefs = context.getSharedPreferences("contId", Context.MODE_PRIVATE);

        SharedPreferences.Editor e = myPrefs.edit();
        e.putInt("contId", contId);
        e.apply();
        // commit() for forced, apply() for background
    }

    public void toggleFavorite(Contact contact) {
        if (!contact.getFavorite()) {
            contact.setFavorite(true);
        } else {
            contact.setFavorite(false);
        }

        db.updateContacts();


    }

    public void toggleDelete(Contact contact) {
        if (!contact.getDeleted()) {
            contact.setDeleted(true);
        } else {
            contact.setDeleted(false);
        }

        db.updateContacts();
    }
}
