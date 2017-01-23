package com.team16.sopra.sopra16team16.Controller;

import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.team16.sopra.sopra16team16.Controller.ContactCursorAdapter;
import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.View.HomeActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by moo on 11/29/2016.
 */

public class ContactManagerTest {

    private static ContactManager contactManager;
    private ContactManager contactManager2;


    @Before
    public void setup() {
                contactManager = ContactManager.getInstance(InstrumentationRegistry.getTargetContext());
                contactManager.wipe();
                singletonTest();
    }


    public void singletonTest() {
                // initial setup test
                contactManager = ContactManager.getInstance(InstrumentationRegistry.getTargetContext());
                contactManager.open();
                assertTrue("initialization of contactManager failed", contactManager != null);
                contactManager2 = ContactManager.getInstance(InstrumentationRegistry.getTargetContext());
                assertTrue("singleton failed", contactManager == contactManager2);

                contactManager.createContact(
                        "first",
                        "last",
                        "dr",
                        "germany",
                        "MALE");
                contactManager.createContact(
                        "first",
                        "last",
                        "dr",
                        "germany",
                        "MALE");
                contactManager.createContact(
                        "first",
                        "last",
                        "dr",
                        "germany",
                        "MALE");
                contactManager.createContact(
                        "first",
                        "last",
                        "dr",
                        "germany",
                        "MALE");
                contactManager.createContact(
                        "first",
                        "last",
                        "dr",
                        "germany",
                        "MALE");
    }


    @Test
    public void getIdTest() {
                int id = contactManager.getId();

                assertTrue("getId failed", id >= 0);
    }


    @Test
    public void updateCursorAdapterTest() {

                ContactCursorAdapter testCursor = contactManager.getCursorAdapterDefault();

                assertNotNull(testCursor);
    }

    @Test
    public void createContactTest() {

                Long res = contactManager.createContact(
                        "first",
                        "last",
                        "dr",
                        "germany",
                        "MALE");

                int resInt = res.intValue();
                Log.d("addedRes", Integer.toString(resInt));
                Log.d("addedResLong", Long.toString(res));

                assertTrue("didnt add one contact", resInt != -1);

                contactManager.createContact(
                        "first",
                        "last",
                        "dr",
                        "germany",
                        "MALE");
                contactManager.createContact(
                        "first",
                        "last",
                        "dr",
                        "germany",
                        "MALE");
                contactManager.createContact(
                        "first",
                        "last",
                        "dr",
                        "germany",
                        "MALE");
                contactManager.createContact(
                        "first",
                        "last",
                        "dr",
                        "germany",
                        "MALE");

    }

    @Test
    public void updateContactTest() {


                Cursor cursor = contactManager.selectContacts();
                cursor.moveToFirst();
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

                Long res = contactManager.updateContact(id, "last", "first", "dr", "germany", "FEMALE");

                assertTrue("didnt update contact", res == 1);

    }

    @Test
    public void selectContactsTest() {

                Cursor cursor = contactManager.selectContacts();
                Log.d("selectCursorSize", Integer.toString(cursor.getCount()));

                assertTrue("selectContacts failed", cursor.getCount()

                        >= 1);

    }

    @Test
    public void favoriteToggle() {


                Cursor cursor = contactManager.selectContacts();
                Log.d("favoriteCursorSize", Integer.toString(cursor.getCount()));


                cursor.moveToFirst();
                int favBefore = cursor.getInt(cursor.getColumnIndexOrThrow("favorite"));
                int res = contactManager.toggleFavorite(cursor.getInt(cursor.getColumnIndexOrThrow("_id")), favBefore);
                cursor = contactManager.selectContacts();
                cursor.moveToFirst();
                int favAfter = cursor.getInt(cursor.getColumnIndexOrThrow("favorite"));

                Log.d("favBefore", Integer.toString(favBefore));
                Log.d("favAfter", Integer.toString(favAfter));

                Log.d("resToggle", Integer.toString(res));

                assertTrue("didnt toggle favorite", favAfter == (favBefore + 1)

                        % 2);

    }

    @Test
    public void deleteContactToggleTest() {


                Cursor cursor = contactManager.selectContacts();
                Log.d("deleteCursorSize", Integer.toString(cursor.getCount()));

                cursor.moveToFirst();

                Log.d("deleteCursorId", Integer.toString(cursor.getInt(cursor.getColumnIndexOrThrow("_id"))));

                assertTrue("didnt delete one contact", 1 ==
                        contactManager.toggleDeleted(cursor.getInt(cursor.getColumnIndexOrThrow("_id")
                        ), 1));

        assertTrue("didnt delete one contact", 1 ==
                contactManager.toggleDeleted(cursor.getInt(cursor.getColumnIndexOrThrow("_id")
                ), 0));


    }


    @Test
    public void contactCursorSingletonTest() {


                ContactCursorAdapter cursor1 = contactManager.getCursorAdapterDefault();

                assertTrue("cursor1 not initialized", cursor1 != null);

                ContactCursorAdapter cursor2 = contactManager.getCursorAdapterDefault();

                assertTrue("singleton failed?", cursor1 == cursor2);

    }

    @Test
    public void searchResultsTest() {


                Cursor cursorNo = contactManager.getSearchResults("");
                Cursor cursorOne = contactManager.getSearchResults("test");
                Cursor cursorMultiple = contactManager.getSearchResults("first last");

                cursorNo.moveToFirst();
                cursorOne.moveToFirst();
                cursorMultiple.moveToFirst();
                boolean resNo = true;
                boolean resOne = true;
                boolean resMultiple = true;

                while (cursorNo.moveToNext())

                {
                    if (!cursorNo.getString(cursorNo.getColumnIndexOrThrow("first")).contains("") &&
                            !cursorNo.getString(cursorNo.getColumnIndexOrThrow("last")).contains("") &&
                            !cursorNo.getString(cursorNo.getColumnIndexOrThrow("country")).contains("") &&
                            !cursorNo.getString(cursorNo.getColumnIndexOrThrow("title")).contains("") &&
                            !cursorNo.getString(cursorNo.getColumnIndexOrThrow("gender")).contains("")) {
                        resNo = false;
                    }
                }


                while (cursorOne.moveToNext())

                {
                    Log.d("cursorOneFirst", cursorOne.getString(cursorOne.getColumnIndexOrThrow("first")));
                    Log.d("cursorOneLast", cursorOne.getString(cursorOne.getColumnIndexOrThrow("last")));
                    Log.d("cursorOneCountry", cursorOne.getString(cursorOne.getColumnIndexOrThrow("country")));
                    Log.d("cursorOneTitle", cursorOne.getString(cursorOne.getColumnIndexOrThrow("title")));
                    Log.d("cursorOneGender", cursorOne.getString(cursorOne.getColumnIndexOrThrow("gender")));
                    if (!cursorOne.getString(cursorOne.getColumnIndexOrThrow("first")).contains("test") &&
                            !cursorOne.getString(cursorOne.getColumnIndexOrThrow("last")).contains("test") &&
                            !cursorOne.getString(cursorOne.getColumnIndexOrThrow("country")).contains("test") &&
                            !cursorOne.getString(cursorOne.getColumnIndexOrThrow("title")).contains("test") &&
                            !cursorOne.getString(cursorOne.getColumnIndexOrThrow("gender")).contains("test")) {
                        resOne = false;
                    }
                }

                while (cursorMultiple.moveToNext())

                {
                    Log.d("cursorMultipleFirst", cursorMultiple.getString(cursorMultiple.getColumnIndexOrThrow("first")));
                    Log.d("cursorMultipleLast", cursorMultiple.getString(cursorMultiple.getColumnIndexOrThrow("last")));
                    Log.d("cursorMultipleCountry", cursorMultiple.getString(cursorMultiple.getColumnIndexOrThrow("country")));
                    Log.d("cursorMultipleTitle", cursorMultiple.getString(cursorMultiple.getColumnIndexOrThrow("title")));
                    Log.d("cursorMultipleGender", cursorMultiple.getString(cursorMultiple.getColumnIndexOrThrow("gender")));
                    if (!cursorMultiple.getString(cursorMultiple.getColumnIndexOrThrow("first")).contains("first") &&
                            !cursorMultiple.getString(cursorMultiple.getColumnIndexOrThrow("first")).contains("last") &&
                            !cursorMultiple.getString(cursorMultiple.getColumnIndexOrThrow("last")).contains("first") &&
                            !cursorMultiple.getString(cursorMultiple.getColumnIndexOrThrow("last")).contains("last") &&
                            !cursorMultiple.getString(cursorMultiple.getColumnIndexOrThrow("country")).contains("first") &&
                            !cursorMultiple.getString(cursorMultiple.getColumnIndexOrThrow("country")).contains("last") &&
                            !cursorMultiple.getString(cursorMultiple.getColumnIndexOrThrow("title")).contains("first") &&
                            !cursorMultiple.getString(cursorMultiple.getColumnIndexOrThrow("title")).contains("last") &&
                            !cursorMultiple.getString(cursorMultiple.getColumnIndexOrThrow("gender")).contains("first") &&
                            !cursorMultiple.getString(cursorMultiple.getColumnIndexOrThrow("gender")).contains("last")) {
                        resMultiple = false;
                    }
                }


                assertTrue("wrong search result with n oword", resNo);

                assertTrue("wrong search result with a single word", resOne);

                assertTrue("wrong search result with multiple words", resMultiple);


    }


    @Test
    public void wipeTest() {

                contactManager.wipe();

                assertTrue("didnt wipe", contactManager.selectContacts().getCount() == 0);

    }

}
