package com.example.application;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChefApproveDeclineScreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_approve_decline_screen);

        DatabaseServices databaseServicesObject = new DatabaseServices();


        Button approveOrderButton = (Button) findViewById(R.id.approveOrderButtonID);
        Button declineOrderButton = (Button) findViewById(R.id.declineOrderButtonID);

        //On Click Listener
        approveOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        //On Click Listener
        declineOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }






}