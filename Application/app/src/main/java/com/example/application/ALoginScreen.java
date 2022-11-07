package com.example.application;
import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

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
                    fAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                if (ROLE.equals("Customer")) {
//                                    dataRef = database.getReference("Customer");
//                                    dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                            if (snapshot.hasChild(Objects.requireNonNull(fAuth.getCurrentUser()).getUid())) {
//                                                Toast.makeText(ALoginScreen.this, "Login successfull!", Toast.LENGTH_SHORT).show();
//                                                goToCustomerLoggedInScreen();
//                                            } else {
//                                                Toast.makeText(ALoginScreen.this, "Username or Password Incorrect!", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError error) {
//
//                                        }
//                                    });

                                    goToCustomerLoggedInScreen();
                                } else if (ROLE.equals("Chef")) {
//                                    dataRef = database.getReference("Chef");
//                                    dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                            if (snapshot.hasChild(Objects.requireNonNull(fAuth.getCurrentUser()).getUid())) {
//                                                Toast.makeText(ALoginScreen.this, "Login successfull!", Toast.LENGTH_SHORT).show();
//                                                goToChefLoggedInScreen();
//                                            } else {
//                                                Toast.makeText(ALoginScreen.this, "Username or Password Incorrect!", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError error) {
//
//                                        }
//                                    });
                                    goToChefLoggedInScreen();
                                } else {
                                    goToAdminLoggedInScreen();
                                }
                            } else {
                                Toast.makeText(ALoginScreen.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });
    }

    public void goToCustomerLoggedInScreen() {
        Intent E1CustomerLoggedInScreen = new Intent(getApplicationContext(), E1CustomerLoggedInScreen.class);
        E1CustomerLoggedInScreen.putExtra("Email", emailText.getText().toString());
        startActivity(E1CustomerLoggedInScreen);
    }

    public void goToChefLoggedInScreen() {
        Intent E2ChefLoggedInScreen = new Intent(getApplicationContext(), E2ChefLoggedInScreen.class);
        E2ChefLoggedInScreen.putExtra("Email", emailText.getText().toString());
        startActivity(E2ChefLoggedInScreen);
    }

    public void goToAdminLoggedInScreen() {
        Intent E3AdminLoggedInScreen = new Intent(getApplicationContext(), E3AdminLoggedInScreen.class);
        E3AdminLoggedInScreen.putExtra("Email", emailText.getText().toString());
        startActivity(E3AdminLoggedInScreen);
    }
}