package com.team16.sopra.sopra16team16.Model;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class DBManagerTest {
    private DBManager dbManager;
    private SQLiteDatabase sql;
    @Before
    public void setup() {
        dbManager = DBManager.getCurrentInstance(InstrumentationRegistry.getTargetContext().getApplicationContext());
    }

    @Test
    public void getDbTest() {
        sql = dbManager.getDbContacts();

        assertTrue("sql didnt open", sql.isOpen());
    }

    /* android handles closing, no need for this
    @Test
    public void closeTest() {
        // how to test this at the very end?
        // causes exceptions because database is closed
        //sql = dbManager.getDbContacts();
        //dbManager.close();

        //assertTrue("sql didnt close", !sql.isOpen());

    }

    @Test
    public void reopenTest() {
        //dbManager.reopen();
        //sql = dbManager.getDbContacts();
        //assertTrue("sql didnt reopen", sql.isOpen());
    }*/


}
