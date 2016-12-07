package com.team16.sopra.sopra16team16.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.Controller.Recorder;
import com.team16.sopra.sopra16team16.R;

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
    private RadioButton unknownSexRadioButton;

    private ImageButton confirmButton;
    private ImageButton cancelButton;
    private ImageButton recordButton;
    private ImageButton editButton;
    private ImageButton deleteButton;
    private ImageButton playButton;

    private ImageView genderSign;



    private String firstName = "";
    private String lastName = "";
    private String title = "";
    private String country = "";
    private String gender = "";
    private int id;

    private String cause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.contact_editor);

        //initialize();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            firstName = (String) bundle.get("first");
            lastName = (String)bundle.get("last");
            title = (String)bundle.get("title");
            country = (String)bundle.get("country");
            Log.i("country", country);
            gender = (String)bundle.get("gender");
            id = (Integer) bundle.get("id");
            cause = bundle.get("cause").toString();
        }
        Log.d("first", firstName);

        initialize();


        setEditLayout();
        // TODO ANDERE FELDER FÜLLEN

        // add Button to change layout to contact viewer
        final ImageButton confirmEditButton = (ImageButton) findViewById(R.id.confirm_button);
        confirmEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (femaleRadioButton.isChecked()) {
                    gender = "FEMALE";
                } else if (maleRadioButton.isChecked()) {
                    gender = "MALE";
                } else if (unknownSexRadioButton.isChecked()) {
                    gender = "UNKNOWN";
                }
                Intent intent = new Intent(NewContactActivity.this, ContactViewerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("first", firstNameEdit.getText().toString());
                bundle.putString("last", lastNameEdit.getText().toString());
                bundle.putString("title", titleEdit.getText().toString());
                bundle.putString("country", countryEdit.getText().toString());
                bundle.putInt("id", id);
                bundle.putString("gender", gender);
                intent.putExtras(bundle);

                if (cause.equals("CREATE")) {
                    setContact();
                } else {
                    updateContact();
                };

                startActivity(intent);
                finish();

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
                countryEdit.setText(countryText.getText().toString());
                titleEdit.setText(titleText.getText().toString());


            }
        });

        // add Button to cancel the current (adding of new contact)/(editing of existing button)
        final ImageButton cancelButton = (ImageButton) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
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
                    recorder.startRecording(id);
                    // TODO autostop recording after x seconds
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Intent i = new Intent(NewContactActivity.this, HomeActivity.class);
        finish();
    }

    public void initialize() {
        findViewByIdTextView();

        findViewByIdEditButton();

        findViewByIdImageButton();

        findViewByIdRadioButton();

        findViewByIdImageView();

        firstNameEdit.setText(firstName);
        lastNameEdit.setText(lastName);
        titleEdit.setText(title);
        countryEdit.setText(country);


        recorder = Recorder.getCurrentInstance(getApplicationContext());

        Log.d("gender", gender.toString());
        if (gender.equals("FEMALE")) {
            femaleRadioButton.setChecked(true);
        } else if (gender.equals("MALE")) {
            maleRadioButton.setChecked(true);
        } else if (gender.equals("UNKNOWN")) {
            unknownSexRadioButton.setChecked(true);
        }
    }


    public void setContact() {
        contactManager = ContactManager.getInstance(this.getApplicationContext());
        // TODO update gender
        // TODO create string attributes for all of this, this is ridiculous lmao
        Log.d("genderCreate", gender);
        contactManager.createContact(firstNameEdit.getText().toString(), lastNameEdit.getText().toString(), titleEdit.getText().toString(), countryEdit.getText().toString(), gender);
        Log.i("createContact", "created contact " + firstNameEdit.getText().toString());
    }

    public void updateContact() {
        contactManager = ContactManager.getInstance(this.getApplicationContext());
        contactManager.updateContact(id, firstNameEdit.getText().toString(), lastNameEdit.getText().toString(), titleEdit.getText().toString(), countryEdit.getText().toString(), gender);

    }

////////////////////////////////////////////////////////////////////////////////////////////////////
//From here on different declarations take place

    private void findViewByIdImageView() {
        genderSign = (ImageView) findViewById(R.id.gender_sign);
    }

    private void findViewByIdRadioButton() {
        femaleRadioButton = (RadioButton) findViewById(R.id.female_radioButton);
        maleRadioButton = (RadioButton) findViewById(R.id.male_radioButton);
        unknownSexRadioButton = (RadioButton) findViewById(R.id.unkown_radioButton);
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
        countryEdit = (EditText) findViewById(R.id.country_edit);
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

        genderSign.setVisibility(View.INVISIBLE);

        cancelButton.setVisibility(View.VISIBLE);
        recordButton.setVisibility(View.VISIBLE);
        confirmButton.setVisibility(View.VISIBLE);

        firstNameEdit.setVisibility(View.VISIBLE);
        lastNameEdit.setVisibility(View.VISIBLE);
        // TODO
        //countryEdit.setVisibility(View.VISIBLE);
        //countrySpinner.setVisibility(View.VISIBLE);
        titleEdit.setVisibility(View.VISIBLE);


        femaleRadioButton.setVisibility(View.VISIBLE);
        maleRadioButton.setVisibility(View.VISIBLE);
        unknownSexRadioButton = (RadioButton) findViewById(R.id.unkown_radioButton);
        unknownSexRadioButton.setVisibility(View.VISIBLE);
    }

}
