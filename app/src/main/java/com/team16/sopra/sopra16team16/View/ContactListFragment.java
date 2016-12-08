package com.team16.sopra.sopra16team16.View;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.widget.Space;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.R;



public class ContactListFragment extends ListFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        /* might do this at some point, kinda pointless with padding tho, unlucky

        getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.i("firstVisible", Integer.toString(firstVisibleItem));
                Log.i("visibleCount", Integer.toString(visibleItemCount));
                Log.i("totalCount", Integer.toString(totalItemCount));
                int lastItem = firstVisibleItem + visibleItemCount;
                Log.i("lastItem", Integer.toString(lastItem));
                if (firstVisibleItem != 0 && visibleItemCount <= totalItemCount) {
                    // this is awful but whatever it works
                    ((HomeActivity)getActivity()).setAddButtonVis(false);
                } else {
                    ((HomeActivity)getActivity()).setAddButtonVis(true);
                }
            }
        });*/
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), ContactViewerActivity.class);
        TextView firstView = (TextView) v.findViewById(R.id.list_firstname);
        TextView lastView = (TextView) v.findViewById(R.id.list_lastname);
        TextView titleView = (TextView) v.findViewById(R.id.list_title);
        TextView countryView = (TextView) v.findViewById(R.id.list_country);
        TextView genderView = (TextView) v.findViewById(R.id.list_gender);

        String first = firstView.getText().toString();
        String last = lastView.getText().toString();
        String title = titleView.getText().toString();
        String country = countryView.getText().toString();
        String gender = genderView.getText().toString();

        Log.d("gender", gender);
        intent.putExtra("first", first);
        intent.putExtra("last", last);
        intent.putExtra("title", title);
        intent.putExtra("country", country);
        intent.putExtra("gender", gender);
        intent.putExtra("id", v.getTag().toString());
        Log.i("v.getTag()", v.getTag().toString());
        Log.i("gender", genderView.getText().toString());

        startActivity(intent);

    }
}



