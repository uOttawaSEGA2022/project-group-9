package com.example.application;

import android.os.Bundle;
import android.widget.Button;

public class CustomerOrderInfoScreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_info_screen);

        Button submitComplaint = (Button) findViewById(R.id.complaintButton);
        Button submitRating = (Button) findViewById(R.id.ratingButton);
    }
}