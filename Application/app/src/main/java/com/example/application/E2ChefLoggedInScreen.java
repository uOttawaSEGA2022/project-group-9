package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class E2ChefLoggedInScreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e2_chef_logged_in_screen);

        Button logOff = (Button) findViewById(R.id.logOffButton);
        TextView chefSuspensed = findViewById(R.id.chefSuspensedText);

        //check to see if the chef is suspensed in the database
        if(true) {
            chefSuspensed.setVisibility(View.VISIBLE);
        }


        logOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);            }
        });

    }
}
