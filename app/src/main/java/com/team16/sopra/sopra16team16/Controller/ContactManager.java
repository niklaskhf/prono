package com.team16.sopra.sopra16team16.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.support.v4.widget.CursorAdapter;

import com.team16.sopra.sopra16team16.Model.DBManager;
import com.team16.sopra.sopra16team16.Model.Gender;


public class ContactManager {
    private static ContactManager currentInstance = null;
    private DBManager dbManager;
    private ContactCursorAdapter cursorAdapter = null;
    private SearchCursorAdapter searchAdapter = null;
    private Context context;

    private static SQLiteDatabase database;

    public final static String TABLE_NAME ="contacts";

    public final static String _ID ="_id"; // id
    public static final String COLUMN_FIRSTNAME = "first";
    public static final String COLUMN_LASTNAME = "last";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_FAVORITE = "favorite";
    public static final String COLUMN_DELETED = "deleted";

    public static ContactManager getInstance(Context context) {
        if (currentInstance == null) {
            currentInstance = new ContactManager(context.getApplicationContext());
            return currentInstance;
        } else {
            return currentInstance;
        }
    }

    /**
     *
     * @param context
     */
    private ContactManager(Context context){
        dbManager = new DBManager(context);
        database = dbManager.getDbContacts();
        this.context = context;
    }


    public long createContact(String first, String last, String title, String country, Gender gender){
        ContentValues values = new ContentValues();
        // id auto increments
        values.put(COLUMN_FIRSTNAME, first);
        values.put(COLUMN_LASTNAME, last);
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_COUNTRY, country);
        values.put(COLUMN_GENDER, gender.toString());
        values.put(COLUMN_FAVORITE, false);
        values.put(COLUMN_DELETED, false);
        Long res =  database.insert(TABLE_NAME, null, values);

        this.updateCursorAdapter();
        return res;
    }

    public Cursor selectContacts() {
        String[] cols = new String[] {_ID, COLUMN_FIRSTNAME, COLUMN_LASTNAME, COLUMN_TITLE, COLUMN_COUNTRY, COLUMN_GENDER, COLUMN_FAVORITE, COLUMN_DELETED};
        Cursor mCursor = database.query(true, TABLE_NAME,cols,null
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; // iterate to get each value.
    }

    public void updateContact(String first, String last, String title, String country, Gender gender, boolean fav, boolean del, int id) {
        String strFilter = "_id=" + id;
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTNAME, first);
        values.put(COLUMN_LASTNAME, last);
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_COUNTRY, country);
        values.put(COLUMN_GENDER, gender.toString());
        values.put(COLUMN_FAVORITE, fav);
        values.put(COLUMN_DELETED, del);

        // which row to update?
        database.replace(TABLE_NAME, null, values);
        //database.update(TABLE_NAME, values, strFilter, null);

        this.updateCursorAdapter();
    }


    public void toggleFavorite(int id, int fav) {
        database.beginTransaction();
        try {
        Log.i("updating favorite", Integer.toString(id) + " " + Integer.toString(fav));
        String strFilter = "_id=" + id;
        ContentValues values = new ContentValues();
        values.put(COLUMN_FAVORITE, (fav+1)%2);

        int res = database.update(TABLE_NAME, values, strFilter, null);
            Log.i("favorited" , Integer.toString(res));
        database.setTransactionSuccessful();}
        finally {
            database.endTransaction();
        }

        this.updateCursorAdapter();
    }

    public void deleteContact(int id) {
        int res = database.delete(TABLE_NAME, "_id = ?", new String[] {Integer.toString(id)});
        Log.i("deleted", Integer.toString(id));
        this.updateCursorAdapter();
    }

    public ContactCursorAdapter getCursorAdapterDefault() {
        if (cursorAdapter == null) {
            cursorAdapter = new ContactCursorAdapter(context, selectContacts());
            return cursorAdapter;
        } else {
            cursorAdapter.changeCursor(selectContacts());
            return cursorAdapter;
        }
    }

    public void updateCursorAdapter() {
        if (cursorAdapter != null) {
            cursorAdapter.changeCursor(selectContacts());
        }
        if (searchAdapter != null) {
            searchAdapter.changeCursor(selectContacts());
        }
    }

    public SearchCursorAdapter getSearchAdapter() {
        if (searchAdapter == null) {
            searchAdapter = new SearchCursorAdapter(context, selectContacts());
            return searchAdapter;
        } else {
            searchAdapter.changeCursor(selectContacts());
            return searchAdapter;
        }
    }
}
