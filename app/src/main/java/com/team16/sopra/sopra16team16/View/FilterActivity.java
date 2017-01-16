package com.team16.sopra.sopra16team16.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.Controller.Filter;
import com.team16.sopra.sopra16team16.Controller.Sorter;
import com.team16.sopra.sopra16team16.R;

/**
 * The user can change the filter and the sorter as desired
 */

public class FilterActivity extends AppCompatActivity {

    RadioButton first_ASC;
    RadioButton first_DESC;
    RadioButton last_ASC;
    RadioButton last_DESC;

    RadioButton female;
    RadioButton male;
    RadioButton unknown;

    EditText edit;


    Filter filter = Filter.getCurrentInstance();
    Sorter sorter = Sorter.getCurrentInstance();

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setContentView(R.layout.activity_filter);

        first_ASC = (RadioButton) findViewById(R.id.first_ASC_radioButton);
        first_DESC = (RadioButton) findViewById(R.id.first_DESC_radioButton);
        last_ASC = (RadioButton) findViewById(R.id.last_ASC_radioButton);
        last_DESC = (RadioButton) findViewById(R.id.last_DESC_radioButton);

        female = (RadioButton) findViewById(R.id.female_radioButton);
        male = (RadioButton) findViewById(R.id.male_radioButton);
        unknown = (RadioButton) findViewById(R.id.unknown_radioButton);

        edit = (EditText) findViewById(R.id.country_edit);

        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(edit.getText().toString().equals("")) {
                    filter.setCountry(null);
                } else {
                    filter.setCountry(edit.getText().toString());
                }
            }
        });

        //RadioGroup doesn't work over different linear layouts
        first_ASC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                first_ASC.setChecked(true);
                first_DESC.setChecked(false);
                last_ASC.setChecked(false);
                last_DESC.setChecked(false);
                sorter.setDirection("ASC");
                sorter.setSortedBy(ContactManager.COLUMN_FIRSTNAME);
            }
        });

        first_DESC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                first_ASC.setChecked(false);
                first_DESC.setChecked(true);
                last_ASC.setChecked(false);
                last_DESC.setChecked(false);
                sorter.setDirection("DESC");
                sorter.setSortedBy(ContactManager.COLUMN_FIRSTNAME);
            }
        });

        last_ASC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                first_ASC.setChecked(false);
                first_DESC.setChecked(false);
                last_ASC.setChecked(true);
                last_DESC.setChecked(false);
                sorter.setDirection("ASC");
                sorter.setSortedBy(ContactManager.COLUMN_LASTNAME);
            }
        });

        last_DESC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                first_ASC.setChecked(false);
                first_DESC.setChecked(false);
                last_ASC.setChecked(false);
                last_DESC.setChecked(true);
                sorter.setDirection("DESC");
                sorter.setSortedBy(ContactManager.COLUMN_LASTNAME);
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                filter.setGender("FEMALE");
            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                filter.setGender("MALE");
            }
        });

        unknown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                filter.setGender("UNKNOWN");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
