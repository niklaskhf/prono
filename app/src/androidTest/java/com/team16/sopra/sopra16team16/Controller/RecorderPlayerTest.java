package com.team16.sopra.sopra16team16.Controller;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.team16.sopra.sopra16team16.R;
import com.team16.sopra.sopra16team16.View.HomeActivity;
import com.team16.sopra.sopra16team16.View.NewContactActivity;

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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Martin on 07.12.2016.
 */



//@RunWith(AndroidJUnit4.class)
//@LargeTest
public class RecorderPlayerTest {

    private Recorder recorder;
    private Player player;
    private Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(NewContactActivity.class.getName(), null, false);
    @Rule
    public ActivityTestRule<HomeActivity> mRule = new ActivityTestRule<>(HomeActivity.class);


    @Before
    public void setup() {
        recorder = Recorder.getCurrentInstance(InstrumentationRegistry.getTargetContext());
        ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).wipe();
        player = Player.getCurrentInstance(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void recordingTest() {
        // TODO PROBLEM
        // NEED TO MANUALLY ALLOW PERMISSION BEFORE STARTING TESTS
        onView(withId(R.id.addNew)).perform(click());

        NewContactActivity nextActivity = (NewContactActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // http://stackoverflow.com/questions/9405561/test-if-a-button-starts-a-new-activity-in-android-junit-pref-without-robotium
        // next activity is opened and captured.

        assertNotNull(nextActivity);
        onView(withId(R.id.record_button))
                .perform(click());

        assertTrue("recorder is not recording", recorder.isPressed());
        onView(withId(R.id.record_button))
                .perform(click());

        assertTrue("recorder didnt stop", !recorder.isPressed());
        File temp = new File(recorder.path + (ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).getId()+1) + "temp.3gp");
        File perm = new File(recorder.path + (ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).getId()+1) + ".3gp");
        Log.d("recorderTemp", temp.toString());
        assertTrue("temp file doesnt exist", temp.exists());

        onView(withId(R.id.accept_dialog)).perform(click());

        onView(withId(R.id.cancel_button)).perform(click());

        assertTrue("temp file still exists", !temp.exists());

        nextActivity.finish();

    }

    @Test
    public void creatingRecordingTest() {
        onView(withId(R.id.addNew)).perform(click());

        NewContactActivity nextActivity = (NewContactActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // http://stackoverflow.com/questions/9405561/test-if-a-button-starts-a-new-activity-in-android-junit-pref-without-robotium
        // next activity is opened and captured.
        assertNotNull(nextActivity);

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

        File temp = new File(recorder.path + (ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).getId()+1) + "temp.3gp");
        File perm = new File(recorder.path + (ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).getId()+1) + ".3gp");
        Log.d("recorderTemp", temp.toString());

        onView(withId(R.id.play_dialog)).perform(click());
        assertTrue("player isnt playing", player.isPlaying());
        onView(withId(R.id.play_dialog)).perform(click());
        assertTrue("player didnt stop", !player.isPlaying());
        onView(withId(R.id.play_dialog)).perform(click());
        assertTrue("player isnt playing", player.isPlaying());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue("player didnt stop", !player.isPlaying());
        onView(withId(R.id.accept_dialog)).perform(click());

        onView(withId(R.id.last_edit)).perform(typeText("test"));

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.confirm_button)).perform(click());

        assertTrue("temp file still exists", !temp.exists());
        assertTrue("perm file doesnt exist", perm.exists());

        nextActivity.finish();

        onView(withId(R.id.edit_button)).perform(click());

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

        assertTrue("temp file still exists", !temp.exists());
        assertTrue("perm file doesnt exist", perm.exists());
        nextActivity.finish();
        Espresso.pressBack();
        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(0)
                .onChildView(withId(R.id.contact_play))
                .perform(click());

        assertTrue("player is not playing", player.isPlaying());
        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(0)
                .onChildView(withId(R.id.contact_play))
                .perform(click());
        assertTrue("player didnt stop playing", !player.isPlaying());

        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(0)
                .onChildView(withId(R.id.contact_play))
                .perform(click());
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue("player didnt stop playing", !player.isPlaying());

        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(0)
                .perform(click());
        onView(withId(R.id.delete_button)).perform(click());
        onView(withText("YES")).perform(click());

        assertTrue("perm file doesnt exist", !perm.exists());
        assertTrue("temp file doesnt exist", !temp.exists());



    }
    @Test
    public void nullCheck() {
        assertTrue("Recorder is null", recorder != null);
    }

    @Test
    public void pathCheck() {
        assertTrue("Path is not null", recorder.path != null);
    }

    public void singletonCheck() {
        Recorder recorder2 = Recorder.getCurrentInstance(InstrumentationRegistry.getTargetContext());

        assertTrue("Not the same instance", recorder == recorder2);
    }

}
