package com.example.application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import java.util.List;

public class ChefViewAcceptedOrdersScreen extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accepted_chef_orders);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        LayoutInflater inflater = LayoutInflater.from(this);

        LinearLayout acceptedChefOrdersLinearLayout = findViewById(R.id.acceptedChefOrdersLinearLayout);

        DatabaseServices databaseServices = new DatabaseServices();

        databaseServices.getChefOrders(inflater, acceptedChefOrdersLinearLayout, "accepted", ChefViewAcceptedOrdersScreen.this);
    }

    public void displayAcceptedOrders(List<ChefOrder> orderList, LayoutInflater inflater, LinearLayout acceptedChefOrdersLinearLayout, String fullName){
        acceptedChefOrdersLinearLayout.removeAllViews();

        for (ChefOrder order : orderList){
            if (order.getStatus().equals("accepted")){
                Meal meal = order.getMeal();

                View orderTemplate = inflater.inflate(R.layout.order_template, null);

                TextView pendingStatusTextView = orderTemplate.findViewById(R.id.pendingStatus);
                TextView mealOrderedTextView = orderTemplate.findViewById(R.id.mealOrdered);
                TextView quantityTextView = orderTemplate.findViewById(R.id.quantityOfMeal);
                TextView ratedStatusTextView = orderTemplate.findViewById(R.id.ratedStatus);
                TextView complainStatusTextView = orderTemplate.findViewById(R.id.complaintStatus);
                TextView mealDescriptionTextView = orderTemplate.findViewById(R.id.mealDescription);

                pendingStatusTextView.setText("Status: accepted");
                mealOrderedTextView.setText("Meal: " + meal.getName());
                ratedStatusTextView.setText("For: " + fullName);
                quantityTextView.setText("Quantity: " + order.getQuantity());
                complainStatusTextView.setVisibility(View.INVISIBLE);
                mealDescriptionTextView.setVisibility(View.INVISIBLE);

                acceptedChefOrdersLinearLayout.addView(orderTemplate);
            }

        }
    }
}