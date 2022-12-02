package com.example.application;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import java.util.List;

public class ChefViewOrdersScreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_view_orders_screen);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        LayoutInflater inflater = LayoutInflater.from(this);

        LinearLayout chefOrdersLinearLayout = findViewById(R.id.chefOrdersLinearLayout);

        DatabaseServices databaseServices = new DatabaseServices();

        databaseServices.getChefOrders(inflater, chefOrdersLinearLayout, "all");
    }

    public void displayChefOrders(List<ChefOrder> orderList, LayoutInflater inflater, LinearLayout chefOrdersLinearLayout, String fullName){
        chefOrdersLinearLayout.removeAllViews();

        for (ChefOrder order : orderList){
            Log.d("HelloThereBro", order.toString());
            Meal meal = order.getMeal();

            View orderTemplate = inflater.inflate(R.layout.order_template, null);

            TextView pendingStatusTextView = orderTemplate.findViewById(R.id.pendingStatus);
            TextView mealOrderedTextView = orderTemplate.findViewById(R.id.mealOrdered);
            TextView quantityTextView = orderTemplate.findViewById(R.id.quantityOfMeal);
            TextView ratedStatusTextView = orderTemplate.findViewById(R.id.ratedStatus);
            TextView complainStatusTextView = orderTemplate.findViewById(R.id.complaintStatus);
            TextView mealDescriptionTextView = orderTemplate.findViewById(R.id.mealDescription);

            pendingStatusTextView.setText("Status: " + order.getStatus());
            mealOrderedTextView.setText("Meal: " + meal.getName());
            ratedStatusTextView.setText("For: " + fullName);
            quantityTextView.setText("Quantity: " + order.getQuantity());
            complainStatusTextView.setVisibility(View.INVISIBLE);
            mealDescriptionTextView.setVisibility(View.INVISIBLE);

            chefOrdersLinearLayout.addView(orderTemplate);

        }
    }
}