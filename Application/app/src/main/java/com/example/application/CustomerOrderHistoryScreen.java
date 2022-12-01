package com.example.application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import java.util.List;

public class CustomerOrderHistoryScreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_history_screen);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        DatabaseServices databaseServices = new DatabaseServices();

        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout allCustomerOrdersLinearLayout = findViewById(R.id.allCustomerOrdersLinearLayout);

        databaseServices.viewCustomerOrders(inflater, allCustomerOrdersLinearLayout, CustomerOrderHistoryScreen.this);
    }

    public void displayOrders(List<customerOrder> orders, LayoutInflater inflater, LinearLayout layout, Context context) {
        for (customerOrder order : orders) {
            String mealName = order.getMealName();
            String quantity = order.getQuantity();
            String hasRated = order.getHasRated();
            String hasComplaint = order.getHasComplaint();

            View orderTemplate = inflater.inflate(R.layout.order_template, null);

            TextView mealOrdered = orderTemplate.findViewById(R.id.mealOrdered);
            TextView quantityStatus = orderTemplate.findViewById(R.id.quantityOfMeal);
            TextView hasRatedStatus = orderTemplate.findViewById(R.id.ratedStatus);
            TextView hasComplaintStatus = orderTemplate.findViewById(R.id.complaintStatus);

            mealOrdered.setText(mealName);
            quantityStatus.setText(quantity);
            hasRatedStatus.setText(hasRated);
            hasComplaintStatus.setText(hasComplaint);

            orderTemplate.setClickable(true);
            orderTemplate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CustomerOrderInfoScreen.class);
                    intent.putExtra("chefID", order.getChefID());
                    context.startActivity(intent);
                }
            });

            layout.addView(orderTemplate);
        }
    }
}

/*
        //METHOD 2 - USING UI
        LinearLayout allCustomerOrders = findViewById(R.id.allCustomerOrdersLinearLayout);
        databaseServices.displayOrderHistory(allCustomerOrders, getApplicationContext());

        allCustomerOrders.setClickable(true);
        allCustomerOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerOrderHistoryScreen.this, CustomerOrderInfoScreen.class);
                startActivity(intent);
            }
        });
 */

/*
//        //METHOD #1 - USING NO UI
//        List<Order> customersOrders = databaseServices.viewCustomerOrders();
//
//        LayoutInflater inflater = LayoutInflater.from(this);
//        LinearLayout allCustomerOrders = findViewById(R.id.allCustomerOrdersLinearLayout);
//
//        for (Order order : customersOrders) {
//            String mealName = order.getMealName();
//            String quantity = order.getQuantity();
//            String hasRated = order.getHasRated();
//            String hasComplaint = order.getHasComplaint();
//
//            View orderTemplate = inflater.inflate(R.layout.order_template, null);
//
//            TextView mealOrdered = orderTemplate.findViewById(R.id.mealOrdered);
//            TextView quantityStatus = orderTemplate.findViewById(R.id.quantityOfMeal);
//            TextView hasRatedStatus = orderTemplate.findViewById(R.id.ratedStatus);
//            TextView hasComplaintStatus = orderTemplate.findViewById(R.id.complaintStatus);
//
//            mealOrdered.setText(mealName);
//            quantityStatus.setText(quantity);
//            hasRatedStatus.setText(hasRated);
//            hasComplaintStatus.setText(hasComplaint);
//
//            orderTemplate.setClickable(true);
//            orderTemplate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(CustomerOrderHistoryScreen.this, CustomerOrderInfoScreen.class);
//                    startActivity(intent);
//                }
//            });
//
//            allCustomerOrders.addView(orderTemplate);
//        }
 */