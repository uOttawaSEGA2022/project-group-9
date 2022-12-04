package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;

public class AllChefMeals extends MainActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chef_meals);

        DatabaseServices databaseServices = new DatabaseServices();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        LinearLayout allChefMeals = findViewById(R.id.allChefMealsLinearLayout);
        Button modifyOfferedMealsBtn = findViewById(R.id.modifyOfferedMealsBtn);
        Button addMeal = findViewById(R.id.addMealBtn);


        databaseServices.displayChefMeals(allChefMeals, AllChefMeals.this, "allChefMeals");

        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddMeal = new Intent(getApplicationContext(), AddOrEditChefMeal.class);
                goToAddMeal.putExtra("Editing or Adding", "Adding");
                startActivity(goToAddMeal);
            }
        });

        modifyOfferedMealsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToModifyOfferedMeals = new Intent(getApplicationContext(), ModifyOfferedMeals.class);
                startActivity(goToModifyOfferedMeals);
            }
        });

        /*
         * What I want to do here is have a list of meals grabbed from the database
         * I will list every meal with kind of a small star or something indicating it's offered
         * I will have 2 buttons to add a meal to the menu and the other to modify the offered meals list
         * */

        /*
         * Step 1: Grab all the meals from the database
         * Step 2: Create a template in a list view, and for each meal, modify that template with the corresponding values and display them
         * Step 3: Create the buttons and connect them to the 2 additional activities
         * Step 4: Create the add a meal activity, in which the chef can add a meal with all the required info
         * Step 5: Create an offered meals activity, in which the chef can select which meals he wants to offer from his menu
         * */
    }

}
