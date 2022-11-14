package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class E3AdminLoggedInScreen extends MainActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e3_admin_logged_in_screen);


        Button suspenseChefUntilSelectedDateButton = (Button) findViewById(R.id.suspenseChefUntilSelectedDateButton);
        Button suspenseChefPermanently = (Button) findViewById(R.id.suspenseChefPermanentlyButton);
        Button dismissComplaint = (Button) findViewById(R.id.dismissComplaintButton);
        Button logOff = (Button) findViewById(R.id.logOffButton);
        EditText datePicker = findViewById(R.id.datePickerText);
        TextView errorMessages = findViewById(R.id.errorMessageText);
        ListView listViewComplaints = findViewById(R.id.listViewComplaints);



        //For passing data from one file to another
        /*
        String userEmail = "";
        //Getting Data from loggining
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userEmail = extras.getString("email");
        }

        final String EMAIL = userEmail;
        Log.d("testing",EMAIL);

        */


        authenticator authenticatorObject = new authenticator();







        //Creates Complaint Class and Arraylist to store all complaints
        DatabaseServices databaseServices = new DatabaseServices();
        databaseServices.displayComplaintsForAdmin(getApplicationContext(), listViewComplaints);

        // logic to add suspended values to database

        //do not touch
        logOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);            }
        });

        //This should remove the top action from the arraylist and change data in the chef folder
        //change the isSuspensed var to "true" as a String
        //change suspensed until to a 01/01/2200
        suspenseChefPermanently.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Suspense Top Chef Permanentlys", Toast.LENGTH_SHORT).show();

            }
        });

        //This should remove the top action from the arraylist
        dismissComplaint.setOnClickListener(new View.OnClickListener() {
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

                if (authenticatorObject.validateSuspensionDateFormat(datePicker,errorMessages)) {
                    //Set Chef Suspensed Date from datePicker
                    datePicker.getText().toString();
                    Toast.makeText(getApplicationContext(), "Suspense Chef until date provided", Toast.LENGTH_SHORT).show();

                    //IDK how to set the data in the data base
                }
        }

        });

    }

}