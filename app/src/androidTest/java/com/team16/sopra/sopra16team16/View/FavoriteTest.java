package com.team16.sopra.sopra16team16.View;


import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;

import com.team16.sopra.sopra16team16.Controller.ContactCursorAdapter;
import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.Matchers;
import com.team16.sopra.sopra16team16.R;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;


/**
 * Favorite feature test class
 */
public class FavoriteTest {
    private ContactManager contactManager;
    private ContactCursorAdapter contactCursorAdapter;
    private ContactCursorAdapter favoriteCursorAdapter;

    @Rule
    public ActivityTestRule<HomeActivity> mRule = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void setup() {
        contactManager = ContactManager.getInstance(InstrumentationRegistry.getTargetContext());
        contactManager.wipe();


        for (int i = 0; i < 5; i++) {
            onView(withId(R.id.addNew)).perform(click());
            onView(withId(R.id.last_edit)).perform(typeText("hi"));
            Espresso.closeSoftKeyboard();

            onView(withId(R.id.record_button)).perform(click());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onView(withId(R.id.record_button)).perform(click());
            onView(withId(R.id.accept_dialog)).perform(click());

            onView(withId(R.id.confirm_button)).perform(click());

            Espresso.pressBack();
        }

    }


    @Test
    public void favoriteTest() {
        onView (withId (R.id.home_fragment)).check (ViewAssertions.matches (Matchers.withListSize(5)));

        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(0)
                .onChildView(withId(R.id.contact_fav))
                .perform(click());
        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(1)
                .onChildView(withId(R.id.contact_fav))
                .perform(click());
        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(2)
                .onChildView(withId(R.id.contact_fav))
                .perform(click());


        ViewInteraction appCompatImageButton = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")),
                        withParent(withId(R.id.my_toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        onData(anything()).inAdapterView(withId(R.id.left_drawer)).atPosition(0)
                .perform(click());
        onView (withId (R.id.home_fragment)).check (ViewAssertions.matches (Matchers.withListSize(3)));

        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(0)
                .onChildView(withId(R.id.contact_fav))
                .perform(click());

        onView (withId (R.id.home_fragment)).check (ViewAssertions.matches (Matchers.withListSize(2)));

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

        appCompatImageButton.perform(click());

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

        onView (withId (R.id.home_fragment)).check (ViewAssertions.matches (Matchers.withListSize(5)));
    }


}
