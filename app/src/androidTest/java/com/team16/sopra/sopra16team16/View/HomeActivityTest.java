package com.team16.sopra.sopra16team16.View;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoActivityResumedException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.widget.EditText;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
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
            mActivityTestRules.getActivity().runOnUiThread(new Runnable() {
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
        nextActivity.finish();
    }

    @Test
    public void drawerTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")),
                        withParent(withId(R.id.my_toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());
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
    public void searchTest() {
        onView(withId(R.id.action_search))
                .perform(click());

        ContactListFragment fragment = mActivityTestRules.getActivity().getFragment();
        assertTrue("searchAdapter wrong count", fragment.getListAdapter().getCount() == 3);

        onView(isAssignableFrom(EditText.class)).perform(clearText());
        onView(isAssignableFrom(EditText.class)).perform(typeText("test"),
                pressKey(KeyEvent.KEYCODE_ENTER));

        assertTrue("searchAdapter wrong count 'test'", fragment.getListAdapter().getCount() == 0);

        onView(isAssignableFrom(EditText.class)).perform(clearText());
        onView(isAssignableFrom(EditText.class)).perform(typeText("first"),
                pressKey(KeyEvent.KEYCODE_ENTER));

        assertTrue("searchAdapter wrong count 'first'", fragment.getListAdapter().getCount() == 1);

        onView(isAssignableFrom(EditText.class)).perform(clearText());
        onView(isAssignableFrom(EditText.class)).perform(typeText("FEMALE"),
                pressKey(KeyEvent.KEYCODE_ENTER));

        assertTrue("searchAdapter wrong count 'FEMALE'", fragment.getListAdapter().getCount() == 2);

        onView(isAssignableFrom(EditText.class)).perform(clearText());
        onView(isAssignableFrom(EditText.class)).perform(typeText("first last"),
                pressKey(KeyEvent.KEYCODE_ENTER));

        assertTrue("searchAdapter wrong count 'first last'", fragment.getListAdapter().getCount() == 1);

        onView(isAssignableFrom(EditText.class)).perform(clearText());
        onView(isAssignableFrom(EditText.class)).perform(typeText("first letzte"),
                pressKey(KeyEvent.KEYCODE_ENTER));

        assertTrue("searchAdapter wrong count 'first letzte'", fragment.getListAdapter().getCount() == 0);

        Espresso.pressBack();
        assertTrue("searchView didnt close", !mActivityTestRules.getActivity().searchVisible());
    }


    @Test
    public void searchVisibleTest() {
        onView(withId(R.id.action_search))
                .perform(click());
        assertTrue("search not visible", mActivityTestRules.getActivity().searchVisible());

        pressBack();
        pressBack();
        assertTrue("searchView didnt close", !mActivityTestRules.getActivity().searchVisible());
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
