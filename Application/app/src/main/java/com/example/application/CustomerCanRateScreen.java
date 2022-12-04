package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

public class CustomerCanRateScreen extends MainActivity {

    public String chefID = "";
    public String orderID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_set_rating);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        DatabaseServices databaseServices = new DatabaseServices();

        Button submitRating = (Button) findViewById(R.id.rate);
        EditText numberRating = (EditText) findViewById(R.id.textRating);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            chefID = extras.getString("chefID");
            orderID = extras.getString("orderID");
        }

        submitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseServices.submitRatingToChef(chefID, numberRating.getText().toString(), orderID);
                Toast.makeText(getApplicationContext(),"You have submitted a rating", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), E1CustomerLoggedInScreen.class);
                startActivity(intent);
            }
        });
    }
}
