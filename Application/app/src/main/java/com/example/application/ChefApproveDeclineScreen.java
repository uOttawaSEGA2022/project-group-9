package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class ChefApproveDeclineScreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_approve_decline_screen);
    }

    Button approveOrderButton = (Button) findViewById(R.id.approveOrderButtonID);
    Button declineOrderButton = (Button) findViewById(R.id.declineOrderButtonID);


    


}