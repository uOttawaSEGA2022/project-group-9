package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class chefLoginSignUpScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_login_sign_up_screen);

        Button login = (Button) findViewById(R.id.loginButton);
        Button signup = (Button) findViewById(R.id.signupButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letChefLogin();
                Toast.makeText(getApplicationContext(), "You want to login as a chef", Toast.LENGTH_SHORT).show();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letChefSignup();
                Toast.makeText(getApplicationContext(), "You want to sign in as a chef", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void letChefLogin() {

    }

    public void letChefSignup() {

    }
}