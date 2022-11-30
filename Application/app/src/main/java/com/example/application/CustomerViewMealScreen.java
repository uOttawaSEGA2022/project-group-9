package com.example.application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import java.util.HashMap;
import java.util.Map;

public class CustomerViewMealScreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_view_selected_meal);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        DatabaseServices databaseServices = new DatabaseServices();

        Meal tempMeal = new Meal(new HashMap<>());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tempMeal = (Meal) extras.getSerializable("Meal");
        }

        final Meal meal = tempMeal;

        LayoutInflater inflater = LayoutInflater.from(this);

        TextView mealNameTextView = findViewById(R.id.mealNameTextView);
        TextView mealTypeTextView = findViewById(R.id.mealTypeTextView);
        TextView mealCuisineTextView = findViewById(R.id.mealCuisineTextView);
        TextView mealPriceTextView = findViewById(R.id.mealPriceTextView);
        TextView mealDescriptionTextView = findViewById(R.id.mealDescriptionTextView);

        LinearLayout mealIngredientsLinearLayout = findViewById(R.id.mealIngredientsLinearLayout);
        LinearLayout mealAllergensLinearLayout = findViewById(R.id.mealAllergensLinearLayout);

        EditText quantityEditText = findViewById(R.id.mealOrderQuantityEditText);

        Button placeOrderButton = findViewById(R.id.placeOrderButton);

        mealNameTextView.setText("Name: " + meal.getName());
        mealTypeTextView.setText("Type: " + meal.getType());
        mealCuisineTextView.setText("Cuisine: " + meal.getCuisine());
        mealPriceTextView.setText("Price: " + meal.getPrice());
        mealDescriptionTextView.setText("Description: " + meal.getDescription());

        addIngredientAllergenToLinearLayout(meal, inflater, mealIngredientsLinearLayout, "Ingredient");
        addIngredientAllergenToLinearLayout(meal, inflater, mealAllergensLinearLayout, "Allergen");


        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringQuantity = String.valueOf(quantityEditText.getText());

                // Important note: This is a quick way of checking the quantity is a number
                // For a better user experience, we should have a list of numbers that the user can select from
                int quantity;
                try {
                    quantity = Integer.parseInt(stringQuantity);
                }
                catch (Exception e){
                    quantity = 1;
                }

                databaseServices.placeOrder(meal, quantity);
                Toast.makeText(CustomerViewMealScreen.this, "Placed order with quantity " + quantity, Toast.LENGTH_SHORT).show();

            }
        });


    }



    public static void addIngredientAllergenToLinearLayout(Meal meal, LayoutInflater inflater, LinearLayout mealIngredientsOrAllergensLinearLayout, String ingredientOrAllergenStringIndicator){
        Map<String, String> ingredientsOrAllergensHashMap;
        if (ingredientOrAllergenStringIndicator.equals("Ingredient")){
            ingredientsOrAllergensHashMap = meal.getIngredients();
        }
        else{
            ingredientsOrAllergensHashMap = meal.getAllergens();
        }
        for (int i = 0; i<ingredientsOrAllergensHashMap.size(); i++){
            addSingleIngredientOrAllergenToLinearLayout(ingredientsOrAllergensHashMap.get(String.valueOf(i)), inflater, mealIngredientsOrAllergensLinearLayout);
        }
    }

    public static void addSingleIngredientOrAllergenToLinearLayout(String ingredientOrAllergenName, LayoutInflater inflater, LinearLayout mealIngredientsOrAllergensLinearLayout){
        View ingredientAllergenTemplate = inflater.inflate(R.layout.ingredients_allergens_template, null);
        TextView ingredientAllergenNameTextView = ingredientAllergenTemplate.findViewById(R.id.ingredientAllergenName);
        ingredientAllergenNameTextView.setText(ingredientOrAllergenName);

        ImageView ingredientOrAllergenRemoveIcon = ingredientAllergenTemplate.findViewById(R.id.ingredientAllergenDeleteIcon);
        ingredientOrAllergenRemoveIcon.setVisibility(View.INVISIBLE);
        mealIngredientsOrAllergensLinearLayout.addView(ingredientAllergenTemplate);

    }
}