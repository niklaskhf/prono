package com.team16.sopra.sopra16team16.View;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoActivityResumedException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.widget.EditText;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.Controller.Filter;
import com.team16.sopra.sopra16team16.Controller.Sorter;
import com.team16.sopra.sopra16team16.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.R.attr.fragment;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * FilterActivity test class
 */
public class FilterActivityTest {

    private ContactManager contactManager;
    private Filter filter;
    private Sorter sorter;

    //Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(NewContactActivity.class.getName(), null, false);
    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRules = new ActivityTestRule<HomeActivity>(HomeActivity.class);

    @Before
    public void setup() {
        contactManager = ContactManager.getInstance(mActivityTestRules.getActivity());
        filter = Filter.getCurrentInstance();
        sorter = Sorter.getCurrentInstance();
        try {
            mActivityTestRules.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    contactManager.wipe();
                    contactManager.createContact("first", "last", "title", "country", "FEMALE");
                    contactManager.createContact("erste", "letzte", "titel", "land", "MALE");
                    contactManager.createContact("zweiter", "letzte", "titel", "land", "MALE");
                    contactManager.createContact("oui", "ouioui", "titlé", "francais", "FEMALE");

                }

            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        onView(withId(R.id.addFilter)).perform(click());
    }


    @Test
    public void addGenderFilter() {


        onView(withId(R.id.filter_female_radioButton))
                .perform(click());

        assertTrue("Size wasn't reduced to 2", filter.getGenderList().size() == 2);
        assertTrue("FEMALE wasn't removed from filter (0)", !filter.getGenderList().get(0).equals("FEMALE"));
        assertTrue("FEMALE wasn't removed from filter (1)", !filter.getGenderList().get(1).equals("FEMALE"));

        onView(withId(R.id.filter_male_radioButton))
                .perform(click());

        assertTrue("Size wasn't reduced to 1", filter.getGenderList().size() == 1);
        assertTrue("MALE wasn't removed from filter (0)", !filter.getGenderList().get(0).equals("MALE"));

        onView(withId(R.id.filter_female_radioButton))
                .perform(click());

        assertTrue("Size wasn't increased to 2", filter.getGenderList().size() == 2);
        assertTrue("FEMALE wasn't added to filter (0 or 1)", (filter.getGenderList().get(0).equals("FEMALE") || filter.getGenderList().get(1).equals("FEMALE")));


        filter.resetFilter();

        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

    }

    @Test
    public void addSorter() {

        onView(withId(R.id.first_ASC_radioButton))
                .perform(click());

        assertTrue("Sorter direction wrong: ASC", sorter.getDirection().equals("ASC"));
        assertTrue("Sorter sortedBy wrong: first", sorter.getSortedBy().equals(ContactManager.COLUMN_FIRSTNAME));

        onView(withId(R.id.last_ASC_radioButton))
                .perform(click());

        assertTrue("Sorter direction wrong: ASC", sorter.getDirection().equals("ASC"));
        assertTrue("Sorter sortedBy wrong: last", sorter.getSortedBy().equals(ContactManager.COLUMN_LASTNAME));


        onView(withId(R.id.last_DESC_radioButton))
                .perform(click());

        assertTrue("Sorter direction wrong: DESC", sorter.getDirection().equals("DESC"));
        assertTrue("Sorter sortedBy wrong: last", sorter.getSortedBy().equals(ContactManager.COLUMN_LASTNAME));

        onView(withId(R.id.first_DESC_radioButton))
                .perform(click());

        assertTrue("Sorter direction wrong: DESC", sorter.getDirection().equals("DESC"));
        assertTrue("Sorter sortedBy wrong: first", sorter.getSortedBy().equals(ContactManager.COLUMN_FIRSTNAME));

        filter.resetFilter();

        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

    }
}
