package com.team16.sopra.sopra16team16.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.team16.sopra.sopra16team16.R;

/**
 * Created by prime on 18.11.16.
 */

public class NewContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.contact_editor);

        // add Button to change layout to contact viewer
        Button confirmEditButton = (Button)findViewById(R.id.confirm_edit_button);
            confirmEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(NewContactActivity.this, ContactViewerActivity.class));
                    finish();
                }
            });

        // add Button to cancel the current (adding of new contact)/(editing of existing button)
        Button cancelButton = (Button) findViewById(R.id.cancel_edit_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewContactActivity.this, HomeActivity.class));
                finish();
            }
            });
    }

}
