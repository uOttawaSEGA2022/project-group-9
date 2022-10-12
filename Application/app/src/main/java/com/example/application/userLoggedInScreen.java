package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class userLoggedInScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logged_in_screen);

        Button logOff = (Button) findViewById(R.id.logOffButton);

        logOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //customerLogin();
                Toast.makeText(getApplicationContext(), "You are logging in as a customer", Toast.LENGTH_SHORT).show();
            }
        });

    }
}