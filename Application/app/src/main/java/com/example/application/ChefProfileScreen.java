package com.example.application;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

public class ChefProfileScreen extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chef_profile);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView chefNameTextView = findViewById(R.id.chefName);
        TextView chefEmailTextView = findViewById(R.id.chefEmail);
        TextView chefRatingTextView = findViewById(R.id.chefRating);
        TextView chefDescriptionTextView = findViewById(R.id.chefDescription);

        DatabaseServices databaseServices = new DatabaseServices();
        databaseServices.getChefProfileInfo(chefNameTextView, chefEmailTextView, chefRatingTextView, chefDescriptionTextView);
    }

    public void displayChefInfo(TextView chefNameTextView, TextView chefEmailTextView,
                                TextView chefRatingTextView, TextView chefDescriptionTextView,
                                String chefName, String chefEmail, String chefRating, String chefDescription){

        chefNameTextView.setText("Name: " + chefName);
        chefEmailTextView.setText("Email: " + chefEmail);
        chefRatingTextView.setText("Rating: " + chefRating + "/5");
        chefDescriptionTextView.setText("Description: " + chefDescription);
    }
}