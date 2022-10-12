package com.example.application;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CustomerLoginSignupScreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_signup_screen);

        Button login = (Button) findViewById(R.id.loginButton);
        Button signup = (Button) findViewById(R.id.signupButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letCustomerLogin();
                Toast.makeText(getApplicationContext(), "You want to login as a customer", Toast.LENGTH_SHORT).show();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letCustomerSignup();
                Toast.makeText(getApplicationContext(), "You want to sign in as a customer", Toast.LENGTH_SHORT).show();
            }
        });

    }



    public void letCustomerLogin() {

    }

    public void letCustomerSignup() {

    }


}