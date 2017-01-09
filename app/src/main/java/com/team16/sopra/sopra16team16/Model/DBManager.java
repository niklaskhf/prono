package com.team16.sopra.sopra16team16.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * Manages the databases/settings.
 */
public class DBManager {
    private static DBManager currentInstance;
    private SQLiteDatabase dbContacts;
    private DBHelper dbHelper;
    private Context context;


    /**
     * Singleton
     * @param context
     * @return returns the only instance of this class
     */
    public static DBManager getCurrentInstance(Context context) {
        if (currentInstance == null) {
            currentInstance = new DBManager(context.getApplicationContext());
            return currentInstance;
        } else {
            return currentInstance;
        }
    }

    /**
     * Constructor
     * @param context
     */
    private DBManager(Context context) {
        dbHelper = DBHelper.getCurrentInstance(context);
    }

    /**
     *
     * @return The SQLiteDatabase DBcontact
     */
    public SQLiteDatabase getDbContacts() {
        return dbContacts=dbHelper.getWritableDatabase();
    }

    /* dont actually need this, android handles closing
    public void close() {
        dbHelper.close();
    }

    public void reopen() {
        dbHelper = DBHelper.getCurrentInstance(context);
        dbContacts = dbHelper.getWritableDatabase();
    }*/
}
