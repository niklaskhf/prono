package com.team16.sopra.sopra16team16.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.team16.sopra.sopra16team16.Model.DBManager;
import com.team16.sopra.sopra16team16.View.HomeActivity;

import java.io.File;

/**
 * Contains methods for manipulating or receiving contact related data.
 */
public class ContactManager{
    private static ContactManager currentInstance = null;
    private DBManager dbManager;
    private ContactCursorAdapter cursorAdapter = null;
    private ContactCursorAdapter favoriteAdapter = null;
    private Context context;
    private QueryBuilder queryBuilder = null;
    private Recorder recorder;

    private static SQLiteDatabase database;

    public final static String TABLE_NAME = "contacts";

    public final static String _ID = "_id"; // id
    public static final String COLUMN_FIRSTNAME = "first";
    public static final String COLUMN_LASTNAME = "last";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_FAVORITE = "favorite";
    public static final String COLUMN_DELETED = "deleted";
    private int id;

    private String[] cols = new String[]{_ID, COLUMN_FIRSTNAME, COLUMN_LASTNAME, COLUMN_TITLE, COLUMN_COUNTRY, COLUMN_GENDER, COLUMN_FAVORITE, COLUMN_DELETED};

    /**
     * Returns the current and only instance of the ContactManager class
     *
     * @param context Context object
     * @return ContactManager object, used to manipulate and receive contact related data
     */
    public static ContactManager getInstance(Context context) {
        if (currentInstance == null) {
            currentInstance = new ContactManager(context);
            return currentInstance;
        } else {
            return currentInstance;
        }
    }

    /**
     * @param context Context object
     */
    private ContactManager(Context context) {
        dbManager = DBManager.getCurrentInstance(context);
        open();
        this.queryBuilder = new QueryBuilder(cols);
        this.context = context.getApplicationContext();

        Cursor lastIdCursor = database.rawQuery("SELECT MAX(_ID) FROM " + TABLE_NAME + ";", null);

        lastIdCursor.moveToFirst();
        if (lastIdCursor.getCount() != 0) {
            id = lastIdCursor.getInt(0);
        } else {
            id = 0;
        }
        lastIdCursor.close();
        Log.i("id", Integer.toString(id));
    }

    public int getId() {
        return id;
    }
    /**
     * Adds a new row to the table 'contacts'
     *
     * @param first   first name - String
     * @param last    last name - String
     * @param title   title - String
     * @param country country - String
     * @param gender  gender
     * @return
     */
    public long createContact(String first, String last, String title, String country, String gender) {
        long res = 0;
        database.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(_ID, ++id);
            values.put(COLUMN_FIRSTNAME, first);
            values.put(COLUMN_LASTNAME, last);
            values.put(COLUMN_TITLE, title);
            values.put(COLUMN_COUNTRY, country);
            values.put(COLUMN_GENDER, gender);
            values.put(COLUMN_FAVORITE, false);
            values.put(COLUMN_DELETED, false);
            res = database.insert(TABLE_NAME, null, values);
            database.setTransactionSuccessful();
        } catch(Exception e) {
            Log.i("createContactDb", e.getMessage());
        } finally {
            database.endTransaction();
        }
        this.updateCursorAdapter();
        return res;
    }

    /**
     * Executes query on the 'contacts' table, only selects rows with COLUMN_DELETED = 0
     *
     * @return Cursor with rows based on query results
     */
    public Cursor selectContacts() {
        Cursor mCursor = database.query(TABLE_NAME, cols, COLUMN_DELETED + " = 0"
                , null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; // iterate to get each value.
    }

    /**
     * Executes query on the 'contacts' table, only selects rows with COLUMN_DELETED = 0
     * and COLUMN_FAVORITE = 1
     *
     * @return Cursor with rows based on query results
     */
    public Cursor selectFavorites() {
        Cursor mCursor = database.query(TABLE_NAME, cols, COLUMN_DELETED + " = 0 AND " + COLUMN_FAVORITE + " = 1"
                , null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; // iterate to get each value.
    }

    /**
     * Updates a row with new values
     *
     * @param first   first name - String
     * @param last    last name - String
     * @param title   title - String
     * @param country country - String
     * @param gender  gender
     */
    public long updateContact(int id, String first, String last, String title, String country, String gender) {
        long res = 0;
        database.beginTransaction();
        try {
            String strFilter = "_id=" + id;
            ContentValues values = new ContentValues();
            values.put(COLUMN_FIRSTNAME, first);
            values.put(COLUMN_LASTNAME, last);
            values.put(COLUMN_TITLE, title);
            values.put(COLUMN_COUNTRY, country);
            values.put(COLUMN_GENDER, gender);

            // which row to update?
            //res = database.replace(TABLE_NAME, null, values);
            res = database.update(TABLE_NAME, values, strFilter, null);
            database.setTransactionSuccessful();
        } catch(Exception e) {
            Log.i("updateContactDb", e.getMessage());
        } finally {
            database.endTransaction();
        }
        this.updateCursorAdapter();
        return res;
    }


    /**
     * Inverts the favorite value of a contact/row
     *
     * @param id  unique id to identify the row
     * @param fav current value, result will be !fav
     */
    public int toggleFavorite(int id, int fav) {
        int res = 0;
        database.beginTransaction();
        try {
            Log.i("updating favorite", Integer.toString(id) + " " + Integer.toString(fav));
            String strFilter = "_id=" + id;
            // values to be changed
            ContentValues values = new ContentValues();
            values.put(COLUMN_FAVORITE, (fav + 1) % 2);

            // update row with new values
            res = database.update(TABLE_NAME, values, strFilter, null);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }

        this.updateCursorAdapter();
        this.getCursorAdapterDefault();
        return res;
    }

    /**
     * Inverts the deleted value of a contact/row
     *
     * @param id  unique id to identify the row
     * @param del current value, result will be !del
     */
    public int toggleDeleted(int id, int del) {
        if (id == -1) {
            throw new IllegalArgumentException("invalid id");
        }
        int res = 0;
        database.beginTransaction();
        try {
            Log.i("toggling deleted", "on " + Integer.toString(id) + " from " + Integer.toString(del) + " to " + (del+1) % 2);
            String strFilter = "_id=" + id;
            // values to be changed
            ContentValues values = new ContentValues();
            values.put(COLUMN_DELETED, (del + 1) % 2);

            // update row with new values
            res = database.update(TABLE_NAME, values, strFilter, null);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }

        this.updateCursorAdapter();
        return res;
    }

    /**
     * Removes a row from the database
     *
     * @param id unique id of row that will be removed
     */
    public int deleteContact(int id) {
        int res = 0;
        database.beginTransaction();
        try {
            res = database.delete(TABLE_NAME, "_id = ?", new String[]{Integer.toString(id)});
            Log.i("deleted", Integer.toString(id));
            database.setTransactionSuccessful();
            FileUtils.deleteFile(FileUtils.PATH + id + ".3gp");
        } catch(Exception e) {
            Log.i("deleteContactDb", e.getMessage());
        } finally {
            database.endTransaction();
        }
        this.updateCursorAdapter();
        return res;
    }

    /**
     * Deletes all rows marked for deletion.
     *
     */
    public void deleteMarked() {
        // get all marked rows
        Cursor mCursor = database.rawQuery("SELECT " + _ID + ", " + COLUMN_DELETED + " FROM " + TABLE_NAME + " WHERE " + COLUMN_DELETED + " = 1;", null);


        if (mCursor != null) {
            mCursor.moveToFirst();
            for (int i = 0; i < mCursor.getCount(); i++) {
                int row = mCursor.getInt(mCursor.getColumnIndexOrThrow(_ID));
                this.deleteContact(row);
                Log.d("deleteMarked", Integer.toString(row));
                i++;
                mCursor.moveToNext();
            }
        }
        mCursor.close();
    }


    /**
     * Returns a ContactCursorAdapter, populates menu_item
     *
     * @return ContactCursorAdapter
     */
    public ContactCursorAdapter getCursorAdapterDefault() {
        if (cursorAdapter == null) {
            cursorAdapter = new ContactCursorAdapter(context, selectContacts());
            return cursorAdapter;
        } else {
            cursorAdapter.changeCursor(selectContacts());
            return cursorAdapter;
        }
    }

    /**
     * Returns a ContactCursorAdapter, populates menu_item
     *
     * @return ContactCursorAdapter
     */
    public ContactCursorAdapter getCursorAdapterFavorite() {
        if (favoriteAdapter == null) {
            favoriteAdapter = new ContactCursorAdapter(context, selectFavorites());
            return cursorAdapter;
        } else {
            favoriteAdapter.changeCursor(selectFavorites());
            return cursorAdapter;
        }
    }



    /**
     * Update cursorAdapter with new dataset
     */
    public void updateCursorAdapter() {
        if (cursorAdapter != null) {
            cursorAdapter.changeCursor(selectContacts());
        }
    }


    /**
     * Queries the table 'contacts' for the search String.
     * Returns a cursor that includes all matches with any column.
     *
     * @param search search query - String
     * @return Cursor
     */
    public Cursor getSearchResults(String search) {
        // cursor to return
        Cursor cursor = null;
        // query to use
        String query;
        if (queryBuilder != null) {
            query = queryBuilder.buildSearchQuery(search);

        } else {
            throw new IllegalStateException("queryBuilder is null");
        }
        Log.i("filterQuery", query);

        // perform the query
        cursor = database.rawQuery(query, null);
        Log.i("filterCursor count", Integer.toString(cursor.getCount()));

        cursor.moveToFirst();
        return cursor;
    }


    /**
     * Closes the database connection.
     * Not really needed, handled by android kernel
     */
    /*
    public void close() {
        database.close();
    }*/

    /**
     * Opens a new database connection.
     */
    public void open() {
        database = dbManager.getDbContacts();
    }


    /**
     * wipes all rows from the database, used for testing
     */
    public void wipe() {
        // wipe the database
        database.delete(TABLE_NAME, null, null);

        // wipe the files
        File filesPath = new File("//data//data//" + HomeActivity.contextOfApplication.getPackageName()
                + "//files//");

        File[] files = filesPath.listFiles();

        for (int i = 1; i < files.length; i++) {
            FileUtils.deleteFile(files[i].getPath());
        }
        Toast.makeText(HomeActivity.contextOfApplication, "All data reset!", Toast.LENGTH_SHORT)
                .show();
    }
}
