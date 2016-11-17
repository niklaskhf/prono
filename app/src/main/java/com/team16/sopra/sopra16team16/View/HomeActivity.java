package com.team16.sopra.sopra16team16.View;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.Model.Contact;
import com.team16.sopra.sopra16team16.Controller.ListAdapter;
import com.team16.sopra.sopra16team16.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private String[] mPlanetTitles = new String[]{"Favorites", "Settings", "About"};
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] testArray = new String[] {"test", "test", "test"};
    private ArrayList<Contact> testCollection = new ArrayList<Contact>();
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_home);

        // configure Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle("");
        myToolbar.setSubtitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        // populate Collection with dummy items
        for (int i = 0; i < 50; i++) {
            testCollection.add(new Contact("first","last","title","country", "gender"));
        }




        // add the ListFragment
        FragmentManager fragmentManager = getFragmentManager();
        ContactListFragment fragment = new ContactListFragment();
        fragmentManager.beginTransaction().add(R.id.content_frame, fragment).commit();

        // populate the ListView
        fragment.setListAdapter(new ListAdapter(this, R.layout.contact_item, testCollection));

        // populate the drawer ListView
        //mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // populate the drawer ListView
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mPlanetTitles));


        Button drawer = (Button) findViewById(R.id.action_menu);

        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        });


//        tv = (TextView) findViewById(R.id.dummy_sorter);
//        tv.setVisibility(View.GONE);
//        Button expandSorter = (Button) findViewById(R.id.sort_button);
//
//        OnClickListener expand = new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (tv.getVisibility() == View.GONE) {
//                    expandOrCollapse(tv, "expand");
//                }
//                else {
//                    expandOrCollapse(tv, "collapse");
//                }
//            }
//        };
//
//        expandSorter.setOnClickListener(expand);

    }

    public void expandOrCollapse(final View v,String exp_or_colpse) {
        TranslateAnimation anim = null;
        if(exp_or_colpse.equals("expand"))
        {
            anim = new TranslateAnimation(0.0f, 0.0f, -v.getHeight(), 0.0f);
            v.setVisibility(View.VISIBLE);
        }
        else{
            anim = new TranslateAnimation(0.0f, 0.0f, 0.0f, -v.getHeight());
            AnimationListener collapselistener= new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    v.setVisibility(View.GONE);
                }
            };

            anim.setAnimationListener(collapselistener);
        }

        // To Collapse
        //

        anim.setDuration(300);
        anim.setInterpolator(new AccelerateInterpolator(0.5f));
        v.startAnimation(anim);
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // TODO CHANGE THIS TO SEARCH DIALOG
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
}
