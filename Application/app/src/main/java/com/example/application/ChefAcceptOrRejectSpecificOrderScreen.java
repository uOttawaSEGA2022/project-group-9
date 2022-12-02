package com.example.application;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

public class ChefAcceptOrRejectSpecificOrderScreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chef_accept_or_reject_specific_order_screen);

        DatabaseServices databaseServices = new DatabaseServices();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ChefOrder tempChefOrder = null;
        String tempFullName = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            tempChefOrder = (ChefOrder) extras.getSerializable("order");
            tempFullName = extras.getString("fullName");
        }

        final ChefOrder order = tempChefOrder;
        final String fullName = tempFullName;

        TextView orderedMealNameTextView = findViewById(R.id.orderMealNameTextView);
        TextView orderPriceTextView = findViewById(R.id.orderPriceTextView);
        TextView orderCustomerNameTextView = findViewById(R.id.orderCustomerNameTextView);
        TextView orderQuantityTextView = findViewById(R.id.orderQuantityTextView);
        TextView chefOrderStatusTextView = findViewById(R.id.chefOrderStatusTextView);

        Button acceptOrderButton = findViewById(R.id.acceptOrderButton);
        Button rejectOrderButton = findViewById(R.id.rejectOrderButton);

        String orderStatus = order.getStatus();

        orderedMealNameTextView.setText("Meal Ordered: " + order.getMeal().getName());
        orderPriceTextView.setText("Total Price: " + order.getPriceOfOrder());
        orderCustomerNameTextView.setText("Customer: " + fullName);
        orderQuantityTextView.setText("Quantity: " + order.getQuantity());
        chefOrderStatusTextView.setText("Status: " + order.getStatus());

        if (orderStatus.equals("accepted") || orderStatus.equals("rejected")){
            acceptOrderButton.setVisibility(View.INVISIBLE);
            rejectOrderButton.setVisibility(View.INVISIBLE);
        }

        acceptOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseServices.chefApprovedOrder(order.getOrderID(), order.getCustomerID());
                chefOrderStatusTextView.setText("Status: accepted");
                acceptOrderButton.setVisibility(View.INVISIBLE);
                rejectOrderButton.setVisibility(View.INVISIBLE);
            }
        });

        rejectOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseServices.chefDeclinedOrder(order.getOrderID(), order.getCustomerID());
                chefOrderStatusTextView.setText("Status: rejected");
                acceptOrderButton.setVisibility(View.INVISIBLE);
                rejectOrderButton.setVisibility(View.INVISIBLE);
            }
        });
    }
}
