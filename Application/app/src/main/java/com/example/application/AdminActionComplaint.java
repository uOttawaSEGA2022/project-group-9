package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AdminActionComplaint extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_action_complaint);

        Button suspenseChefUntilSelectedDateButton = (Button) findViewById(R.id.suspenseChefUntilSelectedDateButton);
        Button suspenseChefPermanentlyButton = (Button) findViewById(R.id.suspenseChefPermanentlyButton);
        Button dismissComplaintButton = (Button) findViewById(R.id.dismissComplaintButton);
        Button logOffButton = (Button) findViewById(R.id.logOffButton);
        EditText datePickerEditText = findViewById(R.id.datePickerText);
        TextView errorMessagesTextView = findViewById(R.id.errorMessageText);

        authenticator authenticatorObject = new authenticator();

        //This should remove the top action from the arraylist and change data in the chef folder
        //change the isSuspensed var to "true" as a String
        //change suspensed until to a 01/01/2200
        suspenseChefPermanentlyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Suspense Top Chef Permanentlys", Toast.LENGTH_SHORT).show();

            }
        });

        //This should remove the top action from the arraylist
        dismissComplaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Top Complaint Dismissed", Toast.LENGTH_SHORT).show();

            }
        });

        //This should remove the top action from the arraylist and change data in the chef folder
        //change the isSuspensed var to "true" as a String
        //change suspensed until to a DD/MM/YY
        suspenseChefUntilSelectedDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Makes sure the log in is good

                if (authenticatorObject.validateSuspensionDateFormat(datePickerEditText,errorMessagesTextView)) {
                    //Set Chef Suspensed Date from datePicker
                    datePickerEditText.getText().toString();
                    Toast.makeText(getApplicationContext(), "Suspense Chef until date provided", Toast.LENGTH_SHORT).show();

                    //IDK how to set the data in the data base
                }
            }

        });

        //do not touch
        logOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);            }
        });
    }


}