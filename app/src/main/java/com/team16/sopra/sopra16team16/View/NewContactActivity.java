package com.team16.sopra.sopra16team16.View;

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
 * Created by prime on 18.11.16.
 */

public class NewContactActivity extends AppCompatActivity {

    private int recordButtonClicked = 2;
    private static ContactManager contactManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.contact_editor);

        findViewByIdTextView();

        findViewByIdEditButton();

        findViewByIdImageButton();

        findViewByIdRadioButton();

        findViewByIdImageView();

        setEditLayout();

        confirmTheEdit();
        editContact();
        cancelTheEditing_Adding();
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
                deleteContactDialog((Integer) view.getTag(), firstNameText.getText().toString(), lastNameText.getText().toString());
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
     * cancels the adding of a new contact or the editing of the newly added contact
     */
    public void cancelTheEditing_Adding() {
        final ImageButton cancelButton = (ImageButton) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewContactActivity.this, HomeActivity.class));
                finish();
            }
            });
    }

    /**
     * switches to edit mode of the newly added contact
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
                }
            });
    }

    // by pressing "back" all information about the new contact are forwarded to HomeActivity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(NewContactActivity.this, HomeActivity.class);
        setContact(i);
        startActivity(i);
        finish();
    }

    /**
     * save contact info in Intent to pass it to HomeActivity which creates the new contact
     */
    public Intent setContact(Intent i) {
        String gender = "";

        if (femaleRadioButton.isChecked()) {
            gender = "FEMALE";
        } else if (maleRadioButton.isChecked()) {
            gender = "MALE";
        } else {
            gender = "UNKNOWN";
        }

        i.putExtra(contactManager.COLUMN_FIRSTNAME, firstNameEdit.getText().toString());
        i.putExtra(contactManager.COLUMN_LASTNAME, lastNameEdit.getText().toString());
        i.putExtra(contactManager.COLUMN_COUNTRY, countryEdit.getText().toString());
        i.putExtra(contactManager.COLUMN_TITLE, titleEdit.getText().toString());
        i.putExtra(contactManager.COLUMN_GENDER, gender);

        return i;
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
        titleEdit = (EditText) findViewById(R.id.title_edit);
        countryEdit = (EditText) findViewById(R.id.country_edit);
    }

    /**
     * connects all TextView from layout to the following TextViews
     * firstNameText, lastNameText, titleText, countryText
     */
    private void findViewByIdTextView() {
        firstNameText = (TextView) findViewById(R.id.real_first_name);
        lastNameText = (TextView) findViewById(R.id.real_last_name);
        titleText= (TextView) findViewById(R.id.real_title);
        countryText = (TextView) findViewById(R.id.real_country);
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
