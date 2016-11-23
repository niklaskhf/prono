package com.team16.sopra.sopra16team16.Controller;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.R;
import com.team16.sopra.sopra16team16.View.HomeActivity;

/**
 * CursorAdapter that populates search_item
 * Result is:
 *
 *     FIRST LAST
 *      title country gender
 */
public class SearchCursorAdapter extends CursorAdapter {
    private Context context;
    private ContactManager contactManager;

    public SearchCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.context = context;
        contactManager = ContactManager.getInstance(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.search_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        // get incrementing id
        final int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        // set id for future reference
        view.setTag(id);

        // set elements in contact_item.xml
        TextView tt1 = (TextView) view.findViewById(R.id.search_firstname);
        TextView tt2 = (TextView) view.findViewById(R.id.search_lastname);
        TextView tt3 = (TextView) view.findViewById(R.id.search_title);
        TextView tt4 = (TextView) view.findViewById(R.id.search_country);
        TextView tt5 = (TextView) view.findViewById(R.id.search_gender);

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
            // TODO this is supposed to be an icon anyways?
            tt5.setText(cursor.getString(cursor.getColumnIndexOrThrow("gender")));
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // OPEN VIEWER OF CONTACT
            }
        });

    }
}
