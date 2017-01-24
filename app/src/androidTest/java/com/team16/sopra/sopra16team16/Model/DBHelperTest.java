package com.team16.sopra.sopra16team16.Model;

import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * DBHelper test class
 * */

public class DBHelperTest {

    private DBHelper dbHelper;

    @Before
    public void setup() {
        dbHelper = DBHelper.getCurrentInstance(InstrumentationRegistry.getTargetContext().getApplicationContext());
        assertNotNull(dbHelper);
    }

    @Test
    public void testSingleton() {
        dbHelper = DBHelper.getCurrentInstance(InstrumentationRegistry.getTargetContext().getApplicationContext());
        assertNotNull(dbHelper);
    }

    @Test
    public void onUpgradeTest() {
        dbHelper.onUpgrade(dbHelper.getWritableDatabase(), 1,2);
    }

}
