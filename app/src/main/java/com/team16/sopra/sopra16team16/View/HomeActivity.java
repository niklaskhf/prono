package com.team16.sopra.sopra16team16.View;


import android.app.FragmentManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FilterQueryProvider;
import android.widget.ImageButton;
import android.widget.ListView;

import com.team16.sopra.sopra16team16.Controller.ContactCursorAdapter;
import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.Model.Gender;
import com.team16.sopra.sopra16team16.R;


/**
 * HomeActivity
 * Contains methods for initializing the various elements
 */
public class HomeActivity extends AppCompatActivity {
    private String[] mOptionsDummy = new String[]{"Favorites", "Settings", "About"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private static ContactManager contactManager;
    public static Context contextOfApplication;




    private ContactListFragment fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_home);
        contextOfApplication = MyApp.getContext();

        // initialize Toolbar
        initializeToolbar();

        // initialize menu drawer
        initializeMenu();

        // add the ListFragment
        initializeFragments();

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // add searchItem to toolbar
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        FilterQueryProvider test = new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence charSequence) {
                contactManager.getSearchAdapter().getCursor();
                return null;
            }
        };

        searchView.setSuggestionsAdapter(contactManager.getSearchAdapter());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // configure menuItems in toolbar
        // only search right now
        // possibly also filter/sort?
        switch (item.getItemId()) {
            case R.id.action_search:
                // open search
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Initializes the toolbar
     */
    public void initializeToolbar() {
        // get the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        // set it
        setSupportActionBar(myToolbar);
        // configure it
        myToolbar.setTitle("");
        myToolbar.setSubtitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // add the drawer action
        ImageButton drawer = (ImageButton) findViewById(R.id.action_menu);

        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        });
    }

    /**
     * Initializes the menu drawer
     */
    public void initializeMenu() {
        // populate the drawer ListView
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mOptionsDummy));

        // add the drawer action
        ImageButton drawer = (ImageButton) findViewById(R.id.action_menu);

        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        });
    }

    /**
     * Initializes the main content of the activity
     */
    public void initializeFragments() {
        // add the ListFragment
        FragmentManager fragmentManager = getFragmentManager();
        fragment = new ContactListFragment();
        fragmentManager.beginTransaction().add(R.id.content_frame, fragment).commit();

        // populate the ListView
        contactManager = ContactManager.getInstance(contextOfApplication);

        // set cursorAdapter
        fragment.setListAdapter(contactManager.getCursorAdapterDefault());



        // testing add button
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.addNew);

        // this is just for testing right now
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactManager.createContact("first", "last", "title", "germany", Gender.MALE);
            }
        });
        // TESTING TESTING TESTING
    }

    /**
     * Starts an activity UNKONWN_NAME to add a new contact.
     */
    public void addNewContact() {
        // open new activity etc etc
        // intent ...
    }


    @Override
    public void onResume() {
        super.onResume();
        contactManager.updateCursorAdapter();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    /**
     * Returns the application Context object
     * @return Context object of the application
     */
    public static Context getContextOfApplication() {
        return contextOfApplication;
    }
}
