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

        Button logOffButton = (Button) findViewById(R.id.logOffButtonID);
        Button goToMealsButton =(Button) findViewById(R.id.goToMealsButtonID);
        Button goToOrdersButton =(Button) findViewById(R.id.goToOrdersButtonID);
        TextView chefIsSuspendedTextView = findViewById(R.id.chefIsSuspendedTextViewID);

        Intent intent=getIntent();
        String email=intent.getStringExtra("Email");

        DatabaseServices databaseServicesObject = new DatabaseServices();
        isLoggedInChefSuspensed = databaseServicesObject.isSuspendedChef(email);

        if (isLoggedInChefSuspensed) {
            goToMealsButton.setVisibility(View.INVISIBLE);
            goToOrdersButton.setVisibility(View.INVISIBLE);

        }

        else {
            chefIsSuspendedTextView.setVisibility(View.INVISIBLE);
        }


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
               add.putExtra("Email",email);
               startActivity(add);
           }
       });

//        goToOrdersButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent add = new Intent(getApplicationContext(), ChefViewOrderScreen.class);
//                add.putExtra("Email",email);
//                startActivity(add);
//            }
//        });
    }
}
