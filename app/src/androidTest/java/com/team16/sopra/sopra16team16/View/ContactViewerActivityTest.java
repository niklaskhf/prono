package com.team16.sopra.sopra16team16.View;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.team16.sopra.sopra16team16.Controller.Player;
import com.team16.sopra.sopra16team16.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
/**
 * Created by prime on 08.12.16.
 */

//@RunWith(AndroidJUnit4.class)
public class ContactViewerActivityTest {

    private String firstName = "Max";
    private String lastName = "Mustermann";
    private String title = "Herr";
    private String country = "Deutschland";
    private String gender = "MALE";
    private String id = "1";

    Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(NewContactActivity.class.getName(), null, false);

    @Rule
    public ActivityTestRule<ContactViewerActivity> mActivityRule =
               new ActivityTestRule<>(ContactViewerActivity.class);

    @Test
    public void demonstrateIntentPrep() {
        Intent intent = new Intent();
        intent.putExtra("id", id);
        intent.putExtra("first", firstName);
        intent.putExtra("last", lastName);
        intent.putExtra("title", title);
        intent.putExtra("country", country);
        intent.putExtra("gender", gender);

        mActivityRule.launchActivity(intent);

        onView(withId(R.id.real_first_name)).check(matches(withText(firstName)));
        onView(withId(R.id.real_last_name)).check(matches(withText(lastName)));
        onView(withId(R.id.real_title)).check(matches(withText(title)));
        onView(withId(R.id.real_country)).check(matches(withText(country)));
    }

    @Test
    public void editContactTest() {
        onView(withId(R.id.edit_button)).perform(click());

        NewContactActivity nextActivity = (NewContactActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // http://stackoverflow.com/questions/9405561/test-if-a-button-starts-a-new-activity-in-android-junit-pref-without-robotium
        // next activity is opened and captured.
        assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void playButtonTest() {
        onView(withId(R.id.play_button)).perform(click());
        assertTrue("Player doesn't play", Player.getCurrentInstance(InstrumentationRegistry.getContext()).isPlaying() == true);
        onView(withId(R.id.play_button)).perform(click());
        assertTrue("Player still plays", Player.getCurrentInstance(InstrumentationRegistry.getContext()).isPlaying() == false);
    }
    

}
