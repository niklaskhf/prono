package com.team16.sopra.sopra16team16.View;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.R;

/**
 * About fragment - used to display project information
 * Does literally nothing
 */
public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);
        TextView tvMot = (TextView) view.findViewById(R.id.about_motivation);
        TextView tvTeam = (TextView) view.findViewById(R.id.about_team);
        TextView tvLicense = (TextView) view.findViewById(R.id.about_license);


        // TODO write something nicer
        tvMot.setText("The app 'Prono' was created for EPLAN, as part of the SoPra2016/17 at the University of Stuttgart.\n" +
                "The goal of the app is to give users a way to avoid embarrassing themselves, by saving the pronunciation of a name," +
                " and being able to check back on it and prepare for meeting a person");


        tvTeam.setText("Kenan Allahyarli\n" +
                "Niklas Kammhoff\n" +
                "Martin Stach");

        tvLicense.setText("Apache License 2.0");
        return view;
    }
}
