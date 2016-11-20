package com.team16.sopra.sopra16team16.Controller;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Space;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.Model.Contact;
import com.team16.sopra.sopra16team16.R;

import java.util.List;

/**
 * Custom ArrayAdapter to display Contact objects
 */

public class ContactListAdapter extends ArrayAdapter<Contact> {

    public ContactListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ContactListAdapter(Context context, int resource, List<Contact> items) {
        super(context, resource, items);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            //vi = LayoutInflater.from(getContext());
            vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.contact_item, null);
        }

        final Contact p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.list_firstname);
            TextView tt2 = (TextView) v.findViewById(R.id.list_lastname);
            TextView tt3 = (TextView) v.findViewById(R.id.list_title);
            TextView tt4 = (TextView) v.findViewById(R.id.list_country);
            TextView tt5 = (TextView) v.findViewById(R.id.list_gender);

            if (p.getDeleted()) {
                v = new Space(getContext());
            }
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

            if (tt5 != null) {
                // TODO this is supposed to be an icon anyways?
                tt5.setText(p.getGender().toString());
            }


            // TESTING TESTING TESTING without unit test
            // playButton setup
            final ImageButton playButton = (ImageButton) v.findViewById(R.id.contact_play);

            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // play audio file associated to contact id
                }
            });



            // favoriteButton setup
            final ImageButton favButton = (ImageButton) v.findViewById(R.id.contact_fav);

            if (p.getFavorite()) {
                Drawable d = ContextCompat.getDrawable(getContext(), R.drawable.favorite_marked_icon);
                favButton.setImageDrawable(d);
            } else {
                Drawable d = ContextCompat.getDrawable(getContext(), R.drawable.favorite_unmarked_icon);
                favButton.setImageDrawable(d);
            }

            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ContactManager manager = new ContactManager(getContext());
                    manager.toggleFavorite(p);

                    if (p.getFavorite()) {
                        Drawable d = ContextCompat.getDrawable(getContext(), R.drawable.favorite_marked_icon);
                        favButton.setImageDrawable(d);
                    } else {
                        Drawable d = ContextCompat.getDrawable(getContext(), R.drawable.favorite_unmarked_icon);
                        favButton.setImageDrawable(d);
                    }
                    notifyDataSetChanged();
                }
            });
        }

        return v;
    }
}
