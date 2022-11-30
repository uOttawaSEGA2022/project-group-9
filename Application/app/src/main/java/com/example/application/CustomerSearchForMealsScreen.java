package com.example.application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import java.util.List;

public class CustomerSearchForMealsScreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_search_meal);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        DatabaseServices databaseServices = new DatabaseServices();

        LayoutInflater inflater = LayoutInflater.from(this);

        EditText searchBarEditText = findViewById(R.id.searchBarEditText);
        ImageView searchBarIcon = findViewById(R.id.searchBarIcon);
        TextView searchResultsTextView = findViewById(R.id.searchResultsMessagesTextView);
        LinearLayout mealSearchResultsLinearLayout = findViewById(R.id.mealSearchResultsLinearLayout);


        searchBarIcon.setClickable(true);
        searchBarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchResultsTextView.setText("Loading...");
                mealSearchResultsLinearLayout.removeAllViews();
                String searchQuery = String.valueOf(searchBarEditText.getText());
                databaseServices.viewSpecifiedMeals(searchQuery, searchResultsTextView, inflater, mealSearchResultsLinearLayout, CustomerSearchForMealsScreen.this);
            }
        });

        searchBarEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    searchResultsTextView.setText("Loading...");
                    mealSearchResultsLinearLayout.removeAllViews();
                    String searchQuery = String.valueOf(searchBarEditText.getText());
                    databaseServices.viewSpecifiedMeals(searchQuery, searchResultsTextView, inflater, mealSearchResultsLinearLayout, CustomerSearchForMealsScreen.this);
                }

                return true;
            }
        });
    }

    public void displaySearchResults(List<Meal> mealSearchResults, TextView searchResultsTextView, LayoutInflater inflater, LinearLayout mealSearchResultsLinearLayout, Context context){
        if (mealSearchResults.size() == 0){
            searchResultsTextView.setText("No results available");
        }
        else{
            searchResultsTextView.setText("Here are the results");
            for (Meal meal : mealSearchResults){
                String mealName = meal.getName();
                String mealType = meal.getType();
                String mealCuisine = meal.getCuisine();
                String mealPrice = meal.getPrice();

                View mealTemplate = inflater.inflate(R.layout.customer_meal_template, null);

                TextView mealTemplateNameTextView = mealTemplate.findViewById(R.id.mealName);
                TextView mealTemplateTypeTextView = mealTemplate.findViewById(R.id.mealType);
                TextView mealTemplateCuisineTextView = mealTemplate.findViewById(R.id.mealCuisine);
                TextView mealTemplatePriceTextView = mealTemplate.findViewById(R.id.mealPrice);

                mealTemplateNameTextView.setText(mealName);
                mealTemplateTypeTextView.setText(mealType);
                mealTemplateCuisineTextView.setText(mealCuisine);
                mealTemplatePriceTextView.setText(mealPrice + "$");

                mealTemplate.setClickable(true);

                mealTemplate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent goToViewSelectedMealActivity = new Intent(context, CustomerViewMealScreen.class);
                        goToViewSelectedMealActivity.putExtra("Meal", meal);
                        context.startActivity(goToViewSelectedMealActivity);
                    }
                });

                mealSearchResultsLinearLayout.addView(mealTemplate);
            }
        }
    }
}