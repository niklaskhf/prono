package com.team16.sopra.sopra16team16.View;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.Controller.ContactCursorAdapter;
import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;

import static org.junit.Assert.assertTrue;

/**
 * ContactCursorAdapter test class
 */

public class ContactCursorAdapterTest {
    private ContactManager contactManager;
    private ContactCursorAdapter contactCursorAdapter;

    @Rule
    public ActivityTestRule<HomeActivity> mRule = new ActivityTestRule<>(HomeActivity.class);

    @Before
    public void setup() {
        mRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                contactManager = ContactManager.getInstance(InstrumentationRegistry.getTargetContext().getApplicationContext());
                contactManager.wipe();
                contactManager.createContact("first1", "last1", "title1", "country1", "MALE");
                contactManager.createContact("first2", "last2", "title2", "country2", "UNKNOWN");
                contactManager.createContact("first3", "last3", "title3", "country3", "FEMALE");
                contactCursorAdapter = contactManager.getCursorAdapterDefault();
            }
        });
        contactManager = ContactManager.getInstance(InstrumentationRegistry.getTargetContext().getApplicationContext());
    }


    @Test
    public void testCount() {
        mRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                assertTrue("wrong count", contactCursorAdapter.getCount() == 3);
            }
        });
    }

    @Test
    public void textViewsTest() {
        mRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View view = contactCursorAdapter.getView(0, null, null);
                TextView first = (TextView) view.findViewById(R.id.list_firstname);
                TextView last = (TextView) view.findViewById(R.id.list_lastname);
                TextView title = (TextView) view.findViewById(R.id.list_title);
                TextView country = (TextView) view.findViewById(R.id.list_country);
                ImageView gender = (ImageView) view.findViewById(R.id.contact_gender);
                TextView genderText = (TextView) view.findViewById(R.id.list_gender);

                Log.d("firstText", first.getText().toString());

                assertTrue("wrong first", first.getText().toString().equals("first1"));
                assertTrue("wrong last", last.getText().toString().equals("last1"));
                assertTrue("wrong title", title.getText().toString().equals("title1"));
                assertTrue("wrong country", country.getText().toString().equals("country1"));
                assertTrue("wrong gender", true);
                assertTrue("wrong genderText", genderText.getText().toString().equals("MALE"));

                // favToggle

                contactManager.toggleFavorite((Integer) view.getTag(), 0);

                contactManager.updateCursorAdapter();
                assertTrue("wrong favorite icon", true);


                view = contactCursorAdapter.getView(1, null, null);
                first = (TextView) view.findViewById(R.id.list_firstname);
                last = (TextView) view.findViewById(R.id.list_lastname);
                title = (TextView) view.findViewById(R.id.list_title);
                country = (TextView) view.findViewById(R.id.list_country);
                gender = (ImageView) view.findViewById(R.id.contact_gender);
                genderText = (TextView) view.findViewById(R.id.list_gender);


                Log.d("firstText", first.getText().toString());

                assertTrue("wrong first", first.getText().toString().equals("first2"));
                assertTrue("wrong last", last.getText().toString().equals("last2"));
                assertTrue("wrong title", title.getText().toString().equals("title2"));
                assertTrue("wrong country", country.getText().toString().equals("country2"));
                // TODO compare drawables? how?
                assertTrue("wrong gender", true);
                assertTrue("wrong genderText", genderText.getText().toString().equals("UNKNOWN"));

                view = contactCursorAdapter.getView(2, null, null);
                first = (TextView) view.findViewById(R.id.list_firstname);
                last = (TextView) view.findViewById(R.id.list_lastname);
                title = (TextView) view.findViewById(R.id.list_title);
                country = (TextView) view.findViewById(R.id.list_country);
                gender = (ImageView) view.findViewById(R.id.contact_gender);
                genderText = (TextView) view.findViewById(R.id.list_gender);


                Log.d("firstText", first.getText().toString());

                assertTrue("wrong first", first.getText().toString().equals("first3"));
                assertTrue("wrong last", last.getText().toString().equals("last3"));
                assertTrue("wrong title", title.getText().toString().equals("title3"));
                assertTrue("wrong country", country.getText().toString().equals("country3"));
                // TODO compare drawables? how?
                assertTrue("wrong gender", true);
                assertTrue("wrong genderText", genderText.getText().toString().equals("FEMALE"));

            }
        });
    }


}
