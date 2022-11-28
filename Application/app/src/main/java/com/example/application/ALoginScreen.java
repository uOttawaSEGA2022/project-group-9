package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

public class ALoginScreen extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_login_screen);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String role = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            role = extras.getString("CustomerOrChefOrAdmin");
        }
        final String ROLE = role;

        Log.d("Testing", ROLE);

        TextView titleTextViewTextView = findViewById(R.id.TitleTextViewID);
        titleTextViewTextView.setText("Welcome Back, " + ROLE);


        Button signInButton = (Button) findViewById(R.id.SignInButtonID);
        EditText emailEditText = findViewById(R.id.EmailEditTextID);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // THIS IS FOR CUSTOMER DEVELOPMENT PURPOSES, REMOVE BEFORE SUBMITTING ######################
//                Intent goToCustomerLoggedInScreen = new Intent(ALoginScreen.this, E1CustomerLoggedInScreen.class);
//                startActivity(goToCustomerLoggedInScreen);
//                Intent test = new Intent(ALoginScreen.this, testingClass.class);
//                startActivity(test);
                // ######################################################################################
                TextView errorMessagesTextView = findViewById(R.id.ErrorMessagesTextViewID);
                EditText emailEditText = findViewById(R.id.EmailEditTextID);
                EditText passwordEditText = findViewById(R.id.PasswordEditTextID);
                AuthenticatorServices authenticatorObject = new AuthenticatorServices();
                boolean signInStatus = authenticatorObject.checkCredentials(emailEditText, passwordEditText, errorMessagesTextView);


                if (signInStatus){

                    DatabaseServices databaseServices = new DatabaseServices();
                    String email = emailEditText.getText().toString();
                    String password = passwordEditText.getText().toString();

                    databaseServices.signInUser(getApplicationContext(), ALoginScreen.this, email, password, ROLE);

                }
            }
        });
    }
}