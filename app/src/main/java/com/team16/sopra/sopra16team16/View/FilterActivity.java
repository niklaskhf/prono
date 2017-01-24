package com.team16.sopra.sopra16team16.View;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.Controller.ContactManager;
import com.team16.sopra.sopra16team16.Controller.Filter;
import com.team16.sopra.sopra16team16.Controller.Sorter;
import com.team16.sopra.sopra16team16.R;

/**
 * The user can change the filter and the sorter as desired
 */
public class FilterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    RadioButton first_ASC;
    RadioButton first_DESC;
    RadioButton last_ASC;
    RadioButton last_DESC;

    RadioButton female;
    RadioButton male;
    RadioButton unknown;

    RadioButton country_enabled;
    RadioButton country_disabled;
    Spinner spinner;

    Filter filter = Filter.getCurrentInstance();
    Sorter sorter = Sorter.getCurrentInstance();

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setContentView(R.layout.activity_filter);

        setTitle(getString(R.string.filter));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //initialize gui elements
        first_ASC = (RadioButton) findViewById(R.id.first_ASC_radioButton);
        first_DESC = (RadioButton) findViewById(R.id.first_DESC_radioButton);
        last_ASC = (RadioButton) findViewById(R.id.last_ASC_radioButton);
        last_DESC = (RadioButton) findViewById(R.id.last_DESC_radioButton);
        female = (RadioButton) findViewById(R.id.filter_female_radioButton);
        male = (RadioButton) findViewById(R.id.filter_male_radioButton);
        unknown = (RadioButton) findViewById(R.id.filter_unknown_radioButton);
        country_enabled = (RadioButton) findViewById(R.id.country_enabled_radioButton);
        country_disabled = (RadioButton) findViewById(R.id.country_disabled_radioButton);

        spinner = (Spinner) findViewById(R.id.country_spinner);


        //load settings
        loadSettings();

        setCountryList();

        //initialize listeners
        //RadioGroup doesn't work over different linear layouts, so check them manually
        first_ASC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setCheck(first_ASC);
                sorter.setDirection("ASC");
                sorter.setSortedBy(ContactManager.COLUMN_FIRSTNAME);
            }
        });

        first_DESC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setCheck(first_DESC);
                sorter.setDirection("DESC");
                sorter.setSortedBy(ContactManager.COLUMN_FIRSTNAME);
            }
        });

        last_ASC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setCheck(last_ASC);
                sorter.setDirection("ASC");
                sorter.setSortedBy(ContactManager.COLUMN_LASTNAME);
            }
        });

        last_DESC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setCheck(last_DESC);
                sorter.setDirection("DESC");
                sorter.setSortedBy(ContactManager.COLUMN_LASTNAME);
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(!filter.setGender("FEMALE")) {
                    female.setChecked(false);
                }
            }
        });
        male.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(!filter.setGender("MALE")) {
                    male.setChecked(false);
                }
            }
        });
        unknown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(!filter.setGender("UNKNOWN")) {
                    unknown.setChecked(false);
                }
            }
        });
        country_enabled.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                //set the first item from the spinner as country filter
                filter.setCountry(returnFirstItem());
            }
        });
        country_disabled.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                spinner.setVisibility(View.INVISIBLE);
                //disable country filter
                filter.setCountry(null);
            }
        });
    }

    /**
     * setCheck for the sorter
     * @param button which is checked true
     */
    private void setCheck(RadioButton button) {
        first_ASC.setChecked(false);
        first_DESC.setChecked(false);
        last_ASC.setChecked(false);
        last_DESC.setChecked(false);
        button.setChecked(true);
    }

    /**
     * load the settings from the filter and sorter
     */
    private void loadSettings() {
        //set the direction
        if(sorter.getSortedBy().equals(ContactManager.COLUMN_FIRSTNAME)) {
            if(sorter.getDirection().equals("ASC")) {
                setCheck(first_ASC);
            } else {
                setCheck(first_DESC);
            }
        } else {
            if(sorter.getDirection().equals("ASC")) {
                setCheck(last_ASC);
            } else {
                setCheck(last_DESC);
            }
        }

        //set the gender filter
        for(int i = 0; i < filter.getGenderList().size(); ++i) {
            if(filter.getGenderList().get(i) == null) {
                break;
            }
            if(filter.getGenderList().get(i).equals("FEMALE")) {
                female.setChecked(true);
            }
            if(filter.getGenderList().get(i).equals("MALE")) {
                male.setChecked(true);
            }
            if(filter.getGenderList().get(i).equals("UNKNOWN")){
                unknown.setChecked(true);
            }
        }

        //set the country filter
        if(filter.getCountry() == null) {
            country_disabled.setChecked(true);
            spinner.setVisibility(View.INVISIBLE);
        } else {
            country_enabled.setChecked(true);
        }
    }

    /**
     * fills the spinner with all countries of the contacts
     */
    private void setCountryList() {
        Cursor cursorCountries = ContactManager.getInstance(getApplicationContext()).getCountryList();

        String[] columns = new String[] { "country" };
        int[] temp = new int[] { android.R.id.text1 };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursorCountries, columns, temp, 0);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //cursorCountries.close();

        //load country from the filter
        selectItem();
    }

    /**
     * selects the country saved in the filter
     */
    private void selectItem() {
        if(Filter.getCountry() == null || spinner.getCount() == 0) return;

        Cursor cursorFilter = (Cursor) spinner.getItemAtPosition(0);

        for(int i = 0; i < spinner.getCount(); ++i) {
            cursorFilter.moveToPosition(i);
            String item = cursorFilter.getString(cursorFilter.getColumnIndex(ContactManager.COLUMN_COUNTRY));
            if(item.equals(Filter.getCountry())) {
                spinner.setSelection(i);
            }
        }
    }

    /**
     * Returns the first item of the spinner
     * @return first item from the spinner
     */
    private String returnFirstItem() {
        //no country available
        if(spinner.getCount() == 0) return null;

        Cursor cursorFilter = (Cursor) spinner.getItemAtPosition(0);
        cursorFilter.moveToPosition(0);
        String item = cursorFilter.getString(cursorFilter.getColumnIndex(ContactManager.COLUMN_COUNTRY));
        return item;
    }

    @Override
    /**
     * close the activity
     */
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = (String) ((TextView) view).getText();
        if(country_enabled.isChecked()) {
            filter.setCountry(item);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}