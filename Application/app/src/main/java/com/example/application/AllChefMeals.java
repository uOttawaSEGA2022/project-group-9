package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AllChefMeals extends MainActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chef_meals);

        LinearLayout allChefMeals = findViewById(R.id.allChefMealsLinearLayout);
        Button modifyOfferedMealsBtn = findViewById(R.id.modifyOfferedMealsBtn);
        Button addMeal = findViewById(R.id.addMealBtn);

        displayChefMeals(allChefMeals);

        addMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddMeal = new Intent(getApplicationContext(), AddOrEditChefMeal.class);
                goToAddMeal.putExtra("Editing or Adding", "Adding");
                startActivity(goToAddMeal);
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

    public void displayChefMeals(LinearLayout allChefMeals){
        DatabaseServices databaseServices = new DatabaseServices();

        List<Meal> currentChefMeals = databaseServices.getCurrentChefMeals();

        LayoutInflater inflater = LayoutInflater.from(this);

        for (Meal meal: currentChefMeals){
            String mealName = meal.getName();
            String mealCuisine = meal.getCuisine();
            String mealType = meal.getType();
            boolean mealIsOffered = meal.getIsOffered();

            View mealTemplate = inflater.inflate(R.layout.meal_template, null);

            TextView mealNameTextView = mealTemplate.findViewById(R.id.mealName);
            TextView mealCuisineTextView = mealTemplate.findViewById(R.id.mealCuisine);
            TextView mealTypeTextView = mealTemplate.findViewById(R.id.mealType);

            mealNameTextView.setText(mealName);
            mealCuisineTextView.setText(mealCuisine);
            mealTypeTextView.setText(mealType);

            if (!mealIsOffered){
                TextView mealIsOfferedTextView = mealTemplate.findViewById(R.id.mealIsOfferedTextView);
                ImageView mealIsOfferedIcon = mealTemplate.findViewById(R.id.mealIsOffered);

                mealIsOfferedTextView.setText("Not Offered");
                mealIsOfferedIcon.setImageResource(R.drawable.not_offered_unchecked_mark);
            }

            mealTemplate.setClickable(true);
            mealTemplate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToEditChefMeal = new Intent(AllChefMeals.this, AddOrEditChefMeal.class);
                    goToEditChefMeal.putExtra("Meal", meal);
                    goToEditChefMeal.putExtra("Editing or Adding", "Editing");
                    startActivity(goToEditChefMeal);
                }
            });

            allChefMeals.addView(mealTemplate);
        }
    }
}
