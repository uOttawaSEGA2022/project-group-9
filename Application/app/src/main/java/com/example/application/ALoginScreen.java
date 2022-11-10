package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ALoginScreen extends MainActivity {
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference dataRef;
    EditText emailText;

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

        TextView screenTitle = findViewById(R.id.loginScreenTitle);
        screenTitle.setText("Welcome Back, " + ROLE);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Chef");


        Button signInButton = (Button) findViewById(R.id.SignIn);
        EditText emailText = findViewById(R.id.Email);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView EmailPasswordErrorMessages = findViewById(R.id.EmailPasswordErrorMessages);
                EditText editTextEmail = findViewById(R.id.Email);
                EditText editTextPassword = findViewById(R.id.Password);
                authenticator authenticatorObject = new authenticator();
                boolean signInStatus = authenticatorObject.checkCredentials(editTextEmail, editTextPassword, EmailPasswordErrorMessages);


                if (signInStatus){

                    DatabaseServices databaseServices = new DatabaseServices();
                    String email = editTextEmail.getText().toString();
                    String password = editTextPassword.getText().toString();

                    databaseServices.signInUser(getApplicationContext(), ALoginScreen.this, email, password, ROLE);

                }
            }
        });
    }
}