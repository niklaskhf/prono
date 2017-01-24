package com.team16.sopra.sopra16team16.View;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.R;

/**
 * About fragment - used to display project information
 */
public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);
        TextView tvMot = (TextView) view.findViewById(R.id.about_motivation);
        TextView tvTeam = (TextView) view.findViewById(R.id.about_team);
        TextView tvLicense = (TextView) view.findViewById(R.id.about_license);


        tvMot.setText(getString(R.string.eplan));


        tvTeam.setText(getString(R.string.programmers));

        tvLicense.setText(getString(R.string.licence));
        return view;
    }
}
