package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerCanComplainScreen extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_set_complaint);

        DatabaseServices databaseServices = new DatabaseServices();

        Button submitComplaint = (Button) findViewById(R.id.complain);
        EditText complaintReason = (EditText) findViewById(R.id.textComplaint);

        submitComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseServices.submitComplaint("ewhbfewakjewbfwa328", complaintReason.getText().toString());
                Toast.makeText(getApplicationContext(), "You have filed a complaint against the chef.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), E1CustomerLoggedInScreen.class);
                startActivity(intent);
            }
        });
    }
}
