package com.team16.sopra.sopra16team16.View;

import android.app.AlertDialog;
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
import com.team16.sopra.sopra16team16.Controller.RecordingMode;
import com.team16.sopra.sopra16team16.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class NewContactActivity extends AppCompatActivity {

    private ContactManager contactManager;
    private Recorder recorder;

    private TextView lastNameTv;
    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private EditText countryEdit;
    private EditText titleEdit;

    private ImageButton firstRecordButton;
    private ImageButton lastRecordButton;

    private RadioButton femaleRadioButton;
    private RadioButton maleRadioButton;
    private RadioButton unknownSexRadioButton;

    private FloatingActionButton confirmEditButton;
    private FloatingActionButton cancelButton;
    private FloatingActionButton recordButton;
    private ColorStateList fabBackground;

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
        setTitle("Editor");

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

        recorder = new Recorder(this, recordButton, firstRecordButton, lastRecordButton);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FileUtils.deleteTempFiles();
        finish();
    }

    /**
     * Initializes the views etc.
     */
    public void initialize() {

        findViewByTextView();
        findViewByIdEditButton();


        findViewByIdRadioButton();

        findViewByIdImageView();

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

                if (requireGenericCountryDialog()) {
                    genericCountryChangedDialog();
                } else if(!confirmRequirements()) {
                    confirmRequirementsDialog();
                } else {
                    // pass data back to ContactViewerActivity
                    Intent intent = new Intent(NewContactActivity.this, ContactViewerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("first", capitalize(getFirstString()));
                    bundle.putString("last", capitalize(getLastString()));
                    bundle.putString("title", capitalize(getTitleString()));
                    bundle.putString("country", capitalize(getCountryString()));
                    bundle.putInt("id", id);
                    bundle.putString("gender", gender);


                    if (cause.equals("CREATE")) {
                        intent.putExtras(bundle);
                        // create the new contact
                        setContact();
                        FileUtils.confirmAudio(id);
                        FileUtils.confirmAudio(getFirstString().toLowerCase() + getCountryString().toLowerCase());
                        FileUtils.confirmAudio(getLastString().toLowerCase() + getCountryString().toLowerCase());
                        FileUtils.deleteTempFiles();
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

                        // ALERT DIALOG
                        FileUtils.confirmAudio(id);
                        FileUtils.confirmAudio(getFirstString().toLowerCase() + getCountryString().toLowerCase());
                        FileUtils.confirmAudio(getLastString().toLowerCase() + getCountryString().toLowerCase());
                        FileUtils.deleteTempFiles();
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
                    enableButtons();
                } else {
                    disableButtons(recordButton);
                    recorder.startRecording(id, RecordingMode.RECORDING_CUSTOM);
                }
            }
        });

        fabBackground = recordButton.getBackgroundTintList();


        // set the first name record button
        firstRecordButton = (ImageButton) findViewById(R.id.recordFirstButton);

        firstRecordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (getFirstString().equals("")) {
                    showEditAlertDialog();
                } else {
                    if (recorder.isPressed()) {
                        recorder.stopRecording();
                        enableButtons();
                    } else {
                        recorder.triggerRecordingGeneric(getFirstString(), getCountryString(), RecordingMode.RECORDING_FIRST);
                        disableButtons(firstRecordButton);
                    }
                }
            }
        });



        // set the lastName record button
        lastRecordButton = (ImageButton) findViewById(R.id.recordLastButton);

        lastRecordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (getLastString().equals("")) {
                    showEditAlertDialog();
                } else {
                    if (recorder.isPressed()) {
                        recorder.stopRecording();
                        enableButtons();
                    } else {
                        recorder.triggerRecordingGeneric(getLastString(), getCountryString(), RecordingMode.RECORDING_LAST);
                        disableButtons(lastRecordButton);
                    }
                }
            }
        });

    }

    /**
     * Saves a contact
     */
    public void setContact() {
        contactManager = ContactManager.getInstance(this.getApplicationContext());
        Log.d("genderCreate", gender);
        contactManager.createContact(
                capitalize(getFirstString()),
                capitalize(getLastString()),
                capitalize(getTitleString()),
                capitalize(getCountryString()),
                gender);
        Log.i("createContact", "created contact " + firstNameEdit.getText().toString());
    }

    /**
     * Updates an existing contact
     */
    public void updateContact() {
        contactManager = ContactManager.getInstance(this.getApplicationContext());
        contactManager.updateContact(id,
                capitalize(getFirstString()),
                capitalize(getLastString()),
                capitalize(getTitleString()),
                capitalize(getCountryString()),
                gender);

    }

    /**
     * Sets the TextViews
     */
    private void findViewByTextView() {
        lastNameTv = (TextView) findViewById(R.id.last_text);
    }
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
        // TODO bring arraylist back here
        int i = 0;
        // check if lastName is empty
        if (getLastString().equals("")) {
            lastNameTv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            lastNameEdit.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            i++;
        }
        // check if there is a recording for the lastName, or if there is a custom recording
        if (!lastRecordExists() && !customRecordExists()) {
            lastRecordButton.setImageTintList(ColorStateList.valueOf(Color.RED));
            recordButton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            i++;
        }
        // check if there is a first name
        if (!getFirstString().equals("")) {
            // if there is a first name:
            // check for a recording of the firstName, or alternatively a custom recording
            if (!firstRecordExists() && !customRecordExists()) {
                firstRecordButton.setImageTintList(ColorStateList.valueOf(Color.RED));
                recordButton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                i++;
            }
        }

        // return any found issues
        return i == 0;
    }


    /**
     * Check if the country has changed
     * @return
     */
    public boolean requireGenericCountryDialog() {
        if (!getCountryString().equals(undoCountry)) {
            // check if new requirements are already met with existing recordings
            if (!confirmRequirements()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if any temporary of permanent audio files associated to the last name exist
     * @return true - a file exists
     *         false - no file exists
     */
    public boolean lastRecordExists() {
        String lastRecordString = FileUtils.PATH + getLastString().toLowerCase() + getCountryString().toLowerCase();
        if (new File(lastRecordString + ".3gp").exists() ||
                new File(lastRecordString + "temp.3gp").exists()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if any temporary of permanent audio files associated to the first name exist
     * @return true - a file exists
     *         false - no file exists
     */
    public boolean firstRecordExists() {
        String firstRecordString = FileUtils.PATH + getFirstString().toLowerCase() + getCountryString().toLowerCase();
        if (new File(firstRecordString + ".3gp").exists() ||
                new File(firstRecordString + "temp.3gp").exists()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if any temporary of permanent audio files associated to the id exist
     * @return true - a file exists
     *         false - no file exists
     */
    public boolean customRecordExists() {

        if (new File(FileUtils.PATH + id + ".3gp").exists() ||
                new File(FileUtils.PATH + id + "temp.3gp").exists()) {
            return true;
        }
        return false;
    }


    /**
     * A dialog informing the user about missing requirements
     */
    public void confirmRequirementsDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setMessage("The last name and either generic recordings of the first/last name, " +
                "or a custom recording of the pronunciation are required.\n\n" +
                "Please enter the name/record a pronunciation.");
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
        if (value.length() == 0) {
            return "";
        }
        while (value.length() != 0 && value.charAt(0) == ' ') {
            value = value.substring(1);
        }

        while (value.length() != 0 && value.charAt(value.length() - 1) == ' ') {
            value = value.substring(0, value.length() - 1);
        }

        for (int i = 0; i < value.length(); i++) {
            if (!Character.isAlphabetic(value.charAt(i))) {
                value = value.replace(String.valueOf(value.charAt(i)), "");
            }
        }

        return value;
    }

    /**
     * Returns the content of lastNameEdit without spaces in the beginning or end
     * @return lastNameEdit contents
     */
    public String getLastString() {
        String value = lastNameEdit.getText().toString();

        if (value.length() == 0) {
            return "";
        }
        while (value.length() != 0 && value.charAt(0) == ' ') {
            value = value.substring(1);
        }


        while (value.length() != 0 && value.charAt(value.length()-1) == ' ') {
            value = value.substring(0, value.length() - 1);
        }

        for (int i = 0; i < value.length(); i++) {
            if (!Character.isAlphabetic(value.charAt(i))) {
                value = value.replace(String.valueOf(value.charAt(i)), "");
            }
        }

        return value;
    }

    /**
     * Returns the content of titleEdit without spaces in the beginning or end
     * @return titleEdit contents
     */
    public String getTitleString() {
        String value = titleEdit.getText().toString();

        if (value.length() == 0) {
            return "";
        }
        while (value.length() != 0 && value.charAt(0) == ' ') {
            value = value.substring(1);
        }


        while (value.length() != 0 && value.charAt(value.length() - 1) == ' ') {
            value = value.substring(0, value.length() - 1);
        }

        for (int i = 0; i < value.length(); i++) {
            if (!Character.isAlphabetic(value.charAt(i))) {
                value = value.replace(String.valueOf(value.charAt(i)), "");
            }
        }

        return value;
    }

    /**
     * Returns the content of countryEdit without spaces in the beginning or end
     * @return countryEdit contents
     */
    public String getCountryString() {
        String value = countryEdit.getText().toString();

        if ( value.length() == 0) {
            return "";
        }
        while (value.length() != 0 &&value.charAt(0) == ' ') {
            value = value.substring(1);
        }


        while (value.length() != 0 && value.charAt(value.length() - 1) == ' ') {
            value = value.substring(0, value.length() - 1);
        }

        for (int i = 0; i < value.length(); i++) {
            if (!Character.isAlphabetic(value.charAt(i))) {
                value = value.replace(String.valueOf(value.charAt(i)), "");
            }
        }

        return value;
    }

    /**
     * Capitalizes a String - First letter capitalized, everything else non capitalized
     */
    public String capitalize(String string) {
        if (string.equals("")) {
            return "";
        }
        string.toLowerCase();
        string = string.substring(0,1).toUpperCase() + string.substring(1, string.length());
        return string;
    }


    /**
     * Diables all buttons, used when recording
     * @param active the active recording button, will remain active
     */
    public void disableButtons(final FloatingActionButton active) {
        firstRecordButton.setEnabled(false);
        lastRecordButton.setEnabled(false);
        cancelButton.setEnabled(false);
        confirmEditButton.setEnabled(false);
        recordButton.setEnabled(false);

        Runnable activateActive = new Runnable() {
            @Override
            public void run() {
                active.setEnabled(true);
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(activateActive, 500);
    }

    /**
     * Diables all buttons, used when recording
     * @param active the active recording button, will remain active
     */
    public void disableButtons(ImageButton active) {
        firstRecordButton.setEnabled(false);
        lastRecordButton.setEnabled(false);
        cancelButton.setEnabled(false);
        confirmEditButton.setEnabled(false);
        recordButton.setEnabled(false);

        active.setEnabled(true);
    }

    /**
     * Enables all buttons
     */
    public void enableButtons() {
        firstRecordButton.setEnabled(true);
        lastRecordButton.setEnabled(true);
        cancelButton.setEnabled(true);
        confirmEditButton.setEnabled(true);
        recordButton.setEnabled(true);
    }


    /**
     * AlertDialog asking the user for confirmation to copy pronounciations to the new country.
     * Only asks, if there are no existing files.
     */
    public void genericCountryChangedDialog() {
        // get the dialog
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        // set the custom layout
        Window win = alertDialog.getWindow();
        if (win != null) {
            win.setContentView(R.layout.confirm_generic_country_dialog);

            // text
            TextView textDialog = (TextView) win.findViewById(R.id.text_dialog);
            textDialog.setText("Do you want to keep the generic name " +
                    "pronunciation in relation to the country " + getCountryString());

            // cancel
            ImageButton cancelDialog = (ImageButton) win.findViewById(R.id.cancel_dialog);

            // NO
            cancelDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                    if(!confirmRequirements()) {
                        confirmRequirementsDialog();
                    }
                }
            });



            // confirm
            ImageButton acceptDialog = (ImageButton) win.findViewById(R.id.accept_dialog);

            // YES
            acceptDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // copy generic files
                    if (!firstRecordExists()) {
                        try {
                            // copy temp
                            if (new File(FileUtils.PATH + getFirstString().toLowerCase() + undoCountry.toLowerCase() + "temp.3gp").exists()) {
                                FileUtils.copyFile(new FileInputStream(new File(FileUtils.PATH + getFirstString().toLowerCase() + undoCountry.toLowerCase() + "temp.3gp")),
                                        new FileOutputStream(new File(FileUtils.PATH + getFirstString().toLowerCase() + getCountryString().toLowerCase() + "temp.3gp")));
                            } else {
                                // copy perm
                                FileUtils.copyFile(new FileInputStream(new File(FileUtils.PATH + getFirstString().toLowerCase() + undoCountry.toLowerCase() + ".3gp")),
                                        new FileOutputStream(new File(FileUtils.PATH + getFirstString().toLowerCase() + getCountryString().toLowerCase() + ".3gp")));
                            }
                            firstRecordColor();
                        } catch (IOException e) {
                            // handle the exception
                            Log.d("CopyIOException", e.getMessage());
                        }
                    }

                    if (!lastRecordExists()) {
                        try {
                            // copy temp
                            if (new File(FileUtils.PATH + getLastString().toLowerCase() + undoCountry.toLowerCase() + "temp.3gp").exists()) {
                                FileUtils.copyFile(new FileInputStream(new File(FileUtils.PATH + getLastString().toLowerCase() + undoCountry.toLowerCase() + "temp.3gp")),
                                        new FileOutputStream(new File(FileUtils.PATH + getLastString().toLowerCase() + getCountryString().toLowerCase() + "temp.3gp")));
                            } else {
                                // copy perm
                                FileUtils.copyFile(new FileInputStream(new File(FileUtils.PATH + getLastString().toLowerCase() + undoCountry.toLowerCase() + ".3gp")),
                                        new FileOutputStream(new File(FileUtils.PATH + getLastString().toLowerCase() + getCountryString().toLowerCase() + ".3gp")));
                            }
                            lastRecordColor();
                        } catch (IOException e) {
                            // handle the exception
                            Log.d("CopyIOException", e.getMessage());
                        }
                    }
                    alertDialog.dismiss();
                    // check if all requirements are met now
                    if(!confirmRequirements()) {
                        confirmRequirementsDialog();
                    }
                }
            });
        }
    }


    /**
     * resets the firstRecordButton
     */
    public void firstRecordColor() {
        firstRecordButton.setImageTintList(ColorStateList.valueOf(Color.BLACK));
        if (lastRecordButton.getBackground() == null) {
            recordButton.setBackgroundTintList(fabBackground);
        }
    }

    /**
     * resets the lastRecordButton color
     */
    public void lastRecordColor() {
        lastRecordButton.setImageTintList(ColorStateList.valueOf(Color.BLACK));
        if (firstRecordButton.getBackground() == null) {
            recordButton.setBackgroundTintList(fabBackground);
        }
    }

    /**
     * resets the recordButton color
     */
    public void customRecordColor() {
        recordButton.setBackgroundTintList(fabBackground);
        firstRecordColor();
        lastRecordColor();
    }


    /**
     * Alert dialog informing the user about the empty editText.
     */
    public void showEditAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setMessage("You can't record for an empty name.");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do nothing
            }
        });
        alertDialog.show();
    }
}
