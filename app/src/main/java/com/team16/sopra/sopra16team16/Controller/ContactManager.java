package com.team16.sopra.sopra16team16.Controller;

import android.content.Context;
import android.util.Log;

import com.team16.sopra.sopra16team16.Model.Contact;
import com.team16.sopra.sopra16team16.Model.Database;

/**
 * Contains methods for managing contacts.
 */

public class ContactManager {
    private static int contId;
    private Database db;
    private Context context;
    private ListAdapter listAdapter;

    /**
     * Constructor
     *
     * @param context
     * @param adapter
     */
    public ContactManager(Context context, ListAdapter adapter) {
        this.contId = 0;
        this.context = context;
        db = Database.getInstance(this.context);
        listAdapter = adapter;
    }

    /**
     * Adds a contact to the database
     * @param contact
     */
    public void addContact(Contact contact) {
        db.addContactJson(contact);
        listAdapter.notifyDataSetChanged();
    }
}
