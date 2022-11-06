package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class E3AdminLoggedInScreen extends MainActivity {

    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e3_admin_logged_in_screen);


        Button suspenseChefUntilSelectedDateButton = (Button) findViewById(R.id.suspenseChefUntilSelectedDateButton);
        Button suspenseChefPermanently = (Button) findViewById(R.id.suspenseChefPermanentlyButton);
        Button logOff = (Button) findViewById(R.id.logOffButton);
        EditText datePicker = findViewById(R.id.datePickerText);
        TextView errorMessages = findViewById(R.id.errorMessageText);

        String userEmail = "";
        //Getting Data from loggining
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userEmail = extras.getString("email");
        }

        final String EMAIL = userEmail;

        Log.d("testing",EMAIL);

        authenticator authenticatorObject = new authenticator();



        //Admin needs to be able to look at complaints



        logOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);            }
        });

        suspenseChefUntilSelectedDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Makes sure the log in is good
                if (authenticatorObject.validateSuspensionDateFormat(datePicker,errorMessages)) {
                    //Set Chef Suspensed Date from datePicker
                    datePicker.getText().toString();
                    //IDK how to set the data in the data base
                }
        }

        });

    }

}