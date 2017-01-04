package com.team16.sopra.sopra16team16.View;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.team16.sopra.sopra16team16.Controller.Backup;
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


        backupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                backup.exportDB();
            }
        });
        importButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                backup.importDBDialog(getActivity());
            }
        });

        return view;
    }
}
