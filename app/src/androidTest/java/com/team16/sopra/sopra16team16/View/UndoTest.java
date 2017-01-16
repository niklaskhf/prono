package com.team16.sopra.sopra16team16.View;


import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.widget.ImageView;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.Controller.FileUtils;
import com.team16.sopra.sopra16team16.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UndoTest {

    private ContactManager contactManager;
    Instrumentation.ActivityMonitor viewerMonitor = getInstrumentation().addMonitor(ContactViewerActivity.class.getName(), null, false);

    @Rule
    public ActivityTestRule<HomeActivity> mRule = new ActivityTestRule<>(HomeActivity.class);


    @Before
    public void setup() {
        contactManager = ContactManager.getInstance(InstrumentationRegistry.getTargetContext());
        contactManager.wipe();
    }


    public void undoDeleteTest() {
        createContact();
        deleteContact();

        assertTrue(contactManager.selectContacts().getCount() == 0);

        // need to do this click manually ??
        onView(allOf(withId(android.support.design.R.id.snackbar_action)))
                .perform(click());

        assertTrue(ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).selectContacts().getCount() == 1);

        deleteContact();
        createContact();
        deleteContact();

        assertTrue(ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).selectContacts().getCount() == 0);

        assertTrue(!new File(FileUtils.PATH + ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).getId() + ".3gp").exists());

    }


    public void createContact() {
        onView(withId(R.id.addNew)).perform(click());

        onView(withId(R.id.first_edit)).perform(typeText(" first"));
        onView(withId(R.id.last_edit)).perform(typeText(" last"));
        onView(withId(R.id.country_edit)).perform(typeText(" ger "));
        onView(withId(R.id.title_edit)).perform(typeText(" ger "));

        Espresso.closeSoftKeyboard();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.record_button)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.record_button)).perform(click());

        onView(withId(R.id.accept_dialog)).perform(click());
        onView(withId(R.id.confirm_button)).perform(click());

        // back to HomeActivity
        Espresso.pressBack();
    }


    public void deleteContact() {
        // click on contact
        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(0)
                .perform(click());

        // delete contact


        onView(withId(R.id.delete_button)).perform(click());
        onView(withText("YES")).perform(click());


    }


    @Test
    public void undoEditTest() {

        createContact();

        onData(anything()).inAdapterView(withId(R.id.home_fragment)).atPosition(0)
                .perform(click());
        ContactViewerActivity nextActivity = (ContactViewerActivity) getInstrumentation().waitForMonitorWithTimeout(viewerMonitor, 5000);
        assertNotNull(nextActivity);

        TextView firstName = (TextView) nextActivity.findViewById(R.id.real_first_name);
        TextView lastName = (TextView) nextActivity.findViewById(R.id.real_last_name);
        TextView title = (TextView) nextActivity.findViewById(R.id.real_title);
        TextView country = (TextView) nextActivity.findViewById(R.id.real_country);
        ImageView gender = (ImageView) nextActivity.findViewById(R.id.gender_sign);

        assertTrue(firstName.getText().equals("First"));
        assertTrue(lastName.getText().equals("Last"));
        assertTrue(country.getText().equals("Ger"));
        File prevRec = new File(FileUtils.PATH + ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).getId() + ".3gp");

        long prevRecSize = prevRec.length();

        onView(withId(R.id.edit_button)).perform(click());

        onView(withId(R.id.first_edit)).perform(typeText("first"));
        onView(withId(R.id.last_edit)).perform(typeText("last"));
        onView(withId(R.id.country_edit)).perform(typeText("get"));
        Espresso.closeSoftKeyboard();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.female_radioButton)).perform(click());

        onView(withId(R.id.record_button)).perform(click());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // should time out on its own ..
        onView(withId(R.id.accept_dialog)).perform(click());

        onView(withId(R.id.confirm_button)).perform(click());


        assertTrue(new File(FileUtils.PATH + ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).getId() + "_undo.3gp").exists());
        assertTrue(firstName.getText().equals("Firstfirst"));
        assertTrue(lastName.getText().equals("Lastlast"));
        assertTrue(country.getText().equals("Gerget"));
        // check gender ..
        File newRec = new File(FileUtils.PATH + ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).getId() + ".3gp");

        long newRecSize = newRec.length();

        assertTrue(newRecSize > prevRecSize);


        onView(allOf(withId(android.support.design.R.id.snackbar_action)))
                .perform(click());

        assertTrue(!new File(ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).getId() + "_undo.3gp").exists());
        assertTrue(firstName.getText().equals("First"));
        assertTrue(lastName.getText().equals("Last"));
        assertTrue(country.getText().equals("Ger"));

        newRec = new File(FileUtils.PATH + ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).getId() + ".3gp");

        newRecSize = newRec.length();

        assertTrue(newRecSize == prevRecSize);
        pressBack();
        deleteContact();

    }
}
