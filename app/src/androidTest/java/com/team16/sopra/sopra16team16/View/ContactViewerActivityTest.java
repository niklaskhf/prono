package com.team16.sopra.sopra16team16.View;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ImageView;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.Controller.Filter;
import com.team16.sopra.sopra16team16.Controller.Player;
import com.team16.sopra.sopra16team16.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by prime on 08.12.16.
 */

public class ContactViewerActivityTest {
    private ContactManager contactManager;
    Instrumentation.ActivityMonitor viewerMonitor = getInstrumentation().addMonitor(ContactViewerActivity.class.getName(), null, false);

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRules = new ActivityTestRule<HomeActivity>(HomeActivity.class);


    @Before
    public void setup() {
        Filter filter = Filter.getCurrentInstance();
        filter.resetFilter();
        contactManager = ContactManager.getInstance(InstrumentationRegistry.getTargetContext());
        try {
            mActivityTestRules.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    contactManager.wipe();
                    contactManager.createContact("first", "last", "title", "country", "FEMALE");
                    contactManager.createContact("erste", "letzte", "titel", "land", "MALE");
                    contactManager.createContact("oui", "ouioui", "titlé", "francais", "UNKNOWN");
                    contactManager.createContact("oui", "ouioui", "titlé", "francais", "");
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Test
    public void testOpenViewer() {
        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(0)
                .perform(click());
        ContactViewerActivity nextActivity = (ContactViewerActivity) getInstrumentation().waitForMonitorWithTimeout(viewerMonitor, 5000);
        assertNotNull(nextActivity);

        TextView firstName = (TextView) nextActivity.findViewById(R.id.real_first_name);
        TextView lastName = (TextView) nextActivity.findViewById(R.id.real_last_name);
        TextView title = (TextView) nextActivity.findViewById(R.id.real_title);
        TextView country = (TextView) nextActivity.findViewById(R.id.real_country);
        ImageView gender = (ImageView) nextActivity.findViewById(R.id.gender_sign);

        String genderTag = (String) gender.getTag();
        assertTrue("first name wrong", firstName.getText().toString().equals("first"));
        assertTrue("last name wrong", lastName.getText().toString().equals("last"));
        assertTrue("title is wrong", title.getText().toString().equals("title"));
        assertTrue("country is wrong", country.getText().toString().equals("country"));
        // lmao its too late for this
        assertTrue("gender is wrong", gender.getDrawable().getConstantState() == getTargetContext().getResources().getDrawable(R.drawable.ic_female_gender).getConstantState());

        onView(withId(R.id.edit_button)).perform(click());
        onView(withId(R.id.male_radioButton)).check(matches(isNotChecked()));
        onView(withId(R.id.female_radioButton)).check(matches(isChecked()));
        onView(withId(R.id.unkown_radioButton)).check(matches(isNotChecked()));
        pressBack();

        Espresso.pressBack();
    }


    @Test
    public void openContactTest2() {
        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(1)
                .perform(click());
        ContactViewerActivity nextActivity = (ContactViewerActivity) getInstrumentation().waitForMonitorWithTimeout(viewerMonitor, 5000);
        assertNotNull(nextActivity);

        TextView firstName = (TextView) nextActivity.findViewById(R.id.real_first_name);
        TextView lastName = (TextView) nextActivity.findViewById(R.id.real_last_name);
        TextView title = (TextView) nextActivity.findViewById(R.id.real_title);
        TextView country = (TextView) nextActivity.findViewById(R.id.real_country);
        ImageView gender = (ImageView) nextActivity.findViewById(R.id.gender_sign);

        String genderTag = (String) gender.getTag();
        assertTrue("first name wrong", firstName.getText().toString().equals("erste"));
        assertTrue("last name wrong", lastName.getText().toString().equals("letzte"));
        assertTrue("title is wrong", title.getText().toString().equals("titel"));
        assertTrue("country is wrong", country.getText().toString().equals("land"));
        // lmao its too late for this
        assertTrue("gender is wrong", gender.getDrawable().getConstantState() == getTargetContext().getResources().getDrawable(R.drawable.ic_male_gender).getConstantState());

        onView(withId(R.id.edit_button)).perform(click());
        onView(withId(R.id.male_radioButton)).check(matches(isChecked()));
        onView(withId(R.id.female_radioButton)).check(matches(isNotChecked()));
        onView(withId(R.id.unkown_radioButton)).check(matches(isNotChecked()));
        pressBack();
        Espresso.pressBack();
    }

    @Test
    public void openContactTest3() {
        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(2)
                .perform(click());
        ContactViewerActivity nextActivity = (ContactViewerActivity) getInstrumentation().waitForMonitorWithTimeout(viewerMonitor, 5000);
        assertNotNull(nextActivity);

        TextView firstName = (TextView) nextActivity.findViewById(R.id.real_first_name);
        TextView lastName = (TextView) nextActivity.findViewById(R.id.real_last_name);
        TextView title = (TextView) nextActivity.findViewById(R.id.real_title);
        TextView country = (TextView) nextActivity.findViewById(R.id.real_country);
        ImageView gender = (ImageView) nextActivity.findViewById(R.id.gender_sign);

        String genderTag = (String) gender.getTag();
        assertTrue("first name wrong", firstName.getText().toString().equals("oui"));
        assertTrue("last name wrong", lastName.getText().toString().equals("ouioui"));
        assertTrue("title is wrong", title.getText().toString().equals("titlé"));
        assertTrue("country is wrong", country.getText().toString().equals("francais"));
        // lmao its too late for this
        assertTrue("gender is wrong", gender.
                getDrawable().
                getConstantState()
                ==
                getTargetContext().getResources().
                        getDrawable(android.R.drawable.sym_def_app_icon).
                        getConstantState());

        onView(withId(R.id.edit_button)).perform(click());
        onView(withId(R.id.male_radioButton)).check(matches(isNotChecked()));
        onView(withId(R.id.female_radioButton)).check(matches(isNotChecked()));
        onView(withId(R.id.unkown_radioButton)).check(matches(isChecked()));
        pressBack();
        Espresso.pressBack();
    }

}
