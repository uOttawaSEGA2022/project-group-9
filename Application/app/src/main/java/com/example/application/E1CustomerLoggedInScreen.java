package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class E1CustomerLoggedInScreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e1_customer_logged_in_screen);

        Button logOff = (Button) findViewById(R.id.logOffButton);


        String userEmail = "";
        //Getting Data from loggining
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userEmail = extras.getString("email");
        }

        final String EMAIL = userEmail;




        logOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
            }
        });

    }
}