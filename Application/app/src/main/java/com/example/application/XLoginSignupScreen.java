package com.example.application;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class XLoginSignupScreen extends MainActivity {

    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_login_signup_screen);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

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
        //If the user is customer, they need 19 data entries, chef needs 14
        if (ROLE.equals("Customer")){
            userInfo = new String[19];
        }

        else if (ROLE.equals("Customer")) {
            userInfo = new String[3];
        }
        else{
            //added 1 more element for a boolean to see if the chef is suspensed
            //added 1 more element for length of chef suspension
            userInfo = new String[14];
        }
        //Role is in the first index
        userInfo[0] = ROLE;

        //For Chef Sign up
        if (userInfo[0].equals("Chef")) {

            // Send to activity B1D1 with string array with first value as the ROLE, with key "User Info"
            Intent b1d1SignUpNameCredentials = new Intent(getApplicationContext(), B1D1SignUpNameCredentials.class);
            b1d1SignUpNameCredentials.putExtra("User Info", userInfo);
            startActivity(b1d1SignUpNameCredentials);

        }
        if (userInfo[0].equals("Customer")) {
            // Send to activity B1D1 with string array with first value as the ROLE, with key "User Info"
            Intent b1d1SignUpNameCredentials = new Intent(getApplicationContext(), B1D1SignUpNameCredentials.class);
            b1d1SignUpNameCredentials.putExtra("User Info", userInfo);
            startActivity(b1d1SignUpNameCredentials);

        }

        if (userInfo[0].equals("Admin")) {
            userInfo[1] = "JayV@gmail.com";
            userInfo[2] = "JayVach12345#";
            String[] registerInfo = {"role", "email", "password"};


            fAuth.createUserWithEmailAndPassword(userInfo[1], userInfo[2]).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        dataRef = database.getReference("Admin").child(fAuth.getCurrentUser().getUid());

                        for (int i = 0; i < userInfo.length; i++) {
                            if (i == 0) continue;
                            Log.d("adminInfo", registerInfo[i] + " " + userInfo[i]);
                            dataRef.child(registerInfo[i]).setValue(userInfo[i]);
                        }

                    }
                }
            });
        }

        // At this point, the string array has 1 element, at index 0, with the role of the user
        // We can generate the ID here, and input it before the role, then shift all indices
        // in the rest of designs by 1

        //Yash's Debugging Code Checking If Array Is Valid
        Log.i("CHECKING INDICES", "Index 0 is updated!");
        System.out.println(userInfo[0]);

        //FINAL COMMIT MESSAGE : YASH JAIN
    }


}