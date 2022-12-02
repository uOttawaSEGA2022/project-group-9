package com.example.application;

import android.content.Context;
import android.content.Intent;
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

        databaseServices.getChefOrders(inflater, chefOrdersLinearLayout, "all", ChefViewOrdersScreen.this);
    }

    public void displayChefOrders(List<ChefOrder> orderList, LayoutInflater inflater, LinearLayout chefOrdersLinearLayout, String fullName, Context context){
        chefOrdersLinearLayout.removeAllViews();

        for (ChefOrder order : orderList){
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

            orderTemplate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToChefAcceptOrRejectSpecificOrderScreen = new Intent(context, ChefAcceptOrRejectSpecificOrderScreen.class);
                    goToChefAcceptOrRejectSpecificOrderScreen.putExtra("order", order);
                    goToChefAcceptOrRejectSpecificOrderScreen.putExtra("fullName", fullName);
                    context.startActivity(goToChefAcceptOrRejectSpecificOrderScreen);
                }
            });

            chefOrdersLinearLayout.addView(orderTemplate);

        }
    }
}