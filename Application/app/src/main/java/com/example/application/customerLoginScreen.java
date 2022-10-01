package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class customerLoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_screen);

        Button custLogOff = (Button) findViewById(R.id.logCustOut);

        custLogOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "We hope to see you again!", Toast.LENGTH_SHORT).show();
                custLogOut();
            }
        });
    }

    public void custLogOut() {
        Intent custLogger = new Intent(this, MainActivity.class);
        startActivity(custLogger);
    }
}