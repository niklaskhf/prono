package com.team16.sopra.sopra16team16;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by moo on 11/11/2016.
 */

public class ListAdapter extends ArrayAdapter<Item> {

    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.contact_item, null);
        }

        Item p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.list_firstname);
            TextView tt2 = (TextView) v.findViewById(R.id.list_lastname);
            TextView tt3 = (TextView) v.findViewById(R.id.list_title);
            TextView tt4 = (TextView) v.findViewById(R.id.list_country);

            if (tt1 != null) {
                tt1.setText(p.getFirstName());
            }

            if (tt2 != null) {
                tt2.setText(p.getLastName());
            }

            if (tt3 != null) {
                tt3.setText(p.getTitle());
            }

            if (tt4 != null) {
                tt4.setText(p.getCountry());
            }
        }
        return v;
    }
}
