package com.team16.sopra.sopra16team16.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.team16.sopra.sopra16team16.R;
import com.team16.sopra.sopra16team16.View.ContactViewerActivity;
import com.team16.sopra.sopra16team16.View.HomeActivity;
import com.team16.sopra.sopra16team16.View.ContactViewerActivity;

public class ContactCursorAdapter extends CursorAdapter {
    private Context context;
    private ContactManager contactManager;

    public ContactCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
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

        // set elements in contact_item.xml
        TextView tt1 = (TextView) view.findViewById(R.id.list_firstname);
        TextView tt2 = (TextView) view.findViewById(R.id.list_lastname);
        TextView tt3 = (TextView) view.findViewById(R.id.list_title);
        TextView tt4 = (TextView) view.findViewById(R.id.list_country);
        TextView tt5 = (TextView) view.findViewById(R.id.list_gender);
        ImageView genderSign = (ImageView) view.findViewById(R.id.contact_gender);

        ImageButton playButton = (ImageButton) view.findViewById(R.id.contact_play);
        final ImageButton favButton = (ImageButton) view.findViewById(R.id.contact_fav);
        Button deleteButton = (Button) view.findViewById(R.id.contact_delete);

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

        if (genderSign != null) {
            // TODO this is supposed to be an icon anyways?
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
            }
        }


        // TODO
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // play audio file associated to contact id
            }
        });


        // get favorite value (0 or 1)
        final int favValue = cursor.getInt(cursor.getColumnIndexOrThrow("favorite"));
        // set appropiate item
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

                if (favValue == 1) {
                    Log.i("updateFavorite", "true->false");
                    Drawable d = ContextCompat.getDrawable(context, R.drawable.favorite_unmarked_icon);
                    favButton.setImageDrawable(d);
                } else {
                    Log.i("updateFavorite", "false->true");
                    Drawable d = ContextCompat.getDrawable(context, R.drawable.favorite_marked_icon);
                    Log.i("favorite icon", d.toString());
                    favButton.setImageDrawable(d);
                }

                contactManager.toggleFavorite(id, favValue);
                Log.i("id", Integer.toString(id));
                notifyDataSetChanged();

            }
        });

        // delete item
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewC) {
                contactManager.deleteContact(id);
            }
        });


    }

}
