package com.team16.sopra.sopra16team16.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.team16.sopra.sopra16team16.Controller.Backup;
import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.R;


public class SettingsFragment extends PreferenceFragment {
    Backup backup  = new Backup();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        Button backupButton = (Button) view.findViewById(R.id.backup_button);
        Button importButton = (Button) view.findViewById(R.id.import_button);
        Button resetButton = (Button) view.findViewById(R.id.reset_button);


        backupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                backup.exportDB();
            }
        });
        importButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                confirmImportDialog();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmResetDialog();
            }
        });
        return view;
    }



    /**
     * Alert dialog informing the user about the loss of data.
     */
    public void confirmImportDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setMessage("This will overwrite all existing data. Continue?");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                backup.importDBDialog(getActivity());
            }
        });
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dismiss
            }
        });
        alertDialog.show();
    }

    /**
     * Alert dialog informing the user about the loss of data.
     */
    public void confirmResetDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setMessage("This will wipe all existing data. Continue?");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ContactManager.getInstance(getActivity()).wipe();
                Toast.makeText(HomeActivity.contextOfApplication, "All data reset!", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dismiss
            }
        });
        alertDialog.show();
    }
}
