package com.example.application;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;

public class chefLoginScreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_login_screen);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Button chefLogOff = (Button) findViewById(R.id.logChefOut);

        chefLogOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Goodbye Chef", Toast.LENGTH_SHORT).show();
                chefLogOut();
            }
        });
    }

    public void chefLogOut() {
        Intent chefLogger = new Intent(this, MainActivity.class);
        startActivity(chefLogger);
    }

    public String[] getChefCredentials() {
        EditText editTextChefEmail = findViewById(R.id.chefEmail);
        EditText editTextChefPassword = findViewById(R.id.chefPassword);
        String chefEmail = editTextChefEmail.getText().toString();
        String chefPassword = editTextChefPassword.getText().toString();
        return new String[]{chefEmail, chefPassword};
    }

}