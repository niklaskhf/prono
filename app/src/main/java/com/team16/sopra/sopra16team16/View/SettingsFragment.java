package com.team16.sopra.sopra16team16.View;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

import com.team16.sopra.sopra16team16.Controller.Export;
import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.Controller.Import;
import com.team16.sopra.sopra16team16.R;

import java.util.Locale;

/**
 * Fragment allowing the user to see settings.
 * These include:
 *  - language selection
 *  - export
 *  - import
 *  - reset
 */
public class SettingsFragment extends Fragment {
    Export export = new Export();
    Import importObject = new Import();

    Locale myLocale;
    TextView languageText;
    Spinner languageSpinner;
    String language;
    ArrayAdapter<CharSequence> languageAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                export.exportDB();
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

        languageAdapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.languages, android.R.layout.simple_spinner_item);
        languageText = (TextView) view.findViewById(R.id.language_text);
        languageSpinner = (Spinner) view.findViewById(R.id.language_spinner);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        languageSpinner.setAdapter(languageAdapter);
        setSpinnerToCurrentLanguage();
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setGlobalLanguage(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        return view;
    }


    /**
     * Alert dialog informing the user about the loss of data.
     */
    public void confirmImportDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setMessage(getString(R.string.overwrite_data));
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                importObject.importDBDialog(getActivity());
            }
        });
        alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

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

        alertDialog.setMessage(getString(R.string.overwrite_data));
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ContactManager.getInstance(getActivity()).wipe();
                Toast.makeText(HomeActivity.contextOfApplication, getString(R.string.data_reset), Toast.LENGTH_SHORT)
                        .show();
            }
        });
        alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dismiss
            }
        });
        alertDialog.show();
    }

    /**
     * set spinner to current app language
     */
    public void setSpinnerToCurrentLanguage() {
        SharedPreferences sharedPreference = getActivity().getSharedPreferences("getLanguage", Context.MODE_PRIVATE);
        Log.d("lang", myLocale.getDefault().getLanguage());
        language = sharedPreference.getString("updatedLanguage", myLocale.getDefault().getLanguage());
        Log.d("lang", myLocale.getDefault().getLanguage());
        switch (language) {
            case "de":
                languageSpinner.setSelection(0, false);
                break;
            case "en":
                languageSpinner.setSelection(1, false);
                break;
            case "ru":
                languageSpinner.setSelection(2, false);
                break;
            case "tr":
                languageSpinner.setSelection(3, false);
                break;
        }
    }

    /**
     * performs setLocale(lang) depending on language chosen in languageSpinner
     *
     * @param i position in spinner
     */
    public void setGlobalLanguage(int i) {
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

    /**
     * sets language chosen in spinner to new global language and sends its
     * String to HomeActivity. So after every new app launch the chosen
     * language is maintained
     *
     * @param lang
     */
    public void setLocale(String lang) {
        ((HomeActivity)getActivity()).setLocale(lang, getActivity().getBaseContext().getResources());
        saveChosenLanguageString(lang);
        getActivity().finish();
        startActivity(getActivity().getIntent());
    }

    /**
     * saves the Locale string of chosen language in SharedPreferences
     *
     * @param lang
     */
    public void saveChosenLanguageString(String lang) {
        SharedPreferences myLanguagePreference = getActivity().getSharedPreferences("getLanguage", Context.MODE_PRIVATE);
        SharedPreferences.Editor languageEditor = myLanguagePreference.edit();
        languageEditor.putString("updatedLanguage", lang);
        languageEditor.commit();
    }
}
