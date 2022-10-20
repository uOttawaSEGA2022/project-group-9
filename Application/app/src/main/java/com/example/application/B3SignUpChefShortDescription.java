package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;

import java.util.ArrayList;

public class B3SignUpChefShortDescription extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b3_signup_chef_short_description);

        String[] tempChefInfo = {};
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tempChefInfo = extras.getStringArray("Chef Info");
        }
        String[] chefInfo = tempChefInfo;

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        EditText shortDescription = findViewById(R.id.chefSignUpShortDescription);

        Button btnContinue = findViewById(R.id.chefSignUpShortDescriptionBtnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chefInfo[10] = shortDescription.getText().toString();
                Intent b4SignUpChefVoidCheque = new Intent(getApplicationContext(), B4SignUpChefVoidCheque.class);
                b4SignUpChefVoidCheque.putExtra("Chef Info", chefInfo);
                startActivity(b4SignUpChefVoidCheque);

                //Yash's Debugging Code Checking If Array Is Valid
                Log.i("CHECKING INDICES", "Index 10 is updated for Chef!");
                System.out.println(chefInfo[10]);
            }
        });
    }
}
