package com.team16.sopra.sopra16team16.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.support.v4.widget.CursorAdapter;

import com.team16.sopra.sopra16team16.Model.DBManager;
import com.team16.sopra.sopra16team16.Model.Gender;

/**
 * Contains methods for manipulating or receiving contact related data.
 */
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

    /**
     * Returns the current and only instance of the ContactManager class
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
     *
     * @param context Context object
     */
    private ContactManager(Context context){
        dbManager = new DBManager(context.getApplicationContext());
        database = dbManager.getDbContacts();
        this.context = context;
    }

    /**
     * Adds a new row to the table 'contacts'
     * @param first first name - String
     * @param last last name - String
     * @param title title - String
     * @param country country - String
     * @param gender gender - Gender
     * @return
     */
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

    /**
     * Executes query on the 'contacts' table
     * @return Cursor with rows based on query results
     */
    public Cursor selectContacts() {
        String[] cols = new String[] {_ID, COLUMN_FIRSTNAME, COLUMN_LASTNAME, COLUMN_TITLE, COLUMN_COUNTRY, COLUMN_GENDER, COLUMN_FAVORITE, COLUMN_DELETED};
        // TODO ADD FILTERS AND SORT
        Cursor mCursor = database.query(true, TABLE_NAME,cols,null
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; // iterate to get each value.
    }

    /**
     * Updates a row with new values
     * @param first first name - String
     * @param last last name - String
     * @param title title - String
     * @param country country - String
     * @param gender gender - Gender
     * @param fav favorite - boolean
     * @param del delete - boolean
     * @param id used to find the correct row
     */
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


    /**
     * Inverts the favorite value of a contact/row
     * @param id unique id to identify the row
     * @param fav current value, result will be !fav
     */
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

    /**
     * Removes a row from the database
     * @param id unique id of row that will be removed
     */
    public void deleteContact(int id) {
        int res = database.delete(TABLE_NAME, "_id = ?", new String[] {Integer.toString(id)});
        Log.i("deleted", Integer.toString(id));
        this.updateCursorAdapter();
    }

    /**
     * Returns a ContactCursorAdapter, populates menu_item
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
        if (searchAdapter != null) {
            searchAdapter.changeCursor(selectContacts());
        }
    }

    /**
     * Returns a SearchCursorAdapter, populates search_item
     * @return searchAdapter populating search_item
     */
    public SearchCursorAdapter getSearchAdapter() {
        if (searchAdapter == null) {
            searchAdapter = new SearchCursorAdapter(context, selectContacts());
            return searchAdapter;
        } else {
            searchAdapter.changeCursor(selectContacts());
            return searchAdapter;
        }
    }

    /**
     * Queries the table 'contacts' for the search String.
     * Returns a cursor that includes all matches with any column.
     * @param search search query - String
     * @return Cursor
     */
    public Cursor getSearchResults(String search) {
        Cursor cursor = null;
        // get individual search words
        String[] searchWords = search.split(" ");
        // add wildcards to match if substring
        for (int i = 0; i < searchWords.length; i++) {
            searchWords[i] = "%" + searchWords[i] + "%";
        }
        for (String s:searchWords) {
            Log.i("searchWords", s);

        }
        // create query
        // columns to add in cursor
        String[] cols = new String[] {_ID, COLUMN_FIRSTNAME, COLUMN_LASTNAME, COLUMN_TITLE, COLUMN_COUNTRY, COLUMN_GENDER, COLUMN_FAVORITE, COLUMN_DELETED};
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


        // attempting to manually construct, same result
        /*String query2 =
                "SELECT ";
        for(String s: cols) {
            query2 = query2 + s +", ";
        }
        query2 = query2.substring(0, query2.length()-2);

        query2 = query2 + " FROM " + TABLE_NAME + " WHERE (" +
                COLUMN_FIRSTNAME + " LIKE ?) OR (" +
                COLUMN_LASTNAME + " LIKE ?);";*/


        // query database
        cursor = database.query(TABLE_NAME, cols, query,
                searchWords, null, null, null, null);
        //Cursor cursor = database.rawQuery(query2, searchWords);
        Log.i("cursor count", Integer.toString(cursor.getCount()));

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

}
