package com.team16.sopra.sopra16team16.Controller;

import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.R;
import com.team16.sopra.sopra16team16.View.HomeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertTrue;


public class BackupTest {

    @Rule
    public ActivityTestRule<HomeActivity> mRule = new ActivityTestRule<>(HomeActivity.class);


    @Test
    public void backupImportResetTest() {
        ContactManager.getInstance(InstrumentationRegistry.getTargetContext()).wipe();
        createContacts();
        backupTest();
        resetTest();
        importTest();
    }

    public void createContacts() {
        onView(withId(R.id.addNew)).perform(click());

        onView(withId(R.id.first_edit)).perform(typeText("first1"));
        onView(withId(R.id.last_edit)).perform(typeText("last1"));
        onView(withId(R.id.title_edit)).perform(typeText("dr"));
        onView(withId(R.id.country_edit)).perform(typeText("country1"));
        onView(withId(R.id.male_radioButton)).perform(click());

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.record_button)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.record_button)).perform(click());
        onView(withId(R.id.accept_dialog)).perform(click());

        onView(withId(R.id.confirm_button)).perform(click());


        pressBack();






        onView(withId(R.id.addNew)).perform(click());

        onView(withId(R.id.first_edit)).perform(typeText("first2"));
        onView(withId(R.id.last_edit)).perform(typeText("last2"));
        onView(withId(R.id.title_edit)).perform(typeText("dr"));
        onView(withId(R.id.country_edit)).perform(typeText("country2"));
        onView(withId(R.id.male_radioButton)).perform(click());

        Espresso.closeSoftKeyboard();


        onView(withId(R.id.record_button)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.record_button)).perform(click());
        onView(withId(R.id.accept_dialog)).perform(click());


        onView(withId(R.id.confirm_button)).perform(click());


        pressBack();


        onView(withId(R.id.addNew)).perform(click());

        onView(withId(R.id.first_edit)).perform(typeText("first3"));
        onView(withId(R.id.last_edit)).perform(typeText("last3"));
        onView(withId(R.id.title_edit)).perform(typeText("dr"));
        onView(withId(R.id.country_edit)).perform(typeText("country3"));
        onView(withId(R.id.male_radioButton)).perform(click());

        Espresso.closeSoftKeyboard();


        onView(withId(R.id.record_button)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.record_button)).perform(click());
        onView(withId(R.id.accept_dialog)).perform(click());


        onView(withId(R.id.confirm_button)).perform(click());


        pressBack();

    }


    public void backupTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")),
                        withParent(withId(R.id.my_toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        onView(withText("Settings")).perform(click());

        int filesBefore = Environment.getExternalStorageDirectory().listFiles().length;
        onView(withId(R.id.backup_button)).perform(click());

        int filesAfter = Environment.getExternalStorageDirectory().listFiles().length;

        assertTrue(filesBefore == filesAfter-1);
        pressBack();
    }


    public void resetTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")),
                        withParent(withId(R.id.my_toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        onView(withText("Settings")).perform(click());

        File filesDir = new File("/data/data/" + HomeActivity.contextOfApplication.getPackageName()
                + "/files/");
        int lengthBefore = filesDir.listFiles().length;

        onView(withId(R.id.reset_button)).perform(click());
        onView(withId(android.R.id.button2)).perform(click());
        onView(withId(R.id.reset_button)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        filesDir = new File("/data/data/" + HomeActivity.contextOfApplication.getPackageName()
                + "/files/");
        int lengthAfter = filesDir.listFiles().length;

        assertTrue(lengthAfter == 0 || lengthAfter == 1);
        assertTrue(lengthAfter != lengthBefore);
        pressBack();
    }

    public void importTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withClassName(is("android.support.v7.widget.AppCompatImageButton")),
                        withParent(withId(R.id.my_toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        onView(withText("Settings")).perform(click());
        onView(withId(R.id.import_button)).perform(click());
        onView(withId(android.R.id.button2)).perform(click());
        onView(withId(R.id.import_button)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        new File(Environment.getExternalStorageDirectory().getPath() + "/foo");
        onData(hasToString(startsWith("foo")))
                .perform(click());

        onView(withId(R.id.import_button)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        onData(hasToString(startsWith("..")))
                .perform(click());

        onData(hasToString(startsWith("Android")))
                .perform(click());


        // save length before to compare file change
        File filesDir = new File("/data/data/" + HomeActivity.contextOfApplication.getPackageName()
                + "/files/");
        int lengthBefore = filesDir.listFiles().length;

        onView(withId(R.id.import_button)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());

        // TODO find something else for this
        // crashes when there is more than one file prono*
        onData(hasToString(startsWith("prono")))
                .perform(click());

        filesDir = new File("/data/data/" + HomeActivity.contextOfApplication.getPackageName()
                + "/files/");
        int lengthAfter = filesDir.listFiles().length;

        assertTrue(lengthAfter != lengthBefore);

        // do some more asserts ...

        pressBack();


    }



    @After
    public void cleanup() {
        File[] files = Environment.getExternalStorageDirectory().listFiles();

        for (File e : files) {
            if (e.getPath().contains("prono")) {
                e.delete();
            }
        }
    }

}
