package com.team16.sopra.sopra16team16.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.team16.sopra.sopra16team16.Model.DBManager;
import com.team16.sopra.sopra16team16.Model.Gender;

/**
 * Contains methods for manipulating or receiving contact related data.
 */
public class ContactManager {
    private static ContactManager currentInstance = null;
    private DBManager dbManager;
    private ContactCursorAdapter cursorAdapter = null;
    private Context context;
    private QueryBuilder queryBuilder = null;

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
            currentInstance = new ContactManager(context.getApplicationContext());
            return currentInstance;
        } else {
            return currentInstance;
        }
    }

    /**
     * @param context Context object
     */
    private ContactManager(Context context) {
        dbManager = new DBManager(context.getApplicationContext());
        open();
        this.queryBuilder = new QueryBuilder(cols);
        this.context = context;

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
     * @param gender  gender - Gender
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
     * Executes query on the 'contacts' table
     *
     * @return Cursor with rows based on query results
     */
    public Cursor selectContacts() {
        // TODO ADD FILTERS AND SORT
        Cursor mCursor = database.query(true, TABLE_NAME, cols, null
                , null, null, null, null, null);
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
     * @param gender  gender - Gender
     */
    public long updateContact(String first, String last, String title, String country, String gender) {
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
            // TODO maybe use update, no real need to change fav/del
            //database.update(TABLE_NAME, values, strFilter, null);
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
        } catch(Exception e) {
            Log.i("deleteContactDb", e.getMessage());
        } finally {
            database.endTransaction();
        }

        this.updateCursorAdapter();
        return res;
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


        // not sure why this didn't work
        // only checked first expression
        /*////////////////////////////////////////////////////////////////////

        // get individual search words
        String[] searchWords = search.split(" ");

        // add wildcards to match if substring
        for (int i = 0; i < searchWords.length; i++) {
            searchWords[i] = "%" + searchWords[i] + "%";
        }

        // create query
        // columns to add in cursor
        // no idea why this query isnt working
        // arguments with wild cards
        // wild cards replaced by any entry in searchWords
        String query =
                COLUMN_FIRSTNAME + " LIKE ? OR " +
                COLUMN_LASTNAME + " LIKE ? OR " +
                COLUMN_TITLE + " LIKE ? OR " +
                COLUMN_COUNTRY + " LIKE ? OR " +
                COLUMN_GENDER + " LIKE ?";
        Log.i("query", query);

        // query database
        cursor = database.query(TABLE_NAME, cols, query,
              searchWords, null, null, null, null);

        ////////////////////////////////////////////////////////////////////*/


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
     */
    public void close() {
        dbManager.close();
    }

    /**
     * Opens a new database connection.
     */
    public void open() {
        database = dbManager.getDbContacts();
    }

}
