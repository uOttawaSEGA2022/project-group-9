package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class chefLoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_login_screen);

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

}