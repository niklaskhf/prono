package com.team16.sopra.sopra16team16.Controller;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.team16.sopra.sopra16team16.R;

/**
 * CursorAdapter presenting data from the database in listitems,
 * used for the ContactListFragment
 */
public class ContactCursorAdapter extends CursorAdapter {
    private ContactManager contactManager;
    private Player player;


    /**
     * Constructor
     * @param context  context
     * @param cursor cursor with contacts
     */
    public ContactCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        contactManager = ContactManager.getInstance(context);
        player = Player.getCurrentInstance();
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.contact_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        // get incrementing id
        final int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        final String first = cursor.getString(cursor.getColumnIndexOrThrow("first"));
        final String last = cursor.getString(cursor.getColumnIndexOrThrow("last"));
        final String country = cursor.getString(cursor.getColumnIndexOrThrow("country"));
        final String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));

        // set id for future reference
        view.setTag(id);

        // get elements of the view
        TextView tt1 = (TextView) view.findViewById(R.id.list_firstname);
        TextView tt2 = (TextView) view.findViewById(R.id.list_lastname);
        TextView tt3 = (TextView) view.findViewById(R.id.list_title);
        TextView tt4 = (TextView) view.findViewById(R.id.list_country);
        TextView tt5 = (TextView) view.findViewById(R.id.list_gender);

        final ImageButton playButton = (ImageButton) view.findViewById(R.id.contact_play);
        final ImageButton favButton = (ImageButton) view.findViewById(R.id.contact_fav);
        ImageView genderSign = (ImageView) view.findViewById(R.id.contact_gender);


        // assign values to TextViews
        tt1.setText(first);


        tt2.setText(last);


        tt3.setText(title);


        tt4.setText(country);

        tt5.setText(cursor.getString(cursor.getColumnIndexOrThrow("gender")));
        genderSign.setVisibility(View.VISIBLE);
        switch (cursor.getString(cursor.getColumnIndexOrThrow("gender"))) {
            case "MALE":
                genderSign.setImageResource(R.drawable.ic_male_gender);
                break;
            case "FEMALE":
                genderSign.setImageResource(R.drawable.ic_female_gender);
                break;
            default:
                genderSign.setVisibility(View.INVISIBLE);
                break;
        }



        Drawable p = ContextCompat.getDrawable(context, R.drawable.ic_play_circle_outline_black_48dp);
        playButton.setImageDrawable(p);

        // assign playButton action
        playButton.setOnClickListener(new View.OnClickListener() {
            //Player player = new Player();
            @Override
            public void onClick(View view) {
                // play audio file associated to contact id
                Log.e("RecordButton", "ID: " + id);
                if (player.isPlaying()) {
                    player.stopPlaying(playButton, id);
                } else {
                    player.startPlaying(id, playButton);
                }
            }
        });

        // get favorite value (0 or 1)
        final int favValue = cursor.getInt(cursor.getColumnIndexOrThrow("favorite"));

        // set correct item based on favValue
        if (favValue == 1) {
            Drawable d = ContextCompat.getDrawable(context, R.drawable.ic_star_black_24dp);
            favButton.setImageDrawable(d);
        } else {
            Drawable d = ContextCompat.getDrawable(context, R.drawable.ic_star_border_black_48dp);
            favButton.setImageDrawable(d);
        }


        // set favButton listener
        // toggle value/icon on click
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // toggle the icon
                if (favValue == 1) {
                    Log.i("updateFavorite", Integer.toString(id) + " true->false");
                    Drawable d = ContextCompat.getDrawable(context, R.drawable.ic_star_border_black_48dp);
                    favButton.setImageDrawable(d);
                } else {
                    Log.i("updateFavorite", Integer.toString(id) + " false->true");
                    Drawable d = ContextCompat.getDrawable(context, R.drawable.ic_star_black_24dp);
                    favButton.setImageDrawable(d);
                }

                // toggle field in database
                contactManager.toggleFavorite(id, favValue);
                cursor.requery();
                notifyDataSetChanged();

            }
        });
    }

}
