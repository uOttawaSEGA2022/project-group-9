package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import java.util.List;

public class ModifyOfferedMeals extends MainActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_offered_meals);

        Intent intent = getIntent();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        LinearLayout allChefMeals = findViewById(R.id.modifyOfferedMealsAllChefMealsLinearLayout);

        displayChefMealsForModifyingOfferedMeals(allChefMeals);

        Button finishModifyingButton = findViewById(R.id.finishModifyingOfferedMealsBtn);

        finishModifyingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAllChefMeals = new Intent(getApplicationContext(), AllChefMeals.class);
                startActivity(goToAllChefMeals);
            }
        });
    }

    public void displayChefMealsForModifyingOfferedMeals(LinearLayout allChefMeals){
        DatabaseServices databaseServices = new DatabaseServices();

        List<Meal> currentChefMeals = databaseServices.getCurrentChefMeals();

        LayoutInflater inflater = LayoutInflater.from(this);

        for (Meal meal: currentChefMeals) {
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

            if (!mealIsOffered) {
                TextView mealIsOfferedTextView = mealTemplate.findViewById(R.id.mealIsOfferedTextView);
                ImageView mealIsOfferedIcon = mealTemplate.findViewById(R.id.mealIsOffered);

                mealIsOfferedTextView.setText("Not Offered");
                mealIsOfferedIcon.setImageResource(R.drawable.not_offered_unchecked_mark);
            }

            mealTemplate.setClickable(true);
            mealTemplate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView mealIsOfferedTextView = mealTemplate.findViewById(R.id.mealIsOfferedTextView);
                    ImageView mealIsOfferedIcon = mealTemplate.findViewById(R.id.mealIsOffered);
                    boolean mealIsOffered = meal.getIsOffered();
                    if (mealIsOffered){
                        meal.setOffered(false);
                        mealIsOfferedTextView.setText("Not Offered");
                        mealIsOfferedIcon.setImageResource(R.drawable.not_offered_unchecked_mark);
                    }
                    else{
                        meal.setOffered(true);
                        mealIsOfferedTextView.setText("Offered");
                        mealIsOfferedIcon.setImageResource(R.drawable.offered_checkmark);
                    }

                    // Editing the current meal in the database, but only changing the offered status
                    // There's an alternative solution to this since realtime database may crash if the cook spams clicking
                    // We can get the offered status of each meal when the finish button is clicked
                    // The only problem is that the meal object is not related in any way to the UI elements
                    // The UI elements have access to the meal object at creation only and the meal never has access to the UI elements
                    // So getting meals out of the UI elements will be a really challenging task which will require heavy modification to the whole system
                    databaseServices.updateOrAddChefMeal(meal, "Editing");
                }
            });

            allChefMeals.addView(mealTemplate);
        }
    }
}
