package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ModifyOfferedMeals extends MainActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_offered_meals);

        Intent intent = getIntent();

        LinearLayout allChefMeals = findViewById(R.id.modifyOfferedMealsAllChefMealsLinearLayout);

        DatabaseServices databaseServices = new DatabaseServices();

        databaseServices.displayChefMeals(allChefMeals, ModifyOfferedMeals.this, "modifying");

        Button finishModifyingButton = findViewById(R.id.finishModifyingOfferedMealsBtn);

        finishModifyingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}