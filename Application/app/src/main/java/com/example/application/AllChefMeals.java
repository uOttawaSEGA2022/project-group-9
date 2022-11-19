package com.example.application;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import java.sql.DataTruncation;
import java.util.List;

public class AllChefMeals extends MainActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chef_meals);

        DatabaseServices databaseServices = new DatabaseServices();
        databaseServices.getCurrentChef();

        Intent intent = getIntent();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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

            mealTemplate.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View currentMealTemplateView) {
                    addDeleteDialog(currentMealTemplateView, databaseServices, meal);
                    return true;
                }
            });

            allChefMeals.addView(mealTemplate);
        }
    }

    public void addDeleteDialog(View currentMealTemplateView, DatabaseServices databaseServices, Meal meal){
        TextView isOfferedMealTextView = currentMealTemplateView.findViewById(R.id.mealIsOfferedTextView);
        TextView mealNameTextView = currentMealTemplateView.findViewById(R.id.mealName);
        if (!String.valueOf(isOfferedMealTextView.getText()).equals("Offered")){
            LayoutInflater inflater = LayoutInflater.from(AllChefMeals.this);
            View popupView = inflater.inflate(R.layout.delete_meal_popup_window, null);

            final AlertDialog alertDialog = new AlertDialog.Builder(AllChefMeals.this, com.google.android.material.R.style.Base_Theme_AppCompat_Dialog_Alert).create();
            alertDialog.setView(popupView);

            TextView dialogTitleTextView = popupView.findViewById(R.id.deleteMealPopupTitle);
            dialogTitleTextView.setText("Are you sure you want to remove " + String.valueOf(mealNameTextView.getText()) + " from your menu?");

            Button popupCancelButton = popupView.findViewById(R.id.deleteMealCancelBtn);
            Button popupDeleteButton = popupView.findViewById(R.id.deleteMealDelteBtn);

            popupCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            popupDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout allChefMeals = (LinearLayout) currentMealTemplateView.getParent();
                    allChefMeals.removeView(currentMealTemplateView);

                    // This is where the meal gets deleted from the database
                    // You can have different approaches to this problem:
                    // 1: You can unpack the meal object into the different strings and lists it contains
                    // Then you can search for the element in the database that matches that meal and delete that element from the db
                    // 2: A different approach (I'm not sure it works, I'll explain why in a bit) is to get all the meals that are currently in the linear layout
                    // Unpack them all and override the whole meal section of the database
                    // This may not work since, in this code block, the methods only see one meal at a time, notice how all of this is in a for loop
                    // So they may not see the rest of the meal templates that are in the linear layout with the current meal template
                    // And this approach is also not efficient, but may work, so do with it as you wish
                    databaseServices.removeMeal(meal);

                    alertDialog.dismiss();
                }
            });

            alertDialog.show();
            alertDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        else{
            Toast.makeText(getApplicationContext(), "This meal is currently offered, cannot remove from menu", Toast.LENGTH_LONG).show();
        }
    }

}
