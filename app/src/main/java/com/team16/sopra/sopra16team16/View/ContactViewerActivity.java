package com.team16.sopra.sopra16team16.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.Model.Gender;
import com.team16.sopra.sopra16team16.R;

/**
 * Displays information of a contact.
 * Allows editing and deleting the contacts information.
 */

public class ContactViewerActivity extends AppCompatActivity {

    private ContactManager contactManager = null;
    private String firstName;
    private String lastName;
    private String title;
    private String country;
    private Gender gender;
    int id;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setContentView(R.layout.contact_viewer);
        contactManager = ContactManager.getInstance(this);

        Button editButton = (Button) findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContactViewerActivity.this, NewContactActivity.class));
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

}
