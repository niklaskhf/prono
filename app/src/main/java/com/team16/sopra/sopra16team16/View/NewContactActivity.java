package com.team16.sopra.sopra16team16.View;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.Controller.FileUtils;
import com.team16.sopra.sopra16team16.Controller.Player;
import com.team16.sopra.sopra16team16.Controller.Recorder;
import com.team16.sopra.sopra16team16.R;


public class NewContactActivity extends AppCompatActivity {

    private static ContactManager contactManager;
    private Recorder recorder;

    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private EditText countryEdit;
    private EditText titleEdit;

    private RadioButton femaleRadioButton;
    private RadioButton maleRadioButton;
    private RadioButton unknownSexRadioButton;

    private FloatingActionButton confirmEditButton;
    private FloatingActionButton cancelButton;
    private FloatingActionButton recordButton;

    private ImageView genderSign;


    private String firstName = "";
    private String lastName = "";
    private String title = "";
    private String country = "";
    private String gender = "";
    private int id;

    private String undoFirst = "";
    private String undoLast = "";
    private String undoTitle = "";
    private String undoCountry = "";
    private String undoGender = "";


    private String cause;

    private Player player = new Player();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.contact_editor);

        //initialize();

        Bundle bundle = getIntent().getExtras();

        firstName = (String) bundle.get("first");
        undoFirst = bundle.getString("first");
        lastName = (String) bundle.get("last");
        undoLast = bundle.getString("last");
        title = (String) bundle.get("title");
        undoTitle = bundle.getString("title");
        country = (String) bundle.get("country");
        undoCountry = bundle.getString("country");
        gender = (String) bundle.get("gender");
        undoGender = bundle.getString("gender");
        id = (Integer) bundle.get("id");
        cause = bundle.get("cause").toString();


        Log.d("first", firstName);

        initialize();

        // add Button to change layout to contact viewer
        confirmEditButton = (FloatingActionButton) findViewById(R.id.confirm_button);
        confirmEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get the gender data
                if (femaleRadioButton.isChecked()) {
                    gender = "FEMALE";
                } else if (maleRadioButton.isChecked()) {
                    gender = "MALE";
                } else if (unknownSexRadioButton.isChecked()) {
                    gender = "UNKNOWN";
                }

                // check if necesarry requirements are met
                // required: LAST and RECORDING
                if (lastNameEdit.getText().toString().equals("") || !FileUtils.exists(id)) {
                    confirmRequirements();
                } else {
                    // pass data back to ContactViewerActivity
                    Intent intent = new Intent(NewContactActivity.this, ContactViewerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("first", firstNameEdit.getText().toString());
                    bundle.putString("last", lastNameEdit.getText().toString());
                    bundle.putString("title", titleEdit.getText().toString());
                    bundle.putString("country", countryEdit.getText().toString());
                    bundle.putInt("id", id);
                    bundle.putString("gender", gender);


                    if (cause.equals("CREATE")) {
                        intent.putExtras(bundle);
                        // create the new contact
                        setContact();
                        FileUtils.confirmAudio(id);
                        startActivity(intent);
                        finish();
                    } else {
                        // add previous data to allow undo
                        bundle.putString("action", "undo");
                        bundle.putString("undoFirst", undoFirst);
                        bundle.putString("undoLast", undoLast);
                        bundle.putString("undoTitle", undoTitle);
                        bundle.putString("undoCountry", undoCountry);
                        bundle.putString("undoGender", undoGender);
                        setResult(RESULT_OK, new Intent().putExtras(bundle));
                        FileUtils.confirmAudio(id);
                        // update contact in database
                        updateContact();
                        finish();
                    }
                }

            }
        });

        // add Button to cancel the current (adding of new contact)/(editing of existing button)
        cancelButton = (FloatingActionButton) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // add Button to record a name
        recordButton = (FloatingActionButton) findViewById(R.id.record_button);
        recordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (recorder.isPressed()) {
                    recorder.stopRecording();
                } else {
                    recorder.startRecording(id);
                }
            }
        });

        recorder = new Recorder(this, recordButton);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FileUtils.deleteFile(FileUtils.PATH + id + "temp.3gp");
        finish();
    }

    /**
     * Initializes the views etc.
     */
    public void initialize() {

        findViewByIdEditButton();


        findViewByIdRadioButton();

        findViewByIdImageView();

        firstNameEdit.setText(firstName);
        lastNameEdit.setText(lastName);
        titleEdit.setText(title);
        countryEdit.setText(country);

        Log.d("gender", gender.toString());
        if (gender.equals("FEMALE")) {
            femaleRadioButton.setChecked(true);
        } else if (gender.equals("MALE")) {
            maleRadioButton.setChecked(true);
        } else if (gender.equals("UNKNOWN")) {
            unknownSexRadioButton.setChecked(true);
        }
    }


    /**
     * Saves a contact
     */
    public void setContact() {
        contactManager = ContactManager.getInstance(this.getApplicationContext());
        // TODO update gender
        // TODO create string attributes for all of this, this is ridiculous lmao
        Log.d("genderCreate", gender);
        contactManager.createContact(firstNameEdit.getText().toString(), lastNameEdit.getText().toString(), titleEdit.getText().toString(), countryEdit.getText().toString(), gender);
        Log.i("createContact", "created contact " + firstNameEdit.getText().toString());
    }

    /**
     * Updates an existing contact
     */
    public void updateContact() {
        contactManager = ContactManager.getInstance(this.getApplicationContext());
        contactManager.updateContact(id, firstNameEdit.getText().toString(), lastNameEdit.getText().toString(), titleEdit.getText().toString(), countryEdit.getText().toString(), gender);

    }


    /**
     * TODO DELETE
     */
    private void findViewByIdImageView() {
        genderSign = (ImageView) findViewById(R.id.gender_sign);
    }

    /**
     * Sets the radio buttons
     */
    private void findViewByIdRadioButton() {
        femaleRadioButton = (RadioButton) findViewById(R.id.female_radioButton);
        maleRadioButton = (RadioButton) findViewById(R.id.male_radioButton);
        unknownSexRadioButton = (RadioButton) findViewById(R.id.unkown_radioButton);
    }

    /**
     * Sets the EditTexts
     */
    private void findViewByIdEditButton() {
        firstNameEdit = (EditText) findViewById(R.id.first_edit);
        lastNameEdit = (EditText) findViewById(R.id.last_edit);
        countryEdit = (EditText) findViewById(R.id.country_edit);
        titleEdit = (EditText) findViewById(R.id.title_edit);
    }


    /**
     * Shows an alert asking the user to input all the required fields.
     * Required fields are: Last name, pronounciation
     */
    public void confirmRequirements() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setMessage("Last name and recording can't both be empty! Please fill them out/record the pronunciation.");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do nothing
            }
        });
        alertDialog.show();
    }

}
