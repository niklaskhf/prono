package com.team16.sopra.sopra16team16.View;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FilterQueryProvider;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.team16.sopra.sopra16team16.Controller.Backup;
import com.team16.sopra.sopra16team16.Controller.ContactCursorAdapter;
import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.R;

import java.util.Locale;


/**
 * HomeActivity
 * Contains methods for initializing the various elements
 */
public class HomeActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ContactManager contactManager;
    public static Context contextOfApplication;
    private FragmentManager fragmentManager;
    private ContactListFragment listFragment;
    private FloatingActionButton addButton;
    private FilterQueryProvider filterQuery;
    private SearchView searchView;
    private Toolbar myToolbar;
    private int mode;
    private MenuItem searchItem;
    private ArrayAdapter<String> drawerAdapter;
    private String updatedLanguage;



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

        // if app language differs from default language
        initializeNewChosenLanguage();

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
        searchItem = menu.findItem(R.id.action_search);
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
                listFragment.setListAdapter(new ContactCursorAdapter(getApplicationContext(), filterQuery.runQuery("")));
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                Log.i("listAdapter", "is now default");
                //contactManager.getCursorAdapterDefault().setFilterQueryProvider(null);
                listFragment.setListAdapter(contactManager.getCursorAdapterDefault());
                return true;
            }
        });

        // actionlisteners for typing/submit
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //contactManager.getCursorAdapterDefault().setFilterQueryProvider(filterQuery);
                listFragment.setListAdapter(new ContactCursorAdapter(getApplicationContext(), filterQuery.runQuery(query)));
                // hide the keyboard
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Log.i("listAdapter", "is now filterQuery");
                listFragment.setListAdapter(new ContactCursorAdapter(getApplicationContext(), filterQuery.runQuery(newText)));
                return true;
            }
        });
    }

    /**
     * Initializes the toolbar
     */
    public void initializeToolbar() {
        // get the toolbar
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        // set it
        setSupportActionBar(myToolbar);
        // configure it
        myToolbar.setTitle("");
        myToolbar.setSubtitle("");
        myToolbar.setNavigationIcon(R.drawable.ic_drawer);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
        drawerAdapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, getResources().getStringArray(R.array.drawer));
        mDrawerList.setAdapter(drawerAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onNavigationDrawerItemSelected(i);
                mDrawerLayout.closeDrawer(mDrawerList);
                toggleSubMode();
            }
        });
    }

    /**
     * Initializes the main content of the activity
     */
    public void initializeFragments() {
        // add the ListFragment
        fragmentManager = getFragmentManager();
        listFragment = new ContactListFragment();

        Bundle args = new Bundle();
        fragmentManager.beginTransaction().add(R.id.content_frame, listFragment).commit();

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
        return listFragment;
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

    public void initializeNewChosenLanguage() {
        SharedPreferences myLanguagePreference = getSharedPreferences("getLanguage", Context.MODE_PRIVATE);
        if (myLanguagePreference.getString("updatedLanguage",null) != null) {
            updatedLanguage = myLanguagePreference.getString("updatedLanguage",null);
        }
        if ((updatedLanguage != null)) {
            setLocale(updatedLanguage, HomeActivity.this.getResources());
        }
    }

    /**
     * changes app language
     * @param lang
     * @param res
     */
    public void setLocale(String lang, Resources res) {
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(lang);
        res.updateConfiguration(conf, dm);
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
        contactManager.deleteMarked();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.content_frame);

                String action = data.getStringExtra("action");
                if (action != null && action.equals("undo")) {
                    final int undoId = data.getIntExtra("undoId", -1);
                    Log.d("undoSnackbar", "showing snackbar for " + undoId);
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Deleted a contact", Snackbar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Snackbar snackbarSuccess = Snackbar.make(coordinatorLayout, "Restored a contact", Snackbar.LENGTH_SHORT);
                                    snackbarSuccess.show();

                                    contactManager.toggleDeleted(undoId, 1);
                                }
                            });

                    snackbar.show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        // either close the open drawer
        // or close the app
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        } else if (mode == 1) {
            toggleMainMode();
        }
        else {
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
            listFragment.setListAdapter(contactManager.getCursorAdapterDefault());
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
                    listFragment.setListAdapter(contactManager.getCursorAdapterDefault());
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


    /**
     * Opens a fragment depending on the position, that was clicked on
     * @param position
     */
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        FragmentManager fragmentManager = getFragmentManager(); // For AppCompat use getSupportFragmentManager
        switch(position) {
            default:
            case 0:
                fragment = listFragment;
                listFragment.setListAdapter(contactManager.getCursorAdapterFavorite());
                break;
            case 1:
                fragment = new SettingsFragment();
                break;
            case 2:
                fragment = new AboutFragment();
                break;
        }
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }
    }


    /**
     * Changes the toolbar/addButton to subMode (settings/about/favorites)
     */
    public void toggleSubMode() {
        mode = 1;
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleMainMode();
            }
        });
        searchItem.setVisible(false);
        addButton.setVisibility(View.INVISIBLE);
        mDrawerList.clearChoices();
        drawerAdapter.notifyDataSetChanged();
    }

    /**
     * Changes the toolbar/addButton to mainMode (default listView of contacts)
     */
    public void toggleMainMode() {
        mode = 0;
        myToolbar.setNavigationIcon(R.drawable.ic_drawer);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        });
        searchItem.setVisible(true);
        addButton.setVisibility(View.VISIBLE);
        listFragment.setListAdapter(contactManager.getCursorAdapterDefault());
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, listFragment)
                .commit();
        mDrawerList.clearChoices();
    }

}