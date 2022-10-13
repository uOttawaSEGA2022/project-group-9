package com.example.application;

import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
            role = extras.getString("CustomerOrChef");
        }
        final String ROLE = role;

        TextView screenTitle = findViewById(R.id.loginScreenTitle);
        screenTitle.setText("Welcome Back, " + ROLE);

        Button chefSignInButton = (Button) findViewById(R.id.chefSignIn);

        chefSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView chefEmailPasswordErrorMessages = findViewById(R.id.chefEmailPasswordErrorMessages);
                EditText editTextChefEmail = findViewById(R.id.chefEmail);
                EditText editTextChefPassword = findViewById(R.id.chefPassword);
                authenticator authenticatorObject = new authenticator();
                boolean signInStatus = authenticatorObject.checkCredentials(editTextChefEmail, editTextChefPassword, chefEmailPasswordErrorMessages);
                if (signInStatus){
                    Toast.makeText(getApplicationContext(), "Sign In Successful", Toast.LENGTH_SHORT).show();
                    if (ROLE.equals("Customer")){
                        goToCustomerLoggedInScreen();
                    }
                    else{
                        goToChefLoggedInScreen();
                    }
                }
            }
        });
    }

    public void goToCustomerLoggedInScreen() {
        Intent mainActivity = new Intent(this, E1CustomerLoggedInScreen.class);
        startActivity(mainActivity);
    }

    public void goToChefLoggedInScreen() {
        Intent mainActivity = new Intent(this, E2ChefLoggedInScreen.class);
        startActivity(mainActivity);
    }
}