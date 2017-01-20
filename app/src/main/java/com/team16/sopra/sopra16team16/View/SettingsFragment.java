package com.team16.sopra.sopra16team16.View;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.team16.sopra.sopra16team16.Controller.Backup;
import com.team16.sopra.sopra16team16.R;

import java.util.Locale;


public class SettingsFragment extends Fragment {
    Backup backup = new Backup();

    Locale myLocale;
    TextView languageText;
    Spinner languageSpinner;
    String language;

    ArrayAdapter<CharSequence> languageAdapter;
    boolean isFirstSelection = true;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // Load the preferences from an XML resource
////        addPreferencesFromResource(R.xml.preferences);
//        getActivity().setContentView(R.layout.settings_view);
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_view, container, false);

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

        languageAdapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.languages, android.R.layout.simple_spinner_item);

        languageText = (TextView) view.findViewById(R.id.language_text);
        languageSpinner = (Spinner) view.findViewById(R.id.language_spinner);

        languageAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        languageSpinner.setAdapter(languageAdapter);

        setSpinnerToCurrentLanguage();

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setGlobalLanguage(adapterView, i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    /**
     * set spinner to current app language
     */
    public void setSpinnerToCurrentLanguage() {
        SharedPreferences sharedPreference = getActivity().getSharedPreferences("getLanguage", Context.MODE_PRIVATE);
        language = sharedPreference.getString("updatedLanguage", myLocale.getDefault().getLanguage());

        switch (language) {
            case "de":
                languageSpinner.setSelection(0);
                break;
            case "en":
                languageSpinner.setSelection(1);
                break;
            case "ru":
                languageSpinner.setSelection(2);
                break;
            case "tr":
                languageSpinner.setSelection(3);
                break;
        }
    }

    /**
     * performs setLocale(lang) depending on language chosen in languageSpinner
     * @param adapterView
     * @param i
     */
    public void setGlobalLanguage(AdapterView<?> adapterView, int i) {
        if (!isFirstSelection) {

            switch (i) {
                case 0:
                    setLocale("de");
                    break;
                case 1:
                    setLocale("en");
                    break;
                case 2:
                    setLocale("ru");
                    break;
                case 3:
                    setLocale("tr");
                    break;
            }
        }
        isFirstSelection = false;
    }

    /**
     * sets language chosen in spinner to new global language and sends its
     * String to HomeActivity. So after every new app launch the chosen
     * language is maintained
     * @param lang
     */
    public void setLocale(String lang) {
        HomeActivity home = new HomeActivity();
        home.setLocale(lang, getActivity().getBaseContext().getResources());
        saveChosenLanguageString(lang);
        getActivity().finish();
        startActivity(getActivity().getIntent());
    }

    /**
     * saves the Locale string of chosen language in SharedPreferences
     * @param lang
     */
    public void saveChosenLanguageString(String lang) {
        SharedPreferences myLanguagePreference = getActivity().getSharedPreferences("getLanguage", Context.MODE_PRIVATE);
        SharedPreferences.Editor languageEditor = myLanguagePreference.edit();
        languageEditor.putString("updatedLanguage", lang);
        languageEditor.commit();
    }

}