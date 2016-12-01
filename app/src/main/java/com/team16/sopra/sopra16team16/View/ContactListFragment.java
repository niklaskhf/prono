package com.team16.sopra.sopra16team16.View;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.R;



public class ContactListFragment extends ListFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.home_fragment, container, false);
        return rootview;
    }


    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);


        ListView lv = this.getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ContactViewerActivity.class);

                TextView firstView = (TextView) view.findViewById(R.id.list_firstname);
                TextView lastView = (TextView) view.findViewById(R.id.list_lastname);
                TextView titleView = (TextView) view.findViewById(R.id.list_title);
                TextView countryView = (TextView) view.findViewById(R.id.list_country);
                TextView genderView = (TextView) view.findViewById(R.id.list_gender);

                String first = firstView.getText().toString();
                String last = lastView.getText().toString();
                String title = titleView.getText().toString();
                String country = countryView.getText().toString();
                String gender = genderView.getText().toString();


                intent.putExtra("first", first);
                intent.putExtra("last", last);
                intent.putExtra("title", title);
                intent.putExtra("country", country);
                intent.putExtra("gender", gender);
                intent.putExtra("id", view.getTag().toString());
                Log.i("view.getTag()", view.getTag().toString());



                startActivity(intent);
            }
        });
    }
}



