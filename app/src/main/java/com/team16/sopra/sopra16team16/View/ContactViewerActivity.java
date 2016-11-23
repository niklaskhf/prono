package com.team16.sopra.sopra16team16.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.team16.sopra.sopra16team16.R;

/**
 * Created by prime on 22.11.16.
 */

public class ContactViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setContentView(R.layout.contact_viewer);

        Button editButton = (Button) findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContactViewerActivity.this, NewContactActivity.class));
            }
        });

    }

}
