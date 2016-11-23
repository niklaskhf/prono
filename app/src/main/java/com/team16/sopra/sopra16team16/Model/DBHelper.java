package com.team16.sopra.sopra16team16.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.team16.sopra.sopra16team16.Controller.ContactManager;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper currentInstance = null;

    private static final String DATABASE_NAME = "DBcontact";

    private static final int DATABASE_VERSION = 2;

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContactManager.TABLE_NAME + " (" +
                    ContactManager._ID + " INTEGER PRIMARY KEY," +
                    ContactManager.COLUMN_FIRSTNAME + TEXT_TYPE + COMMA_SEP +
                    ContactManager.COLUMN_LASTNAME + TEXT_TYPE + COMMA_SEP +
                    ContactManager.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
                    ContactManager.COLUMN_COUNTRY + TEXT_TYPE + COMMA_SEP +
                    ContactManager.COLUMN_GENDER + TEXT_TYPE + COMMA_SEP +
                    // TODO CHANGE FLAGS TO INTEGER
                    ContactManager.COLUMN_FAVORITE + " INTEGER" + COMMA_SEP +
                    ContactManager.COLUMN_DELETED + " INTEGER" + " )";

    public static DBHelper getCurrentInstance(Context context) {
        if (currentInstance == null) {
            currentInstance = new DBHelper(context.getApplicationContext());
            return currentInstance;
        } else {
            return currentInstance;
        }
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_ENTRIES);
    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(database);
    }
}