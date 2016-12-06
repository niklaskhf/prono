package com.team16.sopra.sopra16team16.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
//import android.support.v4.widget.CursorAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.R;
import com.team16.sopra.sopra16team16.View.HomeActivity;

public class ContactCursorAdapter extends CursorAdapter {
    private Context context;
    private ContactManager contactManager;
    private Player player;


    public ContactCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        player = Player.getCurrentInstance(context);
        this.context = context;
        contactManager = ContactManager.getInstance(context);
    }




    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.contact_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        // get incrementing id
        final int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

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
        Button deleteButton = (Button) view.findViewById(R.id.contact_delete);
        ImageView genderSign = (ImageView) view.findViewById(R.id.contact_gender);


        // assign values to textviews
        if (Boolean.getBoolean(cursor.getString(cursor.getColumnIndexOrThrow("deleted")))) {
            view = new Space(context);
        }
        if (tt1 != null) {
            tt1.setText(cursor.getString(cursor.getColumnIndexOrThrow("first")));
        }

        if (tt2 != null) {
            tt2.setText(cursor.getString(cursor.getColumnIndexOrThrow("last")));
        }

        if (tt3 != null) {
            tt3.setText(cursor.getString(cursor.getColumnIndexOrThrow("title")));
        }

        if (tt4 != null) {
            tt4.setText(cursor.getString(cursor.getColumnIndexOrThrow("country")));
        }

        if (tt5 != null) {
            tt5.setText(cursor.getString(cursor.getColumnIndexOrThrow("gender")));
            switch (cursor.getString(cursor.getColumnIndexOrThrow("gender"))) {
                case "MALE":
                    genderSign.setImageResource(R.drawable.running_man);
                    break;
                case "FEMALE":
                    genderSign.setImageResource(R.drawable.pregnant_woman);
                    break;
                case "UNKNOWN":
                    genderSign.setImageResource(android.R.drawable.sym_def_app_icon);
                    break;
            }
        }

        // assign playButton action
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // play audio file associated to contact id
                Log.e("RecordButton", "ID: " + id);
                if (player.isPlaying()) {
                    player.stopPlaying(playButton);
                } else {
                    player.startPlaying(id, playButton);
                }
            }
        });


        // get favorite value (0 or 1)
        final int favValue = cursor.getInt(cursor.getColumnIndexOrThrow("favorite"));

        // set correct item based on favValue
        if (favValue == 1) {
            Drawable d = ContextCompat.getDrawable(context, R.drawable.favorite_marked_icon);
            favButton.setImageDrawable(d);
        } else {
            Drawable d = ContextCompat.getDrawable(context, R.drawable.favorite_unmarked_icon);
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
                    Drawable d = ContextCompat.getDrawable(context, R.drawable.favorite_unmarked_icon);
                    favButton.setImageDrawable(d);
                } else {
                    Log.i("updateFavorite", Integer.toString(id) + " false->true");
                    Drawable d = ContextCompat.getDrawable(context, R.drawable.favorite_marked_icon);
                    favButton.setImageDrawable(d);
                }

                // toggle field in database
                contactManager.toggleFavorite(id, favValue);
                notifyDataSetChanged();

            }
        });

        // delete item
        // TODO remove this and the entire button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewC) {
                contactManager.deleteContact(id);
            }
        });
    }
}
