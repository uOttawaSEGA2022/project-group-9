package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CustomerOrderInfoScreen extends MainActivity {

    public String chefID = "";
    public String orderID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_info_screen);

        Button submitComplaint = (Button) findViewById(R.id.complaintButton);
        Button submitRating = (Button) findViewById(R.id.ratingButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            chefID = extras.getString("chefID");
            orderID = extras.getString("orderID");
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