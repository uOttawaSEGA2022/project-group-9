package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class E2ChefLoggedInScreen extends MainActivity {
    boolean isLoggedInChefSuspensed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e2_chef_logged_in_screen);

        Button logOff = (Button) findViewById(R.id.logOffButton);
        Button goToMeals =(Button) findViewById(R.id.goToMeals);
        TextView chefIsSuspensedTextView = findViewById(R.id.chefIsSuspendedTextViewID);

        Intent intent=getIntent();
        String email=intent.getStringExtra("Email");

        DatabaseServices databaseServicesObject = new DatabaseServices();
        isLoggedInChefSuspensed = databaseServicesObject.isSuspendedChef(email);

        if (isLoggedInChefSuspensed) {
            goToMeals.setVisibility(View.INVISIBLE);
        }

        else {
            chefIsSuspensedTextView.setVisibility(View.INVISIBLE);
        }


        logOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);            }
        });
       goToMeals.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent add = new Intent(getApplicationContext(), AllChefMeals.class);
               add.putExtra("Email",email);
               startActivity(add);
           }
       });
    }
}
