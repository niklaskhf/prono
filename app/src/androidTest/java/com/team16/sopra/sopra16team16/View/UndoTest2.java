package com.team16.sopra.sopra16team16.View;


import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertTrue;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UndoTest2 {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void undoTest2() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.addNew),
                        withParent(withId(R.id.content_frame)),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.first_edit), isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.first_edit), isDisplayed()));
        appCompatEditText2.perform(replaceText("i"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.last_edit), isDisplayed()));
        appCompatEditText3.perform(replaceText("it"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.country_edit), isDisplayed()));
        appCompatEditText4.perform(replaceText("me"), closeSoftKeyboard());


        Espresso.closeSoftKeyboard();

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.record_button),
                        withParent(allOf(withId(R.id.contact_editor_coord),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.record_button),
                        withParent(allOf(withId(R.id.contact_editor_coord),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton3.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.accept_dialog), isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction floatingActionButton4 = onView(
                allOf(withId(R.id.confirm_button),
                        withParent(allOf(withId(R.id.contact_editor_coord),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton4.perform(click());

        pressBack();

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.home_fragment),
                                withParent(withId(R.id.content_frame))),
                        0),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction floatingActionButton5 = onView(
                allOf(withId(R.id.delete_button),
                        withParent(allOf(withId(R.id.contact_viewer_coord),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton5.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("YES")));
        appCompatButton.perform(scrollTo(), click());

        assertTrue(ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).selectContacts().getCount() == 0);
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.snackbar_action), withText("UNDO"), isDisplayed()));
        appCompatButton2.perform(click());

        assertTrue(ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).selectContacts().getCount() == 1);
        ViewInteraction linearLayout2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.home_fragment),
                                withParent(withId(R.id.content_frame))),
                        0),
                        isDisplayed()));
        linearLayout2.perform(click());

        ViewInteraction floatingActionButton6 = onView(
                allOf(withId(R.id.delete_button),
                        withParent(allOf(withId(R.id.contact_viewer_coord),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton6.perform(click());

        ViewInteraction floatingActionButton7 = onView(
                allOf(withId(R.id.delete_button),
                        withParent(allOf(withId(R.id.contact_viewer_coord),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        floatingActionButton7.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button1), withText("YES")));
        appCompatButton3.perform(scrollTo(), click());

        assertTrue(ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).selectContacts().getCount() == 0);

        ViewInteraction floatingActionButton8 = onView(
                allOf(withId(R.id.addNew),
                        withParent(withId(R.id.content_frame)),
                        isDisplayed()));
        floatingActionButton8.perform(click());

        pressBack();
        assertTrue(ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).selectContacts().getCount() == 0);

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
