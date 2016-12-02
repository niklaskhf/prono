package com.team16.sopra.sopra16team16.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;



public class DBManager {
    SQLiteDatabase dbContacts;
    DBHelper dbHelper;

    public DBManager(Context context) {
        //context.deleteDatabase("DBcontact");
        dbHelper = DBHelper.getCurrentInstance(context);
    }

    public SQLiteDatabase getDbContacts() {
        return dbContacts=dbHelper.getWritableDatabase();
    }

    public void close() {
        dbContacts.close();
    }
}
