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

import com.google.android.gms.vision.text.Line;
import com.google.android.gms.vision.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddOrEditChefMeal extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_or_edit_chef_meal);

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
        LinearLayout mealIngredientsList = findViewById(R.id.mealIngredientsList);
        EditText mealAllergensEditText = findViewById(R.id.mealAllergensEditText);
        LinearLayout mealAllergensList = findViewById(R.id.mealAllergensList);
        EditText mealPrice = findViewById(R.id.mealPriceEditText);
        EditText mealDescription = findViewById(R.id.mealDescriptionEditText);
        TextView errorMessages = findViewById(R.id.addOrEditMealErrorMessagesTextView);

        Button finishAddOrEditMealButton = (Button) findViewById(R.id.addOrEditMealBtn);

        Intent intent = getIntent();
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
                    String ingredientName = String.valueOf(mealIngredientsEditText.getText());
                    if (!ingredientName.strip().equals("")){
                        addSingleIngredientOrAllergenToLinearLayout(ingredientName, inflater, mealIngredientsList);
                    }
                }
                return true;
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
                }
                return true;
            }
        });


        finishAddOrEditMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String finalMealName = String.valueOf(mealName.getText());
                String finalMealType = String.valueOf(mealType.getText());
                String finalMealCuisine = String.valueOf(mealCuisine.getText());
                List<String> finalMealingredients = getAllIngredientsOrAllergens(mealIngredientsList);
                List<String> finalMealallergens = getAllIngredientsOrAllergens(mealAllergensList);
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


                        databaseServices.updateOrAddChefMeal(meal, editingOrAddingAMeal);
                    }

                    else{
                        String currentCook = databaseServices.getCurrentChef();
                        HashMap<String, Object> mealInfo = new HashMap<>();
                        mealInfo.put("Name", finalMealName);
                        mealInfo.put("Type", finalMealType);
                        mealInfo.put("Cuisine", finalMealCuisine);
                        mealInfo.put("Ingredients", finalMealingredients);
                        mealInfo.put("Allergens", finalMealallergens);
                        mealInfo.put("Price", finalMealPrice);
                        mealInfo.put("Description", finalMealDescription);
                        mealInfo.put("Cook", currentCook);
                        mealInfo.put("IsOffered", false);

                        Meal finalMeal = new Meal(mealInfo);

                        databaseServices.updateOrAddChefMeal(finalMeal, editingOrAddingAMeal);
                    }
                    Intent goToAllChefMeals = new Intent(getApplicationContext(), AllChefMeals.class);
                    startActivity(goToAllChefMeals);
                }
            }
    });
    }

    public static void addIngredientAllergenToLinearLayout(Meal meal, LayoutInflater inflater, LinearLayout mealIngredientsOrAllergensList, String ingredientOrAllergenStringIndicator){
        List<String> ingredientsOrAllergensList;
        if (ingredientOrAllergenStringIndicator.equals("Ingredient")){
            ingredientsOrAllergensList = meal.getIngredients();
        }
        else{
            ingredientsOrAllergensList = meal.getAllergens();
        }
        for (String ingredientOrAllergenName: ingredientsOrAllergensList){
            addSingleIngredientOrAllergenToLinearLayout(ingredientOrAllergenName, inflater, mealIngredientsOrAllergensList);
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

    public static List<String> getAllIngredientsOrAllergens(LinearLayout mealIngredientsOrAllergensList){
        List<String> ingredientsOrAllergensList = new ArrayList<String>();
        for (int i = 0; i < mealIngredientsOrAllergensList.getChildCount(); i++){
            ConstraintLayout currentIngredientOrAllergenTemplate = (ConstraintLayout) mealIngredientsOrAllergensList.getChildAt(i);
            LinearLayout currentIngredientOrAllergenLinearLayout = (LinearLayout) currentIngredientOrAllergenTemplate.getChildAt(0);
            TextView currentIngredientOrAllergenTextView = (TextView) currentIngredientOrAllergenLinearLayout.getChildAt(1);
            String currentIngredientOrAllergenName = String.valueOf(currentIngredientOrAllergenTextView.getText());
            ingredientsOrAllergensList.add(currentIngredientOrAllergenName);
        }
        return ingredientsOrAllergensList;
    }
}