package com.team16.sopra.sopra16team16;

import android.app.Instrumentation;
import android.content.Context;
import android.os.Bundle;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.Model.Gender;
import com.team16.sopra.sopra16team16.View.HomeActivity;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

import static com.team16.sopra.sopra16team16.View.MyApp.getContext;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import android.support.test.InstrumentationRegistry;

/**
 * Tests the ListAdapter class
 */
/*
public class ContactListAdapterTest{
    /////////////////////////////////////////
    /////////////////////////////////////////
    /////////////////////////////////////////
    // TODO TEST THIS IN INSTRUMENTED UNIT TEST?
    // send help
    // http://stackoverflow.com/questions/11541114/unittesting-of-arrayadapter
    // doesnt work
    /////////////////////////////////////////
    /////////////////////////////////////////
    /////////////////////////////////////////

    @Mock
    Context mContext = Mockito.mock(Context.class);
    @Mock
    LayoutInflater mInflater = Mockito.mock(LayoutInflater.class);

    private ContactListAdapter mAdapter;
    private ContactListAdapter mAdapter2;

    private Contact mJohn;
    private Contact mJane;

    public ContactListAdapterTest() {
        super();
    }
    @Before
    public void setUp() throws Exception {
        ArrayList<Contact> data = new ArrayList<Contact>();

        mJohn = new Contact("John", "Duck", "Dr", "Germany", Gender.MALE, 0);
        mJane = new Contact("Jane", "Bailey", "", "Great Britain", Gender.FEMALE, 1);
        data.add(mJohn);
        data.add(mJane);
        mAdapter = new ContactListAdapter(mContext, R.layout.contact_item, data);
        mAdapter2 = new ContactListAdapter(mContext, R.layout.contact_item);
    }


    @Test
    public void testGetItem() {
        assertEquals("John was expected.", mJohn.getFirstName(),
                ((Contact) mAdapter.getItem(0)).getFirstName());
    }

    @Test
    public void testGetItemId() {
        assertEquals("Wrong ID.", 0, mAdapter.getItemId(0));
    }

    @Test
    public void testGetCount() {
        assertEquals("Contacts amount incorrect.", 2, mAdapter.getCount());
    }


    @Test
    public void testGetView() {
        Mockito.when(mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).thenReturn(mInflater);
        View view = mAdapter.getView(0, null, null);

        TextView firstName = (TextView) view
                .findViewById(R.id.list_firstname);

        TextView lastName = (TextView) view
                .findViewById(R.id.list_lastname);

        TextView title = (TextView) view
                .findViewById(R.id.list_title);

        TextView country = (TextView) view
                .findViewById(R.id.list_country);

        TextView gender = (TextView) view
                .findViewById(R.id.list_gender);

        ImageButton play = (ImageButton) view
                .findViewById(R.id.contact_play);

        ImageButton fav = (ImageButton) view
                .findViewById(R.id.contact_fav);





        //On this part you will have to test it with your own views/data
        assertNotNull("View is null. ", view);
        assertNotNull("firstName TextView is null. ", firstName);
        assertNotNull("lastName TextView is null. ", lastName);
        assertNotNull("title TextView is null. ", title);
        assertNotNull("country TextView is null. ", country);
        assertNotNull("gender TextView is null. ", gender);
        assertNotNull("play ImageButton is null. ", play);
        assertNotNull("fav ImageButton is null. ", fav);


        assertEquals("firstName doesnt match", mJohn.getFirstName(), firstName.getText());
        assertEquals("lastName doesnt match", mJohn.getLastName(), lastName.getText());
        assertEquals("title doesnt match", mJohn.getTitle(), title.getText());
        assertEquals("country doesnt match", mJohn.getCountry(), country.getText());
        // TODO string -> icon
        assertEquals("gender doesnt match", mJohn.getGender().toString(), gender.getText());

        // TODO test buttons

    }
}*/
