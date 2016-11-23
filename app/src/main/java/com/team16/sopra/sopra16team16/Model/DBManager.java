package com.team16.sopra.sopra16team16.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by moo on 11/22/16.
 */

public class DBManager {
    SQLiteDatabase dbContacts;
    DBHelper dbHelper;

    public DBManager(Context context) {
        dbHelper = DBHelper.getCurrentInstance(context);
        dbContacts = dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase getDbContacts() {
        return dbContacts;
    }
}