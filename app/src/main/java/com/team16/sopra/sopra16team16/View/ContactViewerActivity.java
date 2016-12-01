package com.team16.sopra.sopra16team16.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.team16.sopra.sopra16team16.Model.Gender;
import com.team16.sopra.sopra16team16.R;

/**
 * Created by prime on 22.11.16.
 */

public class ContactViewerActivity extends AppCompatActivity {

    private int recordButtonClicked = 2;
    private static ContactManager contactManager;
    public static Context contextOfApplication;

    private String firstName;
    private String lastName;
    private String country;
    private String title;
    private int id;
    private String gender;

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

    private Intent intent;



    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setContentView(R.layout.contact_editor);

        //initializing all GUI components with ids
        findViewByIdTextView();
        findViewByIdEditButton();
        findViewByIdImageButton();
        findViewByIdRadioButton();
        findViewByIdImageView();

        setViewLayout();

        Bundle bundle = getIntent().getExtras();

        // initialize values with the information of the clicked contact
        if (bundle != null) {
            firstName = bundle.getString("first");
            lastName = bundle.getString("last");
            title = bundle.getString("title");
            country = bundle.getString("country");
            gender = bundle.getString("gender");
            id = bundle.getInt("id");
            Log.i("bundle.getInt", bundle.getString("id"));
        }

        //set contact information
        setText();

        //Initialize different OnClick methods
        confirmTheEdit();
        editContact();
        cancelTheEdit();
        recordName();
        deleteContact();

    }

    /**
     * deletes the displaying contact
     */
    public void deleteContact() {
        final ImageButton deleteButton = (ImageButton) findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteContactDialog(id, firstNameText.getText().toString(), lastNameText.getText().toString());
            }
        });
    }

    /**
     * records an audio until the recordButton is clicked a second time
     */
    public void recordName() {
        final ImageButton recordButton = (ImageButton) findViewById(R.id.record_button);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recorder record = new Recorder();
                if(recordButtonClicked % 2 == 0) {
                    record.startRecording();
                    recordButtonClicked++;
                }else {
                    record.stopRecording();
                    recordButtonClicked++;
                }

            }
        });
    }

    /**
     * cancels the the editing of the opened contact
     */
    public void cancelTheEdit() {
        final ImageButton cancelButton = (ImageButton) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                setViewLayout();
            }
        });
    }

    /**
     * switches to edit mode of opened contact
     */
    public void editContact() {
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
    }

    /**
     * saves the new contact and switches to view mode
     */
    public void confirmTheEdit() {
        final ImageButton confirmEditButton = (ImageButton)findViewById(R.id.confirm_button);
        confirmEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewLayout();
                firstNameText.setText(firstNameEdit.getText().toString());
                lastNameText.setText(lastNameEdit.getText().toString());
                countryText.setText(countryEdit.getText().toString());
                titleText.setText(titleEdit.getText().toString());

                intent = new Intent(ContactViewerActivity.this, HomeActivity.class);
                contactManager.updateContact(firstNameText.getText().toString(), lastNameText.getText().toString(),
                                            titleText.getText().toString(),countryText.getText().toString(),gender,
                                            false, false, id);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * set information after contact has been clicked
     */
    public void setText() {
        firstNameText.setText(firstName);
        lastNameText.setText(lastName);
        titleText.setText(title);
        countryText.setText(country);

        switch (gender) {
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

    /**
     * Deletes a contact after asking the user for confirmation.
     * @param id
     * @param f
     * @param l
     */
    public void deleteContactDialog(int id, String f, String l) {
        // im sure this is god awful
        final int idF = id;
        // build the AlertDialog to request confirmation
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        String message = "About to delete " + f +
                " " + l +
                ". Continue?";
        // delete if user confirms with 'YES"
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Log.i("hit OK", "user hit delete");
                        contactManager.deleteContact(idF);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        Log.i("deleted", "User dismissed the dialog");
                        dialogInterface.dismiss();
                        break;
                    default:
                        break;
                }
            }
        };
        alertBuilder.setMessage(message);
        alertBuilder.setPositiveButton("YES", dialogClickListener);
        alertBuilder.setNegativeButton("NO", dialogClickListener);
        alertBuilder.show();
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
//From here on different declarations take place

    /**
     * connects the gender images to following ImageViews: genderSign, maleImage, unkownSexImage
     */
    private void findViewByIdImageView() {
        genderSign = (ImageView) findViewById(R.id.gender_sign);
    }

    /**
     * connects the gender images to the following RadioButtons: femaleRadioButton, maleRadioButton, unknownSexRadioButton
     */
    private void findViewByIdRadioButton() {
        femaleRadioButton = (RadioButton) findViewById(R.id.female_radioButton);
        maleRadioButton = (RadioButton) findViewById(R.id.male_radioButton);
        unknownSexRadioButton = (RadioButton) findViewById(R.id.unkown_radioButton);
    }

    /**
     * connects the ImageButtons on the bottom of the display to the following imageButtons
     * confirmButton, cancelButton, recordButton, editButton, deleteButton, confirmButton, playButton
     */
    private void findViewByIdImageButton() {
        confirmButton = (ImageButton) findViewById(R.id.confirm_button);
        cancelButton= (ImageButton) findViewById(R.id.cancel_button);
        recordButton= (ImageButton) findViewById(R.id.record_button);
        editButton = (ImageButton) findViewById(R.id.edit_button);
        deleteButton = (ImageButton) findViewById(R.id.delete_button);
        confirmButton = (ImageButton) findViewById(R.id.confirm_button);
        playButton = (ImageButton) findViewById(R.id.play_button);
    }

    /**
     * connects all EditTexts from layout to the following EditTexts
     * firstNameEdit, lastNameEdit, titleEditm countryEdit
     */
    private void findViewByIdEditButton() {
        firstNameEdit = (EditText) findViewById(R.id.first_edit);
        lastNameEdit = (EditText) findViewById(R.id.last_edit);
        countryEdit = (EditText) findViewById(R.id.country_edit);
        titleEdit = (EditText) findViewById(R.id.title_edit);
    }

    /**
     * connects all TextView from layout to the following TextViews
     * firstNameText, lastNameText, titleText, countryText
     */
    private void findViewByIdTextView() {
        firstNameText = (TextView) findViewById(R.id.real_first_name);
        lastNameText = (TextView) findViewById(R.id.real_last_name);
        countryText = (TextView) findViewById(R.id.real_country);
        titleText= (TextView) findViewById(R.id.real_title);
    }

    /**
     * sets GUI components which are used for edit mode to visible and the rest (components for view mode) to invisible
     */
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
        countryEdit.setVisibility(View.VISIBLE);
        titleEdit.setVisibility(View.VISIBLE);


        femaleRadioButton.setVisibility(View.VISIBLE);
        maleRadioButton.setVisibility(View.VISIBLE);
        unknownSexRadioButton.setVisibility(View.VISIBLE);
    }

    /**
     * sets GUI components which are used for view mode to visible and the rest (components for edit mode) to invisible
     */
    private void setViewLayout(){

        firstNameEdit.setVisibility(View.INVISIBLE);
        lastNameEdit.setVisibility(View.INVISIBLE);
        countryEdit.setVisibility(View.INVISIBLE);
        titleEdit.setVisibility(View.INVISIBLE);

        femaleRadioButton.setVisibility(View.INVISIBLE);
        maleRadioButton.setVisibility(View.INVISIBLE);
        unknownSexRadioButton.setVisibility(View.INVISIBLE);

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

        genderSign.setVisibility(View.VISIBLE);

        //checking which radio button is checked in order to update the gender symbol if needed
        if (femaleRadioButton.isChecked()) {
            genderSign.setImageResource(R.drawable.pregnant_woman);
        } else if (maleRadioButton.isChecked()) {
            genderSign.setImageResource(R.drawable.running_man);
        } else if (unknownSexRadioButton.isChecked()) {
            genderSign.setImageResource(android.R.drawable.sym_def_app_icon);
        }
    }
}
