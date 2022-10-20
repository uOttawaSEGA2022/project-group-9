package com.example.application;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import java.util.ArrayList;

public class XLoginSignupScreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_login_signup_screen);

        //Action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Role means customer or chef, will get passed as a parameter
        String role = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //Get data for parameters
            role = extras.getString("CustomerOrChefOrAdmin");
        }
        //Finalize the String Role
        //Name ROLE is weird, is there a techanical reason?
        final String ROLE = role;

        //Set Text to screen
        TextView screenTitle = findViewById(R.id.loginSignUpScreenTitle);
        screenTitle.setText("Login or Sign Up " + ROLE +"?");

        TextView screenText = findViewById(R.id.LoginSignUpText);
        screenText.setText("Login or SignUp Screen " + ROLE);
        Button login = findViewById(R.id.loginButton);
        Button signup = findViewById(R.id.signupButton);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLoginProcess(ROLE);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignUpProcess(ROLE);
                Toast.makeText(getApplicationContext(), "You want to sign in as a " + ROLE, Toast.LENGTH_SHORT).show();
            }
        });

    }



    public void goToLoginProcess(String ROLE) {
        Intent loginActivity = new Intent(getApplicationContext(), ALoginScreen.class);
        loginActivity.putExtra("CustomerOrChefOrAdmin", ROLE);
        startActivity(loginActivity);
    }

    //Next Screen is different if the user is a chef vs client
    public void goToSignUpProcess(String ROLE) {
        //user Info in an array for easy access
        String[] userInfo;
        //If the user is customer, they need 19 data entries, chef needs 12
        if (ROLE.equals("Customer")){
            userInfo = new String[19];
        }
        else{
            userInfo = new String[12];
        }
        //Role is in the first index
        userInfo[0] = ROLE;

        //For Chef Sign up
            // Send to activity B1D1 with string array with first value as the ROLE, with key "User Info"
            Intent b1d1SignUpNameCredentials = new Intent(getApplicationContext(), B1D1SignUpNameCredentials.class);
            b1d1SignUpNameCredentials.putExtra("User Info", userInfo);
            startActivity(b1d1SignUpNameCredentials);

<<<<<<< HEAD
=======


>>>>>>> 547f1aa4b9e2d7f890152cba1d41eff6bdee233e

        // At this point, the string array has 1 element, at index 0, with the role of the user
        // We can generate the ID here, and input it before the role, then shift all indices
        // in the rest of designs by 1

        //Yash's Debugging Code Checking If Array Is Valid
        Log.i("CHECKING INDICES", "Index 0 is updated!");
        System.out.println(userInfo[0]);
    }


}