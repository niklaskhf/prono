package com.team16.sopra.sopra16team16.View;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.Space;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FilterQueryProvider;
import android.widget.ImageButton;
import android.widget.ListView;

import com.team16.sopra.sopra16team16.Controller.ContactCursorAdapter;
import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.Controller.Filter;
import com.team16.sopra.sopra16team16.Controller.Sorter;
import com.team16.sopra.sopra16team16.R;


/**
 * HomeActivity
 * Contains methods for initializing the various elements
 */
public class HomeActivity extends AppCompatActivity {
    private String[] mOptionsDummy = new String[]{"Favorites", "Settings", "About"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ContactManager contactManager;
    public static Context contextOfApplication;
    private FragmentManager fragmentManager;
    private ContactListFragment fragment;
    private FloatingActionButton addButton;
    private FloatingActionButton filterButton;
    private FilterQueryProvider filterQuery;
    private SearchView searchView;



    // Storage Permissions
    private static final int REQUEST_ALL = 0;
    private static final int REQUEST_MICROPHONE = 2;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static String[] PERMISSIONS_ALL = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_home);
        contextOfApplication = this.getApplicationContext();
        contactManager = ContactManager.getInstance(this.getApplicationContext());

        //initialize Sorter and Filter
        Filter filter = Filter.getCurrentInstance();
        Sorter sorter = Sorter.getCurrentInstance();

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

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Hides the dropDown of the searchView
     *
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
     *
     * @param searchItem MenuItem related to the searchView
     */
    public void searchFilterActions(MenuItem searchItem) {
        // filterQuery object that will be applied to the CursorAdapter
        filterQuery = new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence charSequence) {
                Cursor mCursor = null;
                mCursor = contactManager.getSearchResults(charSequence.toString());
                return mCursor;
            }
        };

        // actionlisteners for open/close
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                Log.i("listAdapter", "is now filterQuery");
                //contactManager.getCursorAdapterDefault().setFilterQueryProvider(filterQuery);
                fragment.setListAdapter(new ContactCursorAdapter(getApplicationContext(), filterQuery.runQuery("")));
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                Log.i("listAdapter", "is now default");
                //contactManager.getCursorAdapterDefault().setFilterQueryProvider(null);
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
                fragment.setListAdapter(new ContactCursorAdapter(getApplicationContext(), filterQuery.runQuery(newText)));
                return true;
            }
        });
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

        Bundle args = new Bundle();
        fragmentManager.beginTransaction().add(R.id.content_frame, fragment).commit();

        // do we even have permissions?
        verifyStoragePermissions(this);

        // populate the ListView
        // set cursorAdapter
        // moved this to permission request
        //fragment.setListAdapter(contactManager.getCursorAdapterDefault());

    }

    /**
     * Returns the fragment object, for testing purposes
     */
    public ContactListFragment getFragment() {
        return fragment;
    }

    /**
     * Starts an activity UNKONWN_NAME to add a new contact.
     */
    public void addNewContact() {
        addButton = (FloatingActionButton) findViewById(R.id.addNew);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verifyStoragePermissions(HomeActivity.this);
                int id = contactManager.getId();
                id++;
                // opens a NewContactActivity with empty EditTexts
                Intent intent = new Intent(HomeActivity.this, NewContactActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("first", "");
                bundle.putString("last", "");
                bundle.putString("title", "");
                bundle.putString("country", "");
                bundle.putString("gender", "");
                bundle.putInt("id", id);
                // CREATION mode
                bundle.putString("cause", "CREATE");

                intent.putExtras(bundle);
                startActivity(intent);
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
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // according to google theres nothing wrong with keeping the connection open
        // and letting the kernel handle the cleanup after exiting
        // http://stackoverflow.com/questions/6608498/best-place-to-close-database-connection
        //contactManager.close();
    }

    @Override
    public void onBackPressed() {
        // either close the open drawer
        // or close the app
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            finish();
        }
    }

    /**
     * Checks if searchView is visible, used for testing
     */
    public boolean searchVisible() {
        return !searchView.isIconified();
    }


    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     * <p>
     * Currently unused, might be used later on!
     *
     * @param activity
     */
    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int micPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);


        if (permission != PackageManager.PERMISSION_GRANTED || micPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_ALL,
                    REQUEST_ALL
            );
        } else {
            fragment.setListAdapter(contactManager.getCursorAdapterDefault());
            addNewContact();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        final Activity activity = this;
        switch (requestCode) {
            case REQUEST_ALL:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    // do nothing?
                    addNewContact();
                    fragment.setListAdapter(contactManager.getCursorAdapterDefault());
                } else {
                    addButton = (FloatingActionButton) findViewById(R.id.addNew);
                    addButton.setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View view) {
                            verifyStoragePermissions(activity);
                        }
                    });
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void openFilter(View view) {
        Intent intent = new Intent(this, FilterActivity.class);
        startActivity(intent);
        return;
    }

}