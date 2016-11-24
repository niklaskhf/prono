package com.team16.sopra.sopra16team16.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.Model.Gender;
import com.team16.sopra.sopra16team16.R;

/**
 * Created by prime on 22.11.16.
 */

public class ContactViewerActivity extends AppCompatActivity {

    private static ContactManager contactManager;
    public static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setContentView(R.layout.contact_viewer);

        setContact();
        Button editButton = (Button) findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContactViewerActivity.this, NewContactActivity.class));
            }
        });

    }


    public void setContact() {
        Bundle bundle = getIntent().getExtras();
        String first, last, title, country;

        first = bundle.getString(contactManager.COLUMN_FIRSTNAME);
        last = bundle.getString(contactManager.COLUMN_LASTNAME);
        title = bundle.getString(contactManager.COLUMN_TITLE);
        country = bundle.getString(contactManager.COLUMN_COUNTRY);

        TextView firstNameText = (TextView) findViewById(R.id.real_first_name);
        TextView lastNameText = (TextView) findViewById(R.id.real_last_name);
        TextView titleText = (TextView) findViewById(R.id.real_title);
        TextView countryText= (TextView) findViewById(R.id.real_country);

        firstNameText.setText(first);
        lastNameText.setText(last);
        titleText.setText(title);
        countryText.setText(country);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
