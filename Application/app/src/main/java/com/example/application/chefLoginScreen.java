package com.example.application;

import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class chefLoginScreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_login_screen);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Button chefSignInButton = (Button) findViewById(R.id.chefSignIn);

        chefSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int testreceivedinteger = getMainActivityInteger();
                String stringInteger = String.valueOf(testreceivedinteger);
                Toast.makeText(getApplicationContext(), stringInteger, Toast.LENGTH_SHORT).show();
                TextView chefEmailPasswordErrorMessages = findViewById(R.id.chefEmailPasswordErrorMessages);
                EditText editTextChefEmail = findViewById(R.id.chefEmail);
                EditText editTextChefPassword = findViewById(R.id.chefPassword);
                authenticator authenticatorObject = new authenticator(chefEmailPasswordErrorMessages, editTextChefEmail, editTextChefPassword);
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
//        Intent mainActivity = new Intent(this, MainActivity.class);
//        startActivity(mainActivity);
    }

    public int getMainActivityInteger(){
        MainActivity testActivity = new MainActivity();
        return testActivity.testfunction();
    }

}