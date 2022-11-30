package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class E1CustomerLoggedInScreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e1_customer_logged_in_screen);

        Button logOff = (Button) findViewById(R.id.logOffButton);
        Button searchForMeals = findViewById(R.id.searchForMealsButton);
        Button viewOrderHistory = findViewById(R.id.viewOrderHistoryButton);

        /*
        String userEmail = "";
        //Getting Data from loggining
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userEmail = extras.getString("email");
        }

        final String EMAIL = userEmail;

        */

        searchForMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSearchMealsActivity = new Intent(E1CustomerLoggedInScreen.this, CustomerSearchForMealsScreen.class);
                startActivity(goToSearchMealsActivity);
            }
        });

        viewOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Yash and Rudra's part here
                Intent orderHistoryScreen = new Intent(getApplicationContext(), CustomerOrderHistoryScreen.class);
                startActivity(orderHistoryScreen);
            }
        });

        logOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
            }
        });

    }
}