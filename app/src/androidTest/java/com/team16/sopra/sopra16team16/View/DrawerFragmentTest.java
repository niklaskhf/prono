package com.team16.sopra.sopra16team16.View;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;

import com.team16.sopra.sopra16team16.R;

import junit.framework.AssertionFailedError;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

/**
 * Created by moo on 1/18/2017.
 */

public class DrawerFragmentTest {
    // test if all the fragments open as supposed
    // test if navigation works as intended

    @Rule
    public ActivityTestRule<HomeActivity> mRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void testFavorites() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")),
                        withParent(withId(R.id.my_toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        onData(anything()).inAdapterView(withId(R.id.left_drawer)).atPosition(0)
                .perform(click());

        testStateSub();
        appCompatImageButton.perform(click());
        testStateMain();
    }

    @Test
    public void testSettings() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")),
                        withParent(withId(R.id.my_toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        onData(anything()).inAdapterView(withId(R.id.left_drawer)).atPosition(1)
                .perform(click());

        testStateSub();
        Espresso.pressBack();
        testStateMain();

    }

    @Test
    public void testAbout() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")),
                        withParent(withId(R.id.my_toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        onData(anything()).inAdapterView(withId(R.id.left_drawer)).atPosition(2)
                .perform(click());

        testStateSub();
        appCompatImageButton.perform(click());
        testStateMain();
    }


    private void testStateSub() {
        try {
            onView(withId(R.id.addNew)).check(matches(isDisplayed()));
            assertTrue(false);
        } catch (AssertionFailedError e) {
            assertTrue(true);
        }

        try {
            onView(withId(R.id.action_search)).check(matches(isDisplayed()));
            assertTrue(false);
        } catch (NoMatchingViewException e) {
            assertTrue(true);
        }
    }

    private void testStateMain() {
        try {
            onView(withId(R.id.addNew)).check(matches(isDisplayed()));
            assertTrue(true);
        } catch (AssertionFailedError e) {
            assertTrue(false);
        }

        try {
            onView(withId(R.id.action_search)).check(matches(isDisplayed()));
            assertTrue(true);
        } catch (AssertionFailedError e) {
            assertTrue(false);
        }
    }
}
