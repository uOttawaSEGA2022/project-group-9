package com.example.application;

import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class customerLoginScreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_screen);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Button customerSignInButton = (Button) findViewById(R.id.customerSignIn);

        customerSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView customerEmailPasswordErrorMessages = findViewById(R.id.customerEmailPasswordErrorMessages);
                EditText editTextCustomerEmail = findViewById(R.id.customerEmail);
                EditText editTextCustomerPassword = findViewById(R.id.customerPassword);
                authenticator authenticatorObject = new authenticator(customerEmailPasswordErrorMessages, editTextCustomerEmail, editTextCustomerPassword);
                String[] credentials = authenticatorObject.getCredentials();
                boolean signInStatus = authenticatorObject.signIn(credentials);
                if (signInStatus){
                    Toast.makeText(getApplicationContext(), "Sign In Successful", Toast.LENGTH_SHORT).show();
                    goToMainActivity();
                }
            }
        });
    }

    public void goToMainActivity() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}