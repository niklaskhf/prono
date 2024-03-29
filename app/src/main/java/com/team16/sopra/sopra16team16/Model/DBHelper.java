package com.team16.sopra.sopra16team16.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.Controller.FileUtils;
import com.team16.sopra.sopra16team16.View.HomeActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * DBHelper, used to get the SQLiteDatabase object
 */
public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper currentInstance = null;
    public static final String DATABASE_NAME = "DBcontact";
    private static String DB_FILEPATH;

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

    /**
     * Singleton to avoid open/closed connection chaos
     * @param context Context
     * @return current and only instance of the class
     */
    public static DBHelper getCurrentInstance(Context context) {
        if (currentInstance == null) {
            currentInstance = new DBHelper(context);
            return currentInstance;
        } else {
            return currentInstance;
        }
    }

    /**
     * Constructor
     * @param context Context
     */
    private DBHelper(Context context) {
        super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
        DB_FILEPATH = "/data/data/"+ context.getPackageName() +"/databases/"+ DATABASE_NAME;
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) { database.execSQL(SQL_CREATE_ENTRIES); }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(database);
    }


    /**
     * Copies the database file at the specified location over the current
     * internal application database.
     *
     * @return boolean - true if successful, false if not
     * @throws IOException
     * */
    public boolean replaceDatabase(String dbPath) throws IOException {

        // Close the SQLiteOpenHelper so it will commit the created empty
        // database to internal storage.
        close();
        File newDb = new File(dbPath);
        File oldDb = new File(DB_FILEPATH);

        if (newDb.exists()) {
            FileUtils.copyFile(new FileInputStream(newDb), new FileOutputStream(oldDb));
            // Access the copied database so SQLiteHelper will cache it and mark
            // it as created.
            getWritableDatabase().isOpen();
            currentInstance = new DBHelper(HomeActivity.contextOfApplication);
            return true;
        }
        return false;
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}