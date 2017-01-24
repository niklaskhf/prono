package com.team16.sopra.sopra16team16.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;


/**
 * Manages the databases.
 */
public class DBManager {
    private static DBManager currentInstance;
    private SQLiteDatabase dbContacts;
    private DBHelper dbHelper;
    private Context context;


    /**
     * Singleton
     * @param context Context
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
     * @param context Context
     */
    private DBManager(Context context) {
        this.context=context.getApplicationContext();
        dbHelper = DBHelper.getCurrentInstance(context);
    }

    /**
     *
     * @return The SQLiteDatabase DBcontact
     */
    public SQLiteDatabase getDbContacts() {
        return dbContacts=DBHelper.getCurrentInstance(context).getWritableDatabase();
    }

    /**
     * Closes the database
     */
    public void close(){
        dbContacts.close();
    }

    /**
     * Reopens the database
     */
    public void reopen(){
        close();
        dbHelper = DBHelper.getCurrentInstance(context);
        dbContacts = dbHelper.getWritableDatabase();
    }

    /**
     * Calls the replaceDatabase method in DBHelper
     *
     * @return boolean - true if successful, false if not
     * @throws IOException
     */
    public boolean replaceDatabase(String dbPath) throws IOException {
        return dbHelper.replaceDatabase(dbPath);
    }
}
