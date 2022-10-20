package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import java.util.ArrayList;

public class B1D1SignUpNameCredentials extends MainActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b1d1_signup_chef_name_credentials);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String[] tempUserInfo = {};
        Bundle extras = getIntent().getExtras();
        //use only if some parameters have been passed
        if (extras != null) {
            tempUserInfo = extras.getStringArray("User Info");
        }

        //get data from parameters and use it
        String[] userInfo = tempUserInfo;
        final String ROLE = userInfo[0];
        TextView screenTitle = findViewById(R.id.signUpNameCredentialsTitle);

        //Set text to Chef/ Customer Sign Up
        if (!ROLE.equals(screenTitle.getText().toString())){
            screenTitle.setText(ROLE + " Sign Up");
        }

        //Creates Links
        EditText firstName = findViewById(R.id.firstNameSignUp);
        EditText lastName = findViewById(R.id.lastNameSignUp);
        EditText email = findViewById(R.id.emailSignUp);
        EditText password = findViewById(R.id.passwordSignUp);
        EditText reenterPassword = findViewById(R.id.reenterPasswordSignUp);

        //EditsTexts for this screen
        EditText[] editTexts = {firstName, lastName, email, password, reenterPassword};

        TextView errorMessages = findViewById(R.id.signUpNameCredentialsErrorMessages);

        Button btnContinue = findViewById(R.id.signUpNameCredentialsBtn);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To authenticate text fields
                authenticator authenticatorObject = new authenticator();
                //To keep track of the textInputs are fine
                boolean validateTextInputs = authenticatorObject.checkNameCredentialsInputs(editTexts, errorMessages);
                if (validateTextInputs){
                    //Add data to the array
                    addNameCredentialsToArrayList(firstName, lastName, email, password, userInfo);
                    Intent b2d2SignUpAddress = new Intent(getApplicationContext(), B2D2SignUpAddress.class);
                    //Pass a Source and User Info
                    b2d2SignUpAddress.putExtra("User Info", userInfo);
                    b2d2SignUpAddress.putExtra("Source", "B1D1");
                    startActivity(b2d2SignUpAddress);
                }
            }
        });
    }

    // After this method, the string array should have 5 elements:
    // role, first name, last name, email, password
    // Indices 0 through 4
    public static void addNameCredentialsToArrayList(EditText firstName, EditText lastName,
                                          EditText email, EditText password, String[] userInfo){

        userInfo[1] = firstName.getText().toString();
        userInfo[2] = lastName.getText().toString();
        userInfo[3] = email.getText().toString();
        userInfo[4] = password.getText().toString();
    }

}
