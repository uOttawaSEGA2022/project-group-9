package com.example.application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class CustomerSearchForMealsScreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_search_meal);

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
                searchResultsTextView.setText("Here are the results:");
                mealSearchResultsLinearLayout.removeAllViews();
                String searchQuery = String.valueOf(searchBarEditText.getText());
                List<Meal> mealSearchResults = databaseServices.viewSpecifiedMeals(searchQuery);

                if (mealSearchResults.size() == 0){
                    searchResultsTextView.setText("No results available");
                }
                else{
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
                                // Add logic to go to specific order here
                            }
                        });

                        mealSearchResultsLinearLayout.addView(mealTemplate);
                    }
                }
            }
        });
    }
}