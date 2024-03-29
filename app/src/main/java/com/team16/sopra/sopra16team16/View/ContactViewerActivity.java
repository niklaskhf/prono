package com.team16.sopra.sopra16team16.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.Controller.FileUtils;
import com.team16.sopra.sopra16team16.Controller.Player;
import com.team16.sopra.sopra16team16.R;

/**
 * Displays information of a contact.
 * Allows editing and deleting the contacts information.
 */
public class ContactViewerActivity extends AppCompatActivity {

    private ContactManager contactManager = null;
    private String firstName = "";
    private String lastName = "";
    private String title = "";
    private String country = "";
    private String gender = "";


    private int id;

    private TextView firstView;
    private TextView lastView;
    private TextView titleView;
    private TextView countryView;
    private ImageView genderSign;

    private FloatingActionButton playButton;
    private Player player;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        player = Player.getCurrentInstance();

        this.setContentView(R.layout.contact_viewer);
        contactManager = ContactManager.getInstance(this.getApplicationContext());

        Bundle bundle = getIntent().getExtras();

        firstName = bundle.get("first").toString();
        lastName = bundle.get("last").toString();
        title = bundle.get("title").toString();
        country = bundle.get("country").toString();
        gender = bundle.getString("gender");
        id = Integer.parseInt(bundle.get("id").toString());


        setTitle(R.string.contact);

        setTextViews();

        FloatingActionButton editButton = (FloatingActionButton) findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactViewerActivity.this, NewContactActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("first", firstName);
                intent.putExtra("last", lastName);
                intent.putExtra("title", title);
                intent.putExtra("country", country);
                intent.putExtra("gender", gender);
                // EDIT mode
                intent.putExtra("cause", "EDIT");

                startActivityForResult(intent, 2);
            }
        });

        // should probably put all this into a seperate method
        FloatingActionButton deleteButton = (FloatingActionButton) findViewById(R.id.delete_button);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteContactDialog(id, firstName, lastName);
            }
        });

        playButton = (FloatingActionButton) findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player.isPlaying()) {
                    player.stopPlaying(playButton, id);
                } else {
                    player.startPlaying(id, playButton);
                }

            }
        });

    }

    /**
     * Deletes a contact after asking the user for confirmation.
     *
     * @param id - unique id of row in database - int
     * @param f  first name of contact - String
     * @param l  last name of contact - String
     */
    public void deleteContactDialog(int id, String f, String l) {
        // im sure this is god awful
        final int idF = id;

        // build the AlertDialog to request confirmation
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        
        String message = getString(R.string.about_to_delete) + " "+ f +" " + l + "?";

        // delete if user confirms with 'YES"
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Log.i("deletionDialog", "user confirmed deletion of " + idF);
                        //contactManager.deleteContact(idF);
                        contactManager.toggleDeleted(idF, 0);
                        // go back to the front page
                        Intent intent = new Intent();
                        intent.putExtra("action", "undo");
                        intent.putExtra("undoId", idF);
                        setResult(RESULT_OK, intent);
                        dialogInterface.dismiss();
                        onBackPressed();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        Log.i("deletionDialog", "User dismissed the dialog");
                        dialogInterface.dismiss();
                        break;
                    default:
                        dialogInterface.dismiss();
                        break;
                }
            }
        };

        alertBuilder.setMessage(message);
        alertBuilder.setPositiveButton(getString(R.string.yes), dialogClickListener);
        alertBuilder.setNegativeButton(getString(R.string.no), dialogClickListener);
        alertBuilder.show();
    }

    /**
     * Populates the textViews in the contact_viewer layout
     */
    public void setTextViews() {
        // populate textViews
        firstView = (TextView) findViewById(R.id.real_first_name);
        lastView = (TextView) findViewById(R.id.real_last_name);
        titleView = (TextView) findViewById(R.id.real_title);
        countryView = (TextView) findViewById(R.id.real_country);
        genderSign = (ImageView) findViewById(R.id.gender_sign);
        setText();
    }

    /**
     * set information after contact has been clicked
     */
    public void setText() {
        firstView.setText(firstName);
        lastView.setText(lastName);
        titleView.setText(title);
        countryView.setText(country);
        switch (gender) {
            case "MALE":
                genderSign.setImageResource(R.drawable.ic_male_gender);
                break;
            case "FEMALE":
                genderSign.setImageResource(R.drawable.ic_female_gender);
                break;
            case "UNKNOWN":
                genderSign.setImageResource(android.R.drawable.sym_def_app_icon);
                break;
            default:
                genderSign.setImageResource(android.R.drawable.sym_def_app_icon);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 2) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                firstName = data.getStringExtra("first");
                lastName = data.getStringExtra("last");
                title = data.getStringExtra("title");
                country = data.getStringExtra("country");
                gender = data.getStringExtra("gender");
                setText();
                showSnackbar(data);
            }
        }
    }

    /**
     * Shows a snackbar allowing the user to undo editing the contact
     *
     * @param data - Intent - result from startActivityForResult
     */
    public void showSnackbar(final Intent data) {
        Log.d("showSnackar", "called");
        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.contact_viewer_coord);

        Log.d("undoSnackbar", "showing snackbar for " + id);
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, getString(R.string.contactEdited), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.UNDO), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbarSuccess = Snackbar.make(coordinatorLayout, getString(R.string.contactRestored), Snackbar.LENGTH_SHORT);
                        snackbarSuccess.show();

                        // replace the audio files
                        String original = FileUtils.PATH + id + ".3gp";
                        String replacement = FileUtils.PATH + id + "_undo.3gp";
                        FileUtils.replaceFile(original, replacement);

                        firstName = data.getStringExtra("undoFirst");
                        lastName = data.getStringExtra("undoLast");
                        title = data.getStringExtra("undoTitle");
                        country = data.getStringExtra("undoCountry");
                        gender = data.getStringExtra("undoGender");

                        // update views
                        setText();
                        contactManager.updateContact(id, firstName, lastName, title, country, gender);
                    }
                });


        snackbar.setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                // delete the temporary undo file
                String action = data.getStringExtra("action");
                if (action != null && action.equals("edit")) {
                    FileUtils.deleteFile(FileUtils.PATH + id + "_undo.3gp");
                }
            }
        });

        snackbar.show();
    }


    @Override
    public void onPause() {
        super.onPause();
        player.release();
    }

}
