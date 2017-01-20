package com.team16.sopra.sopra16team16.View;

import android.app.Instrumentation;
import android.database.Cursor;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.Controller.Player;
import com.team16.sopra.sopra16team16.R;

import org.junit.Test;
import org.junit.Rule;
import org.junit.Before;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;



public class ContactListTest {

    private ContactManager contactManager;
    Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ContactViewerActivity.class.getName(), null, false);
    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRules = new ActivityTestRule<HomeActivity>(HomeActivity.class);

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    @UiThreadTest
    public void setup() {
        contactManager = ContactManager.getInstance(mActivityTestRules.getActivity());
        try {
            mActivityTestRules.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    contactManager.wipe();
                    contactManager.createContact("first", "last", "title", "country", "FEMALE");
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    @Test
    public void favoriteClickTest() {
        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(0)
                .onChildView(withId(R.id.contact_fav))
                .perform(click());
        try {
            mActivityTestRules.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Cursor cursor;
                    cursor = contactManager.selectContacts();

                    assertTrue("favorite didnt toggle to true",
                            cursor.getInt(cursor.getColumnIndexOrThrow("favorite")) == 1);

                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(0)
                .onChildView(withId(R.id.contact_fav))
                .perform(click());

        try {
            mActivityTestRules.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Cursor cursor;
                    cursor = contactManager.selectContacts();

                    assertTrue("favorite didnt toggle to false",
                            cursor.getInt(cursor.getColumnIndexOrThrow("favorite")) == 0);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

//    @Test
//    public void playButtonTest() {
//        Player player = Player.getCurrentInstance(mActivityTestRules.getActivity());
//
//        //exception.expect(IOException.class);
//        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(0)
//                .onChildView(withId(R.id.contact_play))
//                .perform(click());
//        // how do you test this without an audio file to play HMM
//    }

    @Test
    public void startViewerTest() {
        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(0).perform(click());
        ContactViewerActivity nextActivity = (ContactViewerActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // http://stackoverflow.com/questions/9405561/test-if-a-button-starts-a-new-activity-in-android-junit-pref-without-robotium
        // next activity is opened and captured.
        assertNotNull(nextActivity);
        nextActivity .finish();


        // throws NullPointerException, not sure why?
        //intended(hasComponent(ContactViewerActivity.class.getName()));
    }
}
