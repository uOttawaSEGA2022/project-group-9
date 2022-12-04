package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

public class CustomerOrderInfoScreen extends MainActivity {

    public String chefID = "";
    public String orderID = "";
    public String hasRated = "";
    public String hasComplaint = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_info_screen);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Button submitComplaint = (Button) findViewById(R.id.complaintButton);
        Button submitRating = (Button) findViewById(R.id.ratingButton);

        TextView hasRatedTextView = findViewById(R.id.hasRatedTextView);
        TextView hasComplaintTextView = findViewById(R.id.hasComplaintTextView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            chefID = extras.getString("chefID");
            orderID = extras.getString("orderID");
            hasRated = extras.getString("hasRated");
            hasComplaint = extras.getString("hasComplaint");
        }

        if (hasRated.equals("true")){
            submitRating.setVisibility(View.INVISIBLE);
            hasRatedTextView.setText("You have already rated this chef");
        }
        else{
            hasRatedTextView.setVisibility(View.INVISIBLE);
        }

        if (hasComplaint.equals("true")){
            submitComplaint.setVisibility(View.INVISIBLE);
            hasComplaintTextView.setText("You have already complained about this chef");
        }
        else{
            hasComplaintTextView.setVisibility(View.INVISIBLE);
        }
        submitComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1  = new Intent(getApplicationContext(), CustomerCanComplainScreen.class);
                intent1.putExtra("chefID", chefID);
                intent1.putExtra("orderID", orderID);
                startActivity(intent1);
            }
        });

        submitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), CustomerCanRateScreen.class);
                intent2.putExtra("chefID", chefID);
                intent2.putExtra("orderID", orderID);
                startActivity(intent2);
            }
        });
    }
}