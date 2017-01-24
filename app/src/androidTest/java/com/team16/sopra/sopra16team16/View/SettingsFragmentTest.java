package com.team16.sopra.sopra16team16.View;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.core.deps.guava.util.concurrent.ExecutionError;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.team16.sopra.sopra16team16.R;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
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


public class SettingsFragmentTest {
    Toolbar myToolbar;
    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRules = new ActivityTestRule<HomeActivity>(HomeActivity.class);
    @Test
    public void testSpinner() throws Exception {
        // /check for English
        onView(withId(R.id.drawer_layout)).perform(swipeRight());
        onData(anything()).inAdapterView(withId(R.id.left_drawer)).atPosition(1).perform(click());
        onView(withId(R.id.language_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("English"))).perform(click());
        onView(withId(R.id.drawer_layout)).perform(swipeRight());
        onData(anything()).inAdapterView(withId(R.id.left_drawer)).atPosition(1).check(matches(withText("Settings")));
        //check for German
        onView(withId(R.id.drawer_layout)).perform(swipeRight());
        onData(anything()).inAdapterView(withId(R.id.left_drawer)).atPosition(1).perform(click());
        onView(withId(R.id.language_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Deutsch"))).perform(click());
        onView(withId(R.id.drawer_layout)).perform(swipeRight());
        onData(anything()).inAdapterView(withId(R.id.left_drawer)).atPosition(1).check(matches(withText("Einstellungen")));
        //check for Russian
        onView(withId(R.id.drawer_layout)).perform(swipeRight());
        onData(anything()).inAdapterView(withId(R.id.left_drawer)).atPosition(1).perform(click());
        onView(withId(R.id.language_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("русский"))).perform(click());
        onView(withId(R.id.drawer_layout)).perform(swipeRight());
        onData(anything()).inAdapterView(withId(R.id.left_drawer)).atPosition(1).check(matches(withText("Настройки")));
        //check for Tuerk
        onView(withId(R.id.drawer_layout)).perform(swipeRight());
        onData(anything()).inAdapterView(withId(R.id.left_drawer)).atPosition(1).perform(click());
        onView(withId(R.id.language_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Türk"))).perform(click());
        onView(withId(R.id.drawer_layout)).perform(swipeRight());
        onData(anything()).inAdapterView(withId(R.id.left_drawer)).atPosition(1).check(matches(withText("Ayarlar")));
    }

    public static ViewAction swipeRight() {
        return new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT,
                GeneralLocation.CENTER_RIGHT, Press.THUMB);
    }
}
