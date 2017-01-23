package com.team16.sopra.sopra16team16.Controller;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.ImageButton;

import com.team16.sopra.sopra16team16.R;
import com.team16.sopra.sopra16team16.View.HomeActivity;
import com.team16.sopra.sopra16team16.View.NewContactActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.v4.content.ContextCompat.getDrawable;
import static android.support.v4.content.ContextCompat.startActivity;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by Martin on 07.12.2016.
 */


public class RecorderPlayerTest {

    private Recorder recorder;
    private AudioManager manager;
    private Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(NewContactActivity.class.getName(), null, false);
    private Instrumentation.ActivityMonitor activityMonitor2 = getInstrumentation().addMonitor(NewContactActivity.class.getName(), null, false);
    private Instrumentation.ActivityMonitor activityMonitor3 = getInstrumentation().addMonitor(NewContactActivity.class.getName(), null, false);
    private Instrumentation.ActivityMonitor activityMonitor4 = getInstrumentation().addMonitor(NewContactActivity.class.getName(), null, false);
    @Rule
    public ActivityTestRule<HomeActivity> mRule = new ActivityTestRule<>(HomeActivity.class);


    @Before
    public void setup() {
        recorder = Recorder.getInstance();
        manager = (AudioManager) mRule.getActivity().getSystemService(Context.AUDIO_SERVICE);
        mRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).wipe();
            }
        });

    }

    // im so sorry oh god
    @Test
    public void recordingTest() {
        // TODO PROBLEM
        // NEED TO MANUALLY ALLOW PERMISSION BEFORE STARTING TESTS

        // HOMEACTIVITY
        onView(withId(R.id.addNew)).perform(click());

        NewContactActivity nextActivity = (NewContactActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // http://stackoverflow.com/questions/9405561/test-if-a-button-starts-a-new-activity-in-android-junit-pref-without-robotium
        // next activity is opened and captured.

        // NEWCONTACTACTIVITY
        assertNotNull(nextActivity);


        // TEST RECORDING
        onView(withId(R.id.record_button))
                .perform(click());

        assertTrue("recorder is not recording", recorder.isPressed());


        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.record_button))
                .perform(click());

        assertTrue("recorder didnt stop", !recorder.isPressed());


        // CHECK FOR TEMP FILES
        File temp = new File(FileUtils.PATH + (ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).getId() + 1) + "_temp.3gp");
        File perm = new File(FileUtils.PATH + (ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).getId() + 1) + ".3gp");
        assertTrue("temp file doesnt exist", temp.exists());

        // TEST TEMP ACCEPT DIALOG
        onView(withId(R.id.accept_dialog)).perform(click());

        // INPUT SOME TEXT
        onView(withId(R.id.first_edit)).perform(typeText("first"));
        onView(withId(R.id.last_edit)).perform(typeText("last"));
        onView(withId(R.id.country_edit)).perform(typeText("ger"));

        // TEST GENERIC RECORDING
        // FIRST

        Espresso.closeSoftKeyboard();
        // TEST CANCEL BUTTON
        onView(withId(R.id.cancel_button)).perform(click());

        // TEMP SHOULD ALL BE DELETED
        assertTrue("temp file still exists", !temp.exists());

        nextActivity.finish();


    }

    @Test
    public void creatingRecordingTest() {

        // possibly the biggest mess ever, im so sorry

        // IN HOMEACTIVITY
        onView(withId(R.id.addNew)).perform(click());


        NewContactActivity nextActivity = (NewContactActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // http://stackoverflow.com/questions/9405561/test-if-a-button-starts-a-new-activity-in-android-junit-pref-without-robotium
        // next activity is opened and captured.
        assertNotNull(nextActivity);

        // IN NEWCONTACTACTIVITY

        onView(withId(R.id.confirm_button)).perform(click());
        // TEST MINIMUM REQUIREMENTS FOR CREATION
        onView(withId(android.R.id.button1)).perform(click());

        // TEST RECORDER
        onView(withId(R.id.record_button))
                .perform(click());

        assertTrue("recorder is not recording", recorder.isPressed());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.record_button))
                .perform(click());

        File temp = new File(FileUtils.PATH + (ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).getId() + 1) + "_temp.3gp");
        File perm = new File(FileUtils.PATH + (ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).getId() + 1) + ".3gp");
        Log.d("recorderTemp", temp.toString());

        // TEST TEMP DIALOG
        onView(withId(R.id.play_dialog)).perform(click());
        assertTrue("player isnt playing", manager.isMusicActive());
        onView(withId(R.id.play_dialog)).perform(click());
        assertTrue("player didnt stop", !manager.isMusicActive());
        onView(withId(R.id.play_dialog)).perform(click());
        assertTrue("player isnt playing", manager.isMusicActive());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue("player didnt stop", !manager.isMusicActive());
        onView(withId(R.id.accept_dialog)).perform(click());

        // TEST SAVING
        onView(withId(R.id.confirm_button)).perform(click());
        // OH NO REQUIREMENTS NOT SATISFIED
        onView(withId(android.R.id.button1)).perform(click());
        // MEET REQUIREMENTS
        onView(withId(R.id.last_edit)).perform(typeText("test"));
        onView(withId(R.id.female_radioButton)).perform(click());

        Espresso.closeSoftKeyboard();

        // SAVE
        onView(withId(R.id.confirm_button)).perform(click());

        // IN CONTACTVIEWERACTIVITY
        // CHECK IF TEMP GET DELETED
        assertTrue("temp file still exists", !temp.exists());
        assertTrue("perm file doesnt exist", perm.exists());

        nextActivity.finish();


        onView(withId(R.id.edit_button)).perform(click());




        nextActivity = (NewContactActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor2, 5000);

        // IN NEWCONTACTACTIVITY - EDIT MODE
        // CHECK RADIO BUTTON STATES



        onView(withId(R.id.female_radioButton)).check(matches(isChecked()));
        onView(withId(R.id.male_radioButton)).check(matches(isNotChecked()));
        onView(withId(R.id.unkown_radioButton)).check(matches(isNotChecked()));

        onView(withId(R.id.male_radioButton)).perform(click());
        onView(withId(R.id.female_radioButton)).check(matches(isNotChecked()));
        onView(withId(R.id.male_radioButton)).check(matches(isChecked()));
        onView(withId(R.id.unkown_radioButton)).check(matches(isNotChecked()));

        FloatingActionButton fabRecord = (FloatingActionButton) nextActivity.findViewById(R.id.record_button);
        ColorStateList fabDefCol = fabRecord.getBackgroundTintList();
        Drawable defaultDraw = fabRecord.getDrawable();

        // TEST RECORDER - OVERWRITE
        onView(withId(R.id.record_button))
                .perform(click());

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue("recorder is not recording", recorder.isPressed());
        onView(withId(R.id.record_button))
                .perform(click());

        Log.d("recorderTemp", temp.toString());

        // TEST TEMP DIALOG
        onView(withId(R.id.accept_dialog)).perform(click());

        onView(withId(R.id.last_edit)).perform(typeText("test"));

        Espresso.closeSoftKeyboard();
        // TEST CANCEL WITH EXISTING TEMP FILE
        onView(withId(R.id.cancel_button)).perform(click());

        assertTrue("temp file still exists", !temp.exists());
        assertTrue("perm file doesnt exist", perm.exists());
        nextActivity.finish();


        // IN CONTACTVIEWERACTIVITY
        onView(withId(R.id.edit_button)).perform(click());

        nextActivity = (NewContactActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor3, 5000);
        // IN NEWCONTACTACTIVITY
        // CHECK RADIOBUTTONS
        onView(withId(R.id.female_radioButton)).check(matches(isChecked()));
        onView(withId(R.id.male_radioButton)).check(matches(isNotChecked()));
        onView(withId(R.id.unkown_radioButton)).check(matches(isNotChecked()));

        onView(withId(R.id.male_radioButton)).perform(click());
        onView(withId(R.id.female_radioButton)).check(matches(isNotChecked()));
        onView(withId(R.id.male_radioButton)).check(matches(isChecked()));
        onView(withId(R.id.unkown_radioButton)).check(matches(isNotChecked()));

        fabRecord = (FloatingActionButton) nextActivity.findViewById(R.id.record_button);

        defaultDraw = fabRecord.getDrawable();
        // TEST RECORDER
        onView(withId(R.id.record_button))
                .perform(click());

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue("recorder is not recording", recorder.isPressed());
        onView(withId(R.id.record_button))
                .perform(click());

        Log.d("recorderTemp", temp.toString());


        // TEST TEMP DIALOG
        onView(withId(R.id.accept_dialog)).perform(click());

        onView(withId(R.id.last_edit)).perform(typeText("test"));

        Espresso.closeSoftKeyboard();
        // TEST OVERWRITING
        onView(withId(R.id.confirm_button)).perform(click());


        // IN CONTACTVIEWERACTIVITY
        onView(withId(R.id.edit_button)).perform(click());


        nextActivity = (NewContactActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor4, 5000);

        // IN NEWCONTACTACTIVITY
        // CHECK RADIO BUTTONS AGAIN
        // THIS ENTIRE ITERATOION EXISTS TO GO THROUGH ALL CASES FOR GENDER
        onView(withId(R.id.female_radioButton)).check(matches(isNotChecked()));
        onView(withId(R.id.male_radioButton)).check(matches(isChecked()));
        onView(withId(R.id.unkown_radioButton)).check(matches(isNotChecked()));

        onView(withId(R.id.unkown_radioButton)).perform(click());
        onView(withId(R.id.female_radioButton)).check(matches(isNotChecked()));
        onView(withId(R.id.male_radioButton)).check(matches(isNotChecked()));
        onView(withId(R.id.unkown_radioButton)).check(matches(isChecked()));


        onView(withId(R.id.record_button))
                .perform(click());

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue("recorder is not recording", recorder.isPressed());
        onView(withId(R.id.record_button))
                .perform(click());

        Log.d("recorderTemp", temp.toString());

        onView(withId(R.id.accept_dialog)).perform(click());

        onView(withId(R.id.last_edit)).perform(typeText("test"));

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.confirm_button)).perform(click());


        // IN CONTACTVIEWERACTIVITY
        // TEST PLAYBUTTON
        onView(withId(R.id.play_button)).perform(click());

        assertTrue("player is not playing", manager.isMusicActive());
        onView(withId(R.id.play_button)).perform(click());

        assertTrue("player didnt stop playing", !manager.isMusicActive());

        onView(withId(R.id.play_button)).perform(click());

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue("player didnt stop playing", !manager.isMusicActive());


        Espresso.pressBack();

        // IN HOMEACTIVITY


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // TEST PLAYBUTTON
        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(0)
                .onChildView(withId(R.id.contact_play))
                .perform(click());

        assertTrue("player is not playing", manager.isMusicActive());
        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(0)
                .onChildView(withId(R.id.contact_play))
                .perform(click());
        assertTrue("player didnt stop playing", !manager.isMusicActive());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(0)
                .onChildView(withId(R.id.contact_play))
                .perform(click());
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue("player didnt stop playing", !manager.isMusicActive());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(0)
                .perform(click());

        // IN CONTACTVIEWERACTIVTY
        // TEST DELETE DIALOG
        onView(withId(R.id.delete_button)).perform(click());
        onView(withText("NO")).perform(click());
        onView(withId(R.id.delete_button)).perform(click());
        onView(withText("YES")).perform(click());

        // trigger undo
        onView(withId(R.id.addNew)).perform(click());
        assertTrue("perm file doesnt exist", !perm.exists());
        assertTrue("temp file doesnt exist", !temp.exists());

    }


}
