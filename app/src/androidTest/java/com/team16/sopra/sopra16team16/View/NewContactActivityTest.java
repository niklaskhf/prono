package com.team16.sopra.sopra16team16.View;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;


public class NewContactActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRules = new ActivityTestRule<HomeActivity>(HomeActivity.class);

    @Before
    public void setup() {
        ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).wipe();
    }
    @Test
    public void anotherOne() {
        // TODO add assertions
        onView(withId(R.id.addNew)).perform(click());



        onView(withId(R.id.first_edit)).perform(typeText("hello"));
        onView(withId(R.id.last_edit)).perform(typeText("you"));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.confirm_button)).perform(click());
        onView(withText("OK")).perform(click());



        onView(withId(R.id.last_edit)).perform(clearText());
        onView(withId(R.id.last_edit)).perform(typeText("hello"));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.record_button)).perform(click());

        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        onView(withId(R.id.record_button)).perform(click());
        onView(withId(R.id.accept_dialog)).perform(click());

        onView(withId(R.id.country_edit)).perform(typeText("ger"));

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.confirm_button)).perform(click());

    }
}
