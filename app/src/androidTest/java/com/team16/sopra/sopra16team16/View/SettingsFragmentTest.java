package com.team16.sopra.sopra16team16.View;

import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.team16.sopra.sopra16team16.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by prime on 18.01.17.
 */

public class SettingsFragmentTest {

    Toolbar myToolbar;

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRules = new ActivityTestRule<HomeActivity>(HomeActivity.class);

    @Before
    public void setup() {
        //TODO open drawer and click settings
        // Initializes the toolbar

            // get the toolbar
            onView(withId(R.id.drawer_layout)).perform(click());

    }

//    @Test
//    public void testSpinner() {
////        onData(anything()).inAdapterView(withId(R.id.language_spinner)).atPosition(0)
////                .perform(click());
//        onView(withId(R.id.language_spinner)).perform(click());
////        onData(allOf(is(instanceOf(String.class)), is("Deutsch"))).perform(click());
////        onViekw(withId(R.id.language_spinner)).check(matches(withSpinnerText(containsString("Deutsch"))));
//
//        // TODO klicke auf alle Spinner Items nach einander. wie checke ich ob sprache geaender wurde?
//        // wozu sind die asserts?
//    }

    @Test
    public void testBackup() {
        //TODO klicke auf Backup
    }

    @Test
    public void testImport() {
        //TODO klicke auf Import
    }
}
