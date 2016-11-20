package com.team16.sopra.sopra16team16.Controller;

import android.content.Context;
import android.content.SharedPreferences;
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
    private SharedPreferences prefs;

    /**
     * Constructor
     *
     * @param context
     * @param adapter
     */
    public ContactManager(Context context, ListAdapter adapter) {
        //prefs = context.getSharedPreferences("contId", Context.MODE_PRIVATE);
        //this.contId = prefs.getInt("contId", 0);
        this.context = context;
        db = Database.getInstance(this.context);
        listAdapter = adapter;
    }


    /**
     * Adds a contact to the database
     * @param first
     * @param last
     * @param title
     * @param country
     * @param gender
     * @param favorite
     */
    public void addContact(String first, String last, String title, String country, String gender, String favorite) {
        // TODO set object every time or just once in constructor and it updates?
        prefs = context.getSharedPreferences("contId", Context.MODE_PRIVATE);
        contId = prefs.getInt("contId", 0);
        db.addContactJson(new Contact(first, last, title, country, gender, favorite, contId++));
        listAdapter.notifyDataSetChanged();

        // update contId in SharedPreferences
        SharedPreferences myPrefs = context.getSharedPreferences("contId", Context.MODE_PRIVATE);

        SharedPreferences.Editor e = myPrefs.edit();
        e.putInt("contId", contId);
        e.apply();
        // commit() for forced, apply() for background

        listAdapter.notifyDataSetChanged();
    }
}
