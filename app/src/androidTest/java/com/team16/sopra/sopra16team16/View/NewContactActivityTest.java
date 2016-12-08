package com.team16.sopra.sopra16team16.View;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

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
import static org.junit.Assert.assertNotNull;

/**
 * Created by prime on 08.12.16.
 */
public class NewContactActivityTest {

//    private String firstName = "Max";
//    private String lastName = "Mustermann";
//    private String title = "Herr";
//    private String country = "Deutschland";
//    private String gender = "MALE";
//    private String id = "1";
//    private String cause = "EDIT";
//
//
//    Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ContactViewerActivity.class.getName(), null, false);
//
//    @Rule
//    public ActivityTestRule<ContactViewerActivity> mActivityRule =
//            new ActivityTestRule<>(ContactViewerActivity.class);
//
//    @Test
//    public void demonstrateIntentPrep() {
//        Intent intent = new Intent();
//        intent.putExtra("id", id);
//        intent.putExtra("first", firstName);
//        intent.putExtra("last", lastName);
//        intent.putExtra("title", title);
//        intent.putExtra("country", country);
//        intent.putExtra("gender", gender);
//        intent.putExtra("cause", cause);
//
//        mActivityRule.launchActivity(intent);
//
//
//        onView(withId(R.id.first_edit)).check(matches(withText(firstName)));
//        onView(withId(R.id.last_edit)).check(matches(withText(lastName)));
//        onView(withId(R.id.title_edit)).check(matches(withText(title)));
//        onView(withId(R.id.country_edit)).check(matches(withText(country)));
//    }
//
//    @Test
//    public void editContactTest() {
//        onView(withId(R.id.confirm_button)).perform(click());
//
//        ContactViewerActivity nextActivity = (ContactViewerActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
//        // http://stackoverflow.com/questions/9405561/test-if-a-button-starts-a-new-activity-in-android-junit-pref-without-robotium
//        // next activity is opened and captured.
//        assertNotNull(nextActivity);
//        nextActivity.finish();
//    }
//
//    @Test
//    public void cancelEditTest() {
//        onView(withId(R.id.cancel_button)).perform(click());
//
//        ContactViewerActivity nextActivity = (ContactViewerActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
//        // http://stackoverflow.com/questions/9405561/test-if-a-button-starts-a-new-activity-in-android-junit-pref-without-robotium
//        // next activity is opened and captured.
//        assertNotNull(nextActivity);
//        nextActivity.finish();
//    }
}
