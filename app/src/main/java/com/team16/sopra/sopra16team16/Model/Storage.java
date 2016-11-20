package com.team16.sopra.sopra16team16.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team16.sopra.sopra16team16.R;

import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Realizes read/writing of objects to SharedPreferences
 */

public class Storage {
    private Context context;

    /**
     * Constructor, sets context
     * @param context
     */
    public Storage(Context context) {
        this.context = context;
    }


    /**
     * Serializes a given object to a json-string and saves it to SharedPreferences
     * @param list
     */
    public void saveContactsJson(ArrayList<Contact> list) {
        // TODO GENERALIZE THIS, SO IT CAN BE USED FOR OTHER OBJECTS (settings)

        // set gson object
        Gson gson = new Gson();
        // generate json string for the list
        String listJson = gson.toJson(list);
        // get R.string.contactsFileKey entry from SharedPreferences
        // TODO maybe change to direct string or include as parameter
        SharedPreferences prefs = context.getSharedPreferences("contactsList", Context.MODE_PRIVATE);
        // get the editor
        SharedPreferences.Editor editor = prefs.edit();

        // write the new json string
        editor.putString("contactsList", listJson);
        editor.apply();
        //editor.commit(); for forced writing, apply() for background
    }

    /**
     * Loads the contacts from the SharedPreferences
     *
     * @return ArrayList with previous contacts
     */
    public ArrayList<Contact> loadContactsJson() {
        // TODO GENERALIZE THIS, SO IT CAN BE USED FOR OTHER OBJECTS (settings)
        Gson gson = new Gson();

        // declare list type to restore from json
        Type listType = new TypeToken<ArrayList<Contact>>(){}.getType();

        // set empty list as default
        String defaultPrefs = gson.toJson(new ArrayList<Contact>());

        // get the preferences
        SharedPreferences prefs = context.getSharedPreferences("contactsList", Context.MODE_PRIVATE);

        // get the string from the preferences
        String restoredJson = prefs.getString(
                "contactsList", gson.toJson(new ArrayList<Contact>()));

        if (gson.fromJson(restoredJson, listType) == null) {
            return new ArrayList<Contact>();
        }

        // deserialize json to ArrayList<Contact>

        return gson.fromJson(restoredJson, listType);
    }
}