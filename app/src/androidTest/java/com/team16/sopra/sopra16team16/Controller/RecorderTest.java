package com.team16.sopra.sopra16team16.Controller;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.team16.sopra.sopra16team16.R;
import com.team16.sopra.sopra16team16.View.NewContactActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


import static org.junit.Assert.assertTrue;

/**
 * Created by Martin on 07.12.2016.
 */

/*

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecorderTest {

    private static Recorder recorder;
    private static Recorder recorder2;

    @Before
    public void createRecorder() {
        recorder = Recorder.getCurrentInstance(InstrumentationRegistry.getContext());
        assertTrue("Creating Recorder failed", recorder != null);

        recorder2 = Recorder.getCurrentInstance(InstrumentationRegistry.getContext());
        assertTrue("2 Instances of Recorder", recorder == recorder2);
    }

    @Test
    public void justATest() {
        recorder = Recorder.getCurrentInstance(InstrumentationRegistry.getContext());
        assertTrue("Context is null", recorder.context != null);
        assertTrue("Context is not null", recorder.context == null);

        //assertTrue("is not pressed", recorder.isPressed());
        //assertTrue("is pressed", recorder.isPressed() == false);
        //assertTrue("Path is null", recorder.path != null);
    }


}
*/

//@RunWith(AndroidJUnit4.class)
//@LargeTest
public class RecorderTest {

    @Rule
    public ActivityTestRule<NewContactActivity> mRule = new ActivityTestRule<>(NewContactActivity.class);

    @Before
    public void push_button() {
        onView(withId(R.id.record_button))
                .perform(click());
    }

    @Test
    public void nullCheck() {
        assertTrue("Recorder is null", Recorder.getCurrentInstance(InstrumentationRegistry.getContext()) != null);
    }

    @Test
    public void pathCheck() {
        assertTrue("Path is not null", Recorder.getCurrentInstance(InstrumentationRegistry.getContext()).path != null);
    }

    public void singletonCheck() {
        onView(withId(R.id.record_button))
                .perform(click());
        Recorder recorder = Recorder.getCurrentInstance(InstrumentationRegistry.getContext());
        Recorder recorder2 = Recorder.getCurrentInstance(InstrumentationRegistry.getContext());

        assertTrue("Not the same instance", recorder == recorder2);
        recorder.isPressed();
        assertTrue("Button ist gedrückt", recorder.isPressed() == false);
    }

}
