package com.team16.sopra.sopra16team16.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.Controller.FileUtils;
import com.team16.sopra.sopra16team16.Controller.Recorder;
import com.team16.sopra.sopra16team16.R;

import java.io.File;


public class NewContactActivity extends AppCompatActivity {

    private ContactManager contactManager;
    private Recorder recorder;

    private TextView lastNameTv;
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

    private ColorStateList fabBackground;

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

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.contact_editor);
        setTitle("Editor");
        context = this;

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

        recorder = Recorder.getInstance();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (recorder.isPressed()) {
            recorder.stopRecording(recordButton, this);
        }
        FileUtils.deleteTempFiles();
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (recorder.isPressed()) {
            recorder.stopRecording(recordButton, this);
        }
    }

    /**
     * Initializes the views etc.
     */
    public void initialize() {

        findViewByTextView();
        findViewByIdEditButton();


        findViewByIdRadioButton();

        setText();

        setButtons();
    }

    /**
     * Sets the editText data
     */
    public void setText() {
        firstNameEdit.setText(firstName);
        lastNameEdit.setText(lastName);
        titleEdit.setText(title);
        countryEdit.setText(country);
        if (gender.equals("FEMALE")) {
            femaleRadioButton.setChecked(true);
        } else if (gender.equals("MALE")) {
            maleRadioButton.setChecked(true);
        } else if (gender.equals("UNKNOWN")) {
            unknownSexRadioButton.setChecked(true);
        }
    }

    /**
     * Sets the buttons
     */
    public void setButtons() {
        // add Button to change layout to contact viewer
        confirmEditButton = (FloatingActionButton) findViewById(R.id.confirm_button);
        confirmEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (recorder.isPressed()) {
                    recorder.stopRecording(recordButton, context);
                }

                // get the gender data
                if (femaleRadioButton.isChecked()) {
                    gender = "FEMALE";
                } else if (maleRadioButton.isChecked()) {
                    gender = "MALE";
                } else {
                    gender = "UNKNOWN";
                }

                // check if necesarry requirements are met
                // required: LAST and RECORDING

                if(!confirmRequirements()) {
                    confirmRequirementsDialog();
                } else {
                    // pass data back to ContactViewerActivity
                    Intent intent = new Intent(NewContactActivity.this, ContactViewerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("first", getFirstString());
                    bundle.putString("last", getLastString());
                    bundle.putString("title", getTitleString());
                    bundle.putString("country", getCountryString());
                    bundle.putInt("id", id);
                    bundle.putString("gender", gender);


                    if (cause.equals("CREATE")) {
                        intent.putExtras(bundle);
                        // create the new contact
                        setContact();
                        // go to ContactViewerActivity
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
                    recorder.stopRecording(recordButton, context);
                } else {
                    recorder.startRecording(id, recordButton, context);
                }
            }
        });
        fabBackground = recordButton.getBackgroundTintList();

    }

    /**
     * Saves a contact
     */
    public void setContact() {
        contactManager = ContactManager.getInstance(this.getApplicationContext());
        Log.d("genderCreate", gender);

        FileUtils.confirmAudio(id);
        FileUtils.deleteTempFiles();

        contactManager.createContact(
                getFirstString(),
                getLastString(),
                getTitleString(),
                getCountryString(),
                gender);
        Log.i("createContact", "created contact " + firstNameEdit.getText().toString());
    }

    /**
     * Updates an existing contact
     */
    public void updateContact() {
        contactManager = ContactManager.getInstance(this.getApplicationContext());
        FileUtils.confirmAudio(id);
        FileUtils.deleteTempFiles();

        contactManager.updateContact(id,
                getFirstString(),
                getLastString(),
                getTitleString(),
                getCountryString(),
                gender);

    }

    /**
     * Sets the TextViews
     */
    private void findViewByTextView() {
        lastNameTv = (TextView) findViewById(R.id.last_text);
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

        final ColorStateList originalTextColor = lastNameTv.getTextColors();

        // create listener to check for data
        // if EditText is not empty, reset color to default.
        TextWatcher tw = new TextWatcher() {
            public void afterTextChanged(Editable s){
                if (!(getLastString().equals(""))) {
                    lastNameEdit.getBackground().clearColorFilter();
                    lastNameTv.setTextColor(originalTextColor);
                }
            }
            public void  beforeTextChanged(CharSequence s, int start, int count, int after){
                // you can check for enter key here
            }
            public void  onTextChanged (CharSequence s, int start, int before,int count) {
            }
        };
        // add the listener
        lastNameEdit.addTextChangedListener(tw);
    }


    /**
     * Checks if the necessary requirements are met.
     * Those are:
     * - last name cant be empty
     * - there has to be a recording associated to the data
     *
     * @return boolean value
     *          true - requiremente met
     *          false - requirements not met
     */
    public boolean confirmRequirements() {
        int i = 0;
        // check if lastName is empty
        if (getLastString().equals("")) {
            lastNameTv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            lastNameEdit.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            i++;
        }
        // check if there is a recording for the lastName, or if there is a custom recording
        if (!customRecordExists()) {
            recordButton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            i++;
        }

        // return any found issues
        return i == 0;
    }


    /**
     * Checks if any temporary of permanent audio files associated to the id exist
     * @return true - a file exists
     *         false - no file exists
     */
    public boolean customRecordExists() {

        return new File(FileUtils.PATH + id + ".3gp").exists() ||
                new File(FileUtils.PATH + id + "_temp.3gp").exists();
    }


    /**
     * A dialog informing the user about missing requirements
     */
    public void confirmRequirementsDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setMessage(getText(R.string.missingKeyEntries));
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do nothing
            }
        });
        alertDialog.show();
    }



    /**
     * Returns the content of firstNameEdit without spaces in the beginning or end
     * @return firstNameEdit contents
     */
    public String getFirstString() {
        String value = firstNameEdit.getText().toString();
        return trimSpaces(value);
    }

    /**
     * Returns the content of lastNameEdit without spaces in the beginning or end
     * @return lastNameEdit contents
     */
    public String getLastString() {
        String value = lastNameEdit.getText().toString();

        return trimSpaces(value);
    }

    /**
     * Returns the content of titleEdit without spaces in the beginning or end
     * @return titleEdit contents
     */
    public String getTitleString() {
        String value = titleEdit.getText().toString();

        return trimSpaces(value);
    }

    /**
     * Returns the content of countryEdit without spaces in the beginning or end
     * @return countryEdit contents
     */
    public String getCountryString() {
        String value = countryEdit.getText().toString();

        return trimSpaces(value);
    }

    /**
     * Trims spaces at the beginning or the end of a String,
     * until none are left
     * @param value - String - to be trimmed
     * @return returns the trimmed String
     */
    public String trimSpaces(String value) {
        if ( value.length() == 0) {
            return "";
        }
        while (value.length() != 0 &&value.charAt(0) == ' ') {
            value = value.substring(1);
        }


        while (value.length() != 0 && value.charAt(value.length() - 1) == ' ') {
            value = value.substring(0, value.length() - 1);
        }

        return value;
    }


    /**
     * resets the recordButton color
     */
    public void customRecordColor() {
        recordButton.setBackgroundTintList(fabBackground);
    }
}
