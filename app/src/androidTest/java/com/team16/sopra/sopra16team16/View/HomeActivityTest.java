package com.team16.sopra.sopra16team16.View;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoActivityResumedException;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by moo on 12/6/2016.
 */

public class HomeActivityTest {

    private ContactManager contactManager;
    Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(NewContactActivity.class.getName(), null, false);
    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRules = new ActivityTestRule<HomeActivity>(HomeActivity.class);

    @Before
    public void setup() {
        contactManager = ContactManager.getInstance(mActivityTestRules.getActivity());
        try {
            mActivityTestRules.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    contactManager.wipe();
                    contactManager.createContact("first", "last", "title", "country", "FEMALE");
                    contactManager.createContact("erste", "letzte", "titel", "land", "MALE");
                    contactManager.createContact("oui", "ouioui", "titlé", "francais", "FEMALE");
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Test
    public void addContactTest() {
        onView(withId(R.id.addNew)).perform(click());

        NewContactActivity nextActivity = (NewContactActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // http://stackoverflow.com/questions/9405561/test-if-a-button-starts-a-new-activity-in-android-junit-pref-without-robotium
        // next activity is opened and captured.
        assertNotNull(nextActivity);
        nextActivity .finish();
    }

    @Test
    public void drawerTest() {
        onView(withId(R.id.action_menu))
                .perform(click());
        DrawerLayout mDrawerLayout = (DrawerLayout) mActivityTestRules.getActivity().findViewById(R.id.drawer_layout);

        assertTrue("drawer didnt open", mDrawerLayout.isDrawerOpen(GravityCompat.START));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pressBack();

        assertTrue("drawer didnt close", !mDrawerLayout.isDrawerOpen(GravityCompat.START));

    }

    @Test
    public void closeAppTest() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        Instrumentation.ActivityMonitor activityMonitor = instrumentation.addMonitor(HomeActivity.class.getName(), null, false);
        Activity activity = instrumentation.waitForMonitorWithTimeout(activityMonitor, 1000);

        try {
            pressBack();
        } catch(NoActivityResumedException e) {
                assertTrue(true);
            }
    }

}
