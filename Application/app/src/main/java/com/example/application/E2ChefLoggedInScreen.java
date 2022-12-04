package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class E2ChefLoggedInScreen extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e2_chef_logged_in_screen);

        Button logOffButton = (Button) findViewById(R.id.logOffButtonID);
        Button goToMealsButton =(Button) findViewById(R.id.goToMealsButtonID);
        Button goToOrdersButton =(Button) findViewById(R.id.goToOrdersButtonID);
        TextView chefIsSuspendedTextView = findViewById(R.id.chefIsSuspendedTextViewID);

        chefIsSuspendedTextView.setVisibility(View.INVISIBLE);

        Button goToAcceptedOrdersButton = findViewById(R.id.goToAcceptedOrdersButton);
        Button goToProfileButton = findViewById(R.id.viewProfileButton);

        DatabaseServices databaseServicesObject = new DatabaseServices();
        databaseServicesObject.isSuspendedChef(goToMealsButton, goToOrdersButton, goToAcceptedOrdersButton, goToProfileButton, chefIsSuspendedTextView);


        logOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);            }
        });
        goToMealsButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent add = new Intent(getApplicationContext(), AllChefMeals.class);
               startActivity(add);
           }
       });

        goToAcceptedOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAcceptedOrderScreen = new Intent(getApplicationContext(), ChefViewAcceptedOrdersScreen.class);
                startActivity(goToAcceptedOrderScreen);
            }
        });

        goToOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToOrdersScreen = new Intent(getApplicationContext(), ChefViewOrdersScreen.class);
                startActivity(goToOrdersScreen);
            }
        });

        goToProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToChefProfileScreen = new Intent(E2ChefLoggedInScreen.this, ChefProfileScreen.class);
                startActivity(goToChefProfileScreen);
            }
        });
    }

    public void checkIfSuspended(boolean isLoggedInChefSuspended, Button goToMealsButton, Button goToOrdersButton, Button goToAcceptedOrdersButton, Button goToProfileButton,  TextView chefIsSuspendedTextView){
        if (isLoggedInChefSuspended) {
            goToMealsButton.setVisibility(View.INVISIBLE);
            goToOrdersButton.setVisibility(View.INVISIBLE);
            goToAcceptedOrdersButton.setVisibility(View.INVISIBLE);
            goToProfileButton.setVisibility(View.INVISIBLE);
            chefIsSuspendedTextView.setVisibility(View.VISIBLE);

        }

    }
}
