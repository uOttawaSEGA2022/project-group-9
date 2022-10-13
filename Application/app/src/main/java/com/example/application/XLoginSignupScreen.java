package com.example.application;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String role = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = extras.getString("CustomerOrChef");
        }
        final String ROLE = role;

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
        loginActivity.putExtra("CustomerOrChef", ROLE);
        startActivity(loginActivity);
    }

    public void goToSignUpProcess(String ROLE) {
        String[] userInfo;
        if (ROLE.equals("Customer")){
            userInfo = new String[19];
        }
        else{
            userInfo = new String[12];
        }
        userInfo[0] = ROLE;
        // Send to activity B1D1 with string array with first value as the ROLE, with key "User Info"
        Intent b1d1SignUpNameCredentials = new Intent(getApplicationContext(), B1D1SignUpNameCredentials.class);
        b1d1SignUpNameCredentials.putExtra("User Info", userInfo);
        startActivity(b1d1SignUpNameCredentials);

        // At this point, the string array has 1 element, at index 0, with the role of the user
        // We can generate the ID here, and input it before the role, then shift all indices
        // in the rest of designs by 1
    }


}