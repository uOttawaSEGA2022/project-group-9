package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.HashMap;
import java.util.Map;

public class AddOrEditChefMeal extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_or_edit_chef_meal);

        Intent intent = getIntent();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        LayoutInflater inflater = LayoutInflater.from(this);

        TextView addOrEditMealTitle = findViewById(R.id.addOrEditChefMealTitle);
        EditText mealName = findViewById(R.id.mealNameEditText);
        EditText mealType = findViewById(R.id.mealTypeEditText);
        EditText mealCuisine = findViewById(R.id.mealCuisineEditText);
        EditText mealIngredientsEditText = findViewById(R.id.mealIngredientsEditText);
        LinearLayout mealIngredientsList = findViewById(R.id.mealIngredientsLinearLayout);
        EditText mealAllergensEditText = findViewById(R.id.mealAllergensEditText);
        LinearLayout mealAllergensList = findViewById(R.id.mealAllergensLinearLayout);
        EditText mealPrice = findViewById(R.id.mealPriceEditText);
        EditText mealDescription = findViewById(R.id.mealDescriptionEditText);
        TextView errorMessages = findViewById(R.id.addOrEditMealErrorMessagesTextView);

        Button finishAddOrEditMealButton = (Button) findViewById(R.id.addOrEditMealBtn);

        Meal meal = (Meal) intent.getSerializableExtra("Meal");

        String tempEditingOrAddingAMeal = "Adding";
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            tempEditingOrAddingAMeal = (String) bundle.get("Editing or Adding");
        }

        final String editingOrAddingAMeal = tempEditingOrAddingAMeal;

        if (meal != null && editingOrAddingAMeal.equals("Editing")){
            addOrEditMealTitle.setText("Edit Meal");

            finishAddOrEditMealButton.setText("Finish Editing");

            mealName.setText(meal.getName());
            mealType.setText(meal.getType());
            mealCuisine.setText(meal.getCuisine());

            addIngredientAllergenToLinearLayout(meal, inflater, mealIngredientsList, "Ingredient");

            addIngredientAllergenToLinearLayout(meal, inflater, mealAllergensList, "Allergen");

            mealPrice.setText(meal.getPrice());
            mealDescription.setText(meal.getDescription());
        }

        mealIngredientsEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Log.d("HelloThereBro", "onKey: ");
                    String ingredientName = String.valueOf(mealIngredientsEditText.getText());
                    if (!ingredientName.strip().equals("")){
                        addSingleIngredientOrAllergenToLinearLayout(ingredientName, inflater, mealIngredientsList);
                    }
                    mealIngredientsEditText.setText("");
                    return true;
                }
                return false;
            }
        });

        mealAllergensEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String allergenName = String.valueOf(mealAllergensEditText.getText());
                    if (!allergenName.strip().equals("")){
                        addSingleIngredientOrAllergenToLinearLayout(allergenName, inflater, mealAllergensList);
                    }
                    mealAllergensEditText.setText("");
                    return true;
                }
                return false;
            }
        });


        finishAddOrEditMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String finalMealName = String.valueOf(mealName.getText());
                String finalMealType = String.valueOf(mealType.getText());
                String finalMealCuisine = String.valueOf(mealCuisine.getText());
                Map<String, String> finalMealingredients = getAllIngredientsOrAllergens(mealIngredientsList);
                Map<String, String> finalMealallergens = getAllIngredientsOrAllergens(mealAllergensList);
                String finalMealPrice = String.valueOf(mealPrice.getText());
                String finalMealDescription = String.valueOf(mealDescription.getText());

                AuthenticatorServices authenticatorObject = new AuthenticatorServices();

                String inputValidationMessage = authenticatorObject.validateMealNameTypeCuisinePrice(finalMealName, finalMealType, finalMealCuisine, finalMealPrice);

                if (!inputValidationMessage.equals("Valid")){
                    errorMessages.setText(inputValidationMessage);
                }
                else{
                    // Important note to whoever is implementing database code: There's several things to be done here:
                    // You need to look into how you will figure out which cook is modifying this email, either by making use of the cook variable from the meal
                    // (If you're using the cook variable, you need to make sure it's being updated (or initialized) correctly because I didn't implement that
                    // Or by using the authentication database method fAuth.getCurrentUser().getUID() or something like that
                    // Once you know what cook is working with the app, you need to upload the Meal object instance to the database, which cannot be done as it
                    // My code will send a Meal object to the DatabaseServices method, but that method will need to unpack it and configure it in a format that is accepted by the realtime database

                    DatabaseServices databaseServices = new DatabaseServices();

                    if (editingOrAddingAMeal.equals("Editing")){
                        meal.setName(finalMealName);
                        meal.setType(finalMealType);
                        meal.setCuisine(finalMealCuisine);
                        meal.setIngredients(finalMealingredients);
                        meal.setAllergens(finalMealallergens);
                        meal.setPrice(finalMealPrice);
                        meal.setDescription(finalMealDescription);
                        // Note that the cook stays as it, change that if needed based on the database person wants to implement the way the code will recognize the current cook


                        databaseServices.updateOrAddChefMeal(meal);
                    }

                    else{
                        String currentCook = databaseServices.getCurrentChef();
                        HashMap<String, Object> mealInfo = new HashMap<>();
                        mealInfo.put("name", finalMealName);
                        mealInfo.put("type", finalMealType);
                        mealInfo.put("cuisine", finalMealCuisine);
                        mealInfo.put("ingredients", finalMealingredients);
                        mealInfo.put("allergens", finalMealallergens);
                        mealInfo.put("price", finalMealPrice);
                        mealInfo.put("description", finalMealDescription);
                        mealInfo.put("cook", currentCook);
                        mealInfo.put("isOffered", "false");

                        Meal finalMeal = new Meal(mealInfo);

                        databaseServices.updateOrAddChefMeal(finalMeal);
                    }
                    finish();
                }
            }
    });
    }

    public static void addIngredientAllergenToLinearLayout(Meal meal, LayoutInflater inflater, LinearLayout mealIngredientsOrAllergensList, String ingredientOrAllergenStringIndicator){
        Map<String, String> ingredientsOrAllergensHashMap;
        if (ingredientOrAllergenStringIndicator.equals("Ingredient")){
            ingredientsOrAllergensHashMap = meal.getIngredients();
        }
        else{
            ingredientsOrAllergensHashMap = meal.getAllergens();
        }
        for (int i = 0; i<ingredientsOrAllergensHashMap.size(); i++){
            addSingleIngredientOrAllergenToLinearLayout(ingredientsOrAllergensHashMap.get(String.valueOf(i)), inflater, mealIngredientsOrAllergensList);
        }
    }

    public static void addSingleIngredientOrAllergenToLinearLayout(String ingredientOrAllergenName, LayoutInflater inflater, LinearLayout mealIngredientsOrAllergensList){
        View ingredientAllergenTemplate = inflater.inflate(R.layout.ingredients_allergens_template, null);
        TextView ingredientAllergenNameTextView = ingredientAllergenTemplate.findViewById(R.id.ingredientAllergenName);
        ingredientAllergenNameTextView.setText(ingredientOrAllergenName);

        ImageView ingredientOrAllergenRemoveIcon = ingredientAllergenTemplate.findViewById(R.id.ingredientAllergenDeleteIcon);
        ingredientOrAllergenRemoveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mealIngredientsOrAllergensList.removeView(ingredientAllergenTemplate);
            }
        });
        mealIngredientsOrAllergensList.addView(ingredientAllergenTemplate);

    }

    public static Map<String, String> getAllIngredientsOrAllergens(LinearLayout mealIngredientsOrAllergensList){
        Map<String, String> ingredientsOrAllergensList = new HashMap<>();
        for (int i = 0; i < mealIngredientsOrAllergensList.getChildCount(); i++){
            ConstraintLayout currentIngredientOrAllergenTemplate = (ConstraintLayout) mealIngredientsOrAllergensList.getChildAt(i);
            LinearLayout currentIngredientOrAllergenLinearLayout = (LinearLayout) currentIngredientOrAllergenTemplate.getChildAt(0);
            TextView currentIngredientOrAllergenTextView = (TextView) currentIngredientOrAllergenLinearLayout.getChildAt(1);
            String currentIngredientOrAllergenName = String.valueOf(currentIngredientOrAllergenTextView.getText());
            ingredientsOrAllergensList.put(String.valueOf(i), currentIngredientOrAllergenName);
        }
        return ingredientsOrAllergensList;
    }
}