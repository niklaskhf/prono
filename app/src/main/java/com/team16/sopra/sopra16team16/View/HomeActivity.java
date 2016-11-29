package com.team16.sopra.sopra16team16.View;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
    private FragmentManager fragmentManager;
    private ContactListFragment fragment;
    private FloatingActionButton addButton;
    private FilterQueryProvider filterQuery;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_home);
        contextOfApplication = MyApp.getContext();
        contactManager = ContactManager.getInstance(this);

        // initialize Toolbar
        initializeToolbar();

        // initialize menu drawer
        initializeMenu();

        // add the ListFragment
        initializeFragments();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // add searchItem to toolbar
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        // TODO discuss if this is the right approach
        // hide the dropdown from the searchbar
        hideSearchDropDown(searchView);
        // apply functionality to searchItem
        searchFilterActions(searchItem);

        // set searchView adapter
        // TODO depending on dropdown searchAdapter is unnecessary
        contactManager.getSearchAdapter().setFilterQueryProvider(filterQuery);
        searchView.setSuggestionsAdapter(contactManager.getSearchAdapter());
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Hides the dropDown of the searchView
     * @param searchView
     */
    public void hideSearchDropDown(SearchView searchView) {
        // id of AutoCompleteTextView
        int searchEditTextId = R.id.search_src_text; // for AppCompat

        // get AutoCompleteTextView from SearchView
        final AutoCompleteTextView searchEditText = (AutoCompleteTextView) searchView.findViewById(searchEditTextId);

        // remove dropdown
        searchEditText.setDropDownHeight(0);
    }


    /**
     * Sets the appropriate adapters.
     * @param searchItem MenuItem related to the searchView
     */
    public void searchFilterActions(MenuItem searchItem) {
        // filterQuery object that will be applied to the CursorAdapter
        filterQuery = new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence charSequence) {
                Cursor mCursor = null;
                if (charSequence != null) {
                    mCursor = contactManager.getSearchResults(charSequence.toString());
                }
                return mCursor;
            }
        };

        // actionlisteners for open/close
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                Log.i("listAdapter", "is now filterQuery");
                fragment.setListAdapter(new ContactCursorAdapter(getApplicationContext(), filterQuery.runQuery("")));
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                Log.i("listAdapter", "is now default");
                fragment.setListAdapter(contactManager.getCursorAdapterDefault());
                return true;
            }
        });

        // actionlisteners for typing/submit
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //contactManager.getCursorAdapterDefault().setFilterQueryProvider(filterQuery);
                fragment.setListAdapter(new ContactCursorAdapter(getApplicationContext(), filterQuery.runQuery(query)));
                // hide the keyboard
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Log.i("listAdapter", "is now filterQuery");
                // TODO discuss if this is intended, slows everything down
                fragment.setListAdapter(new ContactCursorAdapter(getApplicationContext(), filterQuery.runQuery(newText)));
                return true;
            }
        });
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
        fragmentManager = getFragmentManager();
        fragment = new ContactListFragment();
        fragmentManager.beginTransaction().add(R.id.content_frame, fragment).commit();

        String first, last, title, country;
        Bundle bundle = getIntent().getExtras();

        // populate the ListView
        // set cursorAdapter
        fragment.setListAdapter(contactManager.getCursorAdapterDefault());


        if(bundle != null) {
            first = bundle.getString(contactManager.COLUMN_FIRSTNAME);
            last = bundle.getString(contactManager.COLUMN_LASTNAME);
            title = bundle.getString(contactManager.COLUMN_TITLE);
            country = bundle.getString(contactManager.COLUMN_COUNTRY);
            // TODO this is not working, intent gets called on every app launch!
            // contactManager.createContact(first, last, title, country, Gender.MALE );
        }
        // testing add button
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.addNew);
        addNewContact(addButton);
    }

    /**
     * Starts an activity UNKONWN_NAME to add a new contact.
     */
    public void addNewContact(FloatingActionButton addButton) {
        addButton = (FloatingActionButton) findViewById(R.id.addNew);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, NewContactActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        contactManager.updateCursorAdapter();
        //contactManager.open();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // according to google theres nothing wrong with keeping the connection open
        // and letting the kernel handle the cleanup after exiting
        // http://stackoverflow.com/questions/6608498/best-place-to-close-database-connection
        //contactManager.close();
    }
}