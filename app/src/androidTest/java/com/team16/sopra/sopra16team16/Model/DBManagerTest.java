package com.team16.sopra.sopra16team16.Model;

import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by moo on 12/5/16.
 */

public class DBManagerTest {
    private DBManager dbManager;
    @Before
    public void setup() {
        dbManager = new DBManager(InstrumentationRegistry.getTargetContext().getApplicationContext());
    }

    @Test
    public void closeTest() {
        // how do you even test a closed conncetion?
        dbManager.getDbContacts();
        dbManager.close();
    }
}
