package com.team16.sopra.sopra16team16.View;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.R;



public class ContactListFragment extends ListFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }


    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
       // registerForContextMenu(getListView());
    }

}