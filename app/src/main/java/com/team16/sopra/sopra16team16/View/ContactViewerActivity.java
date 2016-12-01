package com.team16.sopra.sopra16team16.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.Controller.Player;
import com.team16.sopra.sopra16team16.Model.Gender;
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

    TextView firstView;
    TextView lastView;
    TextView titleView;
    TextView countryView;
    TextView maleIcon;
    TextView femaleIcon;
    // TODO GENDER VIEW
    // TODO PLAY BUTTON

    ImageButton playButton;
    Player player;



    // TODO IMPLEMENT backPressed(), intent to homeActivity

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);


        player = Player.getCurrentInstance(getApplicationContext());

        this.setContentView(R.layout.contact_viewer);
        contactManager = ContactManager.getInstance(this);

        // TODO FIX INTENT
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            firstName = bundle.get("first").toString();
            lastName = bundle.get("last").toString();
            title = bundle.get("title").toString();
            country = bundle.get("country").toString();
            gender = bundle.getString("gender");
            id = Integer.parseInt(bundle.get("id").toString());
        }
        setTextViews();

        Button editButton = (Button) findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactViewerActivity.this, NewContactActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("first", firstName);
                intent.putExtra("last", lastName);
                intent.putExtra("title", title);
                intent.putExtra("country", country);
                Log.i("country", country);
                intent.putExtra("gender", gender);
                intent.putExtra("cause", "EDIT");

                startActivity(intent);
            }
        });

        // should probably put all this into a seperate method
        Button deleteButton = (Button) findViewById(R.id.delete_button);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteContactDialog(id, firstName, lastName);
            }
        });

        playButton = (ImageButton) findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player.isPlaying()) {
                    player.stopPlaying(playButton);
                } else {
                    player.startPlaying(id, playButton);
                }

            }
        });

    }

    /**
     * Deletes a contact after asking the user for confirmation.
     * @param id - unique id of row in database - int
     * @param f first name of contact - String
     * @param l last name of contact - String
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
                        Log.i("deletionDialog", "user confirmed deletion of " + idF);
                        contactManager.deleteContact(idF);
                        // go back to the front page
                        onBackPressed();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        Log.i("deletionDialog", "User dismissed the dialog");
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

    /**
     * Populates the textViews in the contact_viewer layout
     */
    public void setTextViews() {
        // populate textViews
        firstView = (TextView) findViewById(R.id.real_first_name);
        lastView = (TextView) findViewById(R.id.real_last_name);
        titleView = (TextView) findViewById(R.id.real_title);
        countryView = (TextView) findViewById(R.id.real_country);
        femaleIcon = (TextView) findViewById(R.id.female_sign);
        maleIcon = (TextView) findViewById(R.id.male_sign);

        maleIcon.setVisibility(View.INVISIBLE);
        femaleIcon.setVisibility(View.INVISIBLE);
        firstView.setText(firstName);
        lastView.setText(lastName);
        titleView.setText(title);
        countryView.setText(country);
        // TODO SET GENDER ICON
        if (gender.equals("MALE")) {
            maleIcon.setVisibility(View.VISIBLE);
        } else if (gender.equals("FEMALE")) {
            femaleIcon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ContactViewerActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }

}
