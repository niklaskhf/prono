package com.team16.sopra.sopra16team16.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.Controller.Recorder;
import com.team16.sopra.sopra16team16.Model.Gender;
import com.team16.sopra.sopra16team16.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by prime on 18.11.16.
 */

public class NewContactActivity extends AppCompatActivity {

    private static ContactManager contactManager;
    private static Recorder recorder;

    private TextView firstNameText;
    private TextView lastNameText;
    private TextView countryText;
    private TextView titleText;

    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private EditText countryEdit;
    private EditText titleEdit;

    private RadioButton femaleRadioButton;
    private RadioButton maleRadioButton;
    private RadioButton unkownSexRadioButton;

    private ImageButton confirmButton;
    private ImageButton cancelButton;
    private ImageButton recordButton;
    private ImageButton editButton;
    private ImageButton deleteButton;
    private ImageButton playButton;

    private ImageView femaleImage;
    private ImageView maleImage;
    private ImageView unknownSexImage;

    private Spinner countrySpinner;
    private ArrayAdapter<String> countryAdapter;

    private String firstName = "";
    private String lastName = "";
    private String title = "";
    private String country = "";
    private String gender = "";
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.contact_editor);

        initialize();

        findViewByIdTextView();

        findViewByIdEditButton();

        findViewByIdImageButton();

        findViewByIdRadioButton();

        findViewByIdImageView();

        setEditLayout();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            firstName = (String) bundle.get("first");
            lastName = (String)bundle.get("last");
            title = (String)bundle.get("title");
            country = (String)bundle.get("country");
            gender = (String)bundle.get("gender");
            id = (Integer) bundle.get("id");
        }
        Log.d("first", firstName);
        firstNameEdit.setText(firstName);
        lastNameEdit.setText(lastName);
        titleEdit.setText(title);
        // TODO ANDERE FELDER FÜLLEN

        // add Button to change layout to contact viewer
        final ImageButton confirmEditButton = (ImageButton) findViewById(R.id.confirm_button);
        confirmEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*setViewLayout();
                firstNameText.setText(firstNameEdit.getText().toString());
                lastNameText.setText(lastNameEdit.getText().toString());
                // TODO
                //countryText.setText(countryEdit.getText().toString());
                countryText.setText(countrySpinner.getSelectedItem().toString());
                titleText.setText(titleEdit.getText().toString());*/

                Intent intent = new Intent(NewContactActivity.this, ContactViewerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("first", firstNameEdit.getText().toString());
                bundle.putString("last", lastNameEdit.getText().toString());
                bundle.putString("title", titleEdit.getText().toString());
                bundle.putString("country", countrySpinner.getSelectedItem().toString());
                // TODO GENDER
                // TODO DO THIS PROPERLY
                startActivity(intent);

            }
        });

        @SuppressLint("WrongViewCast")
        final ImageButton editButton = (ImageButton) findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEditLayout();
                firstNameEdit.setText(firstNameText.getText().toString());
                lastNameEdit.setText(lastNameText.getText().toString());
                //countryEdit.setText(countryText.getText().toString());
                titleEdit.setText(titleText.getText().toString());

                // set default spinner position to current value
                String curCountry = countryText.getText().toString();

                // set default if empty
                if (curCountry.equals("")) {
                    // TODO set to system language maybe
                    curCountry = "Germany";
                }
                int position = countryAdapter.getPosition(curCountry);
                countrySpinner.setSelection(position);
            }
        });

        // add Button to cancel the current (adding of new contact)/(editing of existing button)
        final ImageButton cancelButton = (ImageButton) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewContactActivity.this, HomeActivity.class));
                finish();
            }
        });

        // add Button to record a name
        final ImageButton recordButton = (ImageButton) findViewById(R.id.record_button);
        recordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (recorder.isPressed()) {
                    recordButton.setBackgroundResource(R.drawable.mic_icon);
                    recorder.stopRecording();
                } else {
                    //start recording
                    recordButton.setBackgroundResource(R.drawable.accept_icon);
                    //TODO ID (hier 0) aus der Datenbank entnehmen
                    recorder.startRecording(id);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(NewContactActivity.this, HomeActivity.class);
        //setContact(i);
        setContact();
        startActivity(i);
        finish();
    }

    public void initialize() {
        recorder = Recorder.getCurrentInstance(getApplicationContext());

        countrySpinner = (Spinner) findViewById(R.id.country_spinner);
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }

        Collections.sort(countries);
        for (String country : countries) {
            Log.i("testingCountries", country);
        }

        countryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);

        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);
    }

    // save contact info in Intent to pass it to HomeActivity which creates the new contact
    public Intent setContact(Intent i) {
        EditText firstName = (EditText) findViewById(R.id.first_edit);
        EditText lastName = (EditText) findViewById(R.id.last_edit);
        //EditText country = (EditText) findViewById(R.id.country_edit);
        EditText title = (EditText) findViewById(R.id.title_edit);

        i.putExtra(contactManager.COLUMN_FIRSTNAME, firstName.getText().toString());
        i.putExtra(contactManager.COLUMN_LASTNAME, lastName.getText().toString());
        //i.putExtra(contactManager.COLUMN_COUNTRY, country.getText().toString());
        i.putExtra(contactManager.COLUMN_COUNTRY, countrySpinner.getSelectedItem().toString());
        i.putExtra(contactManager.COLUMN_TITLE, title.getText().toString());

        return i;
    }

    public void setContact() {
        ContactManager contactManager = ContactManager.getInstance(this);
        // TODO update gender
        // TODO create string attributes for all of this, this is ridiculous lmao
        contactManager.createContact(firstNameText.getText().toString(), lastNameText.getText().toString(), titleText.getText().toString(), countryText.getText().toString(), Gender.MALE);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
//From here on different declarations take place

    private void findViewByIdImageView() {
        femaleImage = (ImageView) findViewById(R.id.female_sign);
        maleImage = (ImageView) findViewById(R.id.male_sign);
        unknownSexImage = (ImageView) findViewById(R.id.unkown_sex_sign);
    }

    private void findViewByIdRadioButton() {
        femaleRadioButton = (RadioButton) findViewById(R.id.female_radioButton);
        maleRadioButton = (RadioButton) findViewById(R.id.male_radioButton);
        unkownSexRadioButton = (RadioButton) findViewById(R.id.unkown_radioButton);
    }

    @SuppressLint("WrongViewCast")
    private void findViewByIdImageButton() {
        confirmButton = (ImageButton) findViewById(R.id.confirm_button);
        cancelButton = (ImageButton) findViewById(R.id.cancel_button);
        recordButton = (ImageButton) findViewById(R.id.record_button);
        editButton = (ImageButton) findViewById(R.id.edit_button);
        deleteButton = (ImageButton) findViewById(R.id.delete_button);
        confirmButton = (ImageButton) findViewById(R.id.confirm_button);
        playButton = (ImageButton) findViewById(R.id.play_button);
    }

    private void findViewByIdEditButton() {
        firstNameEdit = (EditText) findViewById(R.id.first_edit);
        lastNameEdit = (EditText) findViewById(R.id.last_edit);
        //countryEdit = (EditText) findViewById(R.id.country_edit);
        titleEdit = (EditText) findViewById(R.id.title_edit);
    }

    private void findViewByIdTextView() {
        firstNameText = (TextView) findViewById(R.id.real_first_name);
        lastNameText = (TextView) findViewById(R.id.real_last_name);
        countryText = (TextView) findViewById(R.id.real_country);
        titleText = (TextView) findViewById(R.id.real_title);
    }


    private void setEditLayout() {

        firstNameText.setVisibility(View.INVISIBLE);
        lastNameText.setVisibility(View.INVISIBLE);
        countryText.setVisibility(View.INVISIBLE);
        titleText.setVisibility(View.INVISIBLE);

        deleteButton.setVisibility(View.INVISIBLE);
        playButton.setVisibility(View.INVISIBLE);
        editButton.setVisibility(View.INVISIBLE);

        femaleImage.setVisibility(View.INVISIBLE);
        maleImage.setVisibility(View.INVISIBLE);
        unknownSexImage.setVisibility(View.INVISIBLE);

        cancelButton.setVisibility(View.VISIBLE);
        recordButton.setVisibility(View.VISIBLE);
        confirmButton.setVisibility(View.VISIBLE);

        firstNameEdit.setVisibility(View.VISIBLE);
        lastNameEdit.setVisibility(View.VISIBLE);
        // TODO
        //countryEdit.setVisibility(View.VISIBLE);
        countrySpinner.setVisibility(View.VISIBLE);
        titleEdit.setVisibility(View.VISIBLE);


        femaleRadioButton.setVisibility(View.VISIBLE);
        maleRadioButton.setVisibility(View.VISIBLE);
        unkownSexRadioButton.setVisibility(View.VISIBLE);
    }

    private void setViewLayout() {

        firstNameEdit.setVisibility(View.INVISIBLE);
        lastNameEdit.setVisibility(View.INVISIBLE);
        // TODO
        //countryEdit.setVisibility(View.INVISIBLE);
        countrySpinner.setVisibility(View.INVISIBLE);
        titleEdit.setVisibility(View.INVISIBLE);

        femaleRadioButton.setVisibility(View.INVISIBLE);
        maleRadioButton.setVisibility(View.INVISIBLE);
        unkownSexRadioButton.setVisibility(View.INVISIBLE);

        cancelButton.setVisibility(View.INVISIBLE);
        recordButton.setVisibility(View.INVISIBLE);
        confirmButton.setVisibility(View.INVISIBLE);

        firstNameText.setVisibility(View.VISIBLE);
        lastNameText.setVisibility(View.VISIBLE);
        countryText.setVisibility(View.VISIBLE);
        titleText.setVisibility(View.VISIBLE);

        deleteButton.setVisibility(View.VISIBLE);
        playButton.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.VISIBLE);

        if (femaleRadioButton.isChecked()) {
            femaleImage.setVisibility(View.VISIBLE);
        } else if (maleRadioButton.isChecked()) {
            maleImage.setVisibility(View.VISIBLE);
        } else if (unkownSexRadioButton.isChecked()) {
            unknownSexImage.setVisibility(View.VISIBLE);
        }
    }

}
