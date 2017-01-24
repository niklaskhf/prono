package com.team16.sopra.sopra16team16.View;

import android.support.test.InstrumentationRegistry;

import com.team16.sopra.sopra16team16.Controller.ContactManager;

import org.junit.Before;
import org.junit.Rule;

public class StressTest {

    private ContactManager contactManager;

    @Rule

    @Before
    public void setup() {
        contactManager = ContactManager.getInstance(InstrumentationRegistry.getTargetContext());
        contactManager.wipe();
    }
}
