package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.util.*;

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

        AuthenticatorServices authenticatorObject = new AuthenticatorServices();

        //This should remove the top action from the arraylist and change data in the chef folder
        //change the isSuspensed var to "true" as a String
        //change suspensed until to a 01/01/2200
        suspenseChefPermanentlyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DatabaseServices.tempListOfChefIDs != null && DatabaseServices.tempListOfReasons != null) {
                    DatabaseServices.updateChefStatus(DatabaseServices.tempListOfChefIDs[0], "01/01/9999");
                    Toast.makeText(getApplicationContext(), "Suspended Chef Forever!", Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(getApplicationContext(), "Unable To Suspend!", Toast.LENGTH_SHORT).show();
                }

                Intent E3Admin = new Intent(getApplicationContext(), E3AdminLoggedInScreen.class);
                startActivity(E3Admin);

            }
        });

        //WORK IN PROGRESS BY YASH!
        //This should remove the top action from the arraylist
        dismissComplaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DatabaseServices.tempListOfChefIDs != null && DatabaseServices.tempListOfReasons != null) {
                    DatabaseServices.updateList(DatabaseServices.tempListOfReasons[0]);
                    Toast.makeText(getApplicationContext(), "Successful Dismiss!", Toast.LENGTH_SHORT).show();
                }
//                if (DatabaseServices.tempListOfChefIDs != null && DatabaseServices.tempListOfReasons != null) {
//                    System.out.println("BEFORE UPDATE FOR REASONS");
//                    System.out.println(Arrays.toString(DatabaseServices.tempListOfChefIDs));
//                    System.out.println(Arrays.toString(DatabaseServices.tempListOfReasons));
//
//                    DatabaseServices.updateChefList(Arrays.copyOfRange(DatabaseServices.tempListOfChefIDs, 1, DatabaseServices.tempListOfChefIDs.length));
//                    DatabaseServices.updateReasonList(Arrays.copyOfRange(DatabaseServices.tempListOfReasons, 1, DatabaseServices.tempListOfReasons.length));
//                    Toast.makeText(getApplicationContext(), "Successful Dismiss!", Toast.LENGTH_SHORT).show();
//                }
//
//                else {
//                    Toast.makeText(getApplicationContext(), "Unsuccessful Dismiss!", Toast.LENGTH_SHORT).show();
//                }

                Intent E3Admin = new Intent(getApplicationContext(), E3AdminLoggedInScreen.class);
                startActivity(E3Admin);

//                Toast.makeText(getApplicationContext(), "Top Complaint Dismissed", Toast.LENGTH_SHORT).show();

//                Bundle extras = getIntent().getExtras();
//                if (extras != null) {
//                    String [] listOfChefs = extras.getStringArray("chefList");
//                    String [] listOfReasons = extras.getStringArray("reasonList");
//
//                    listOfChefs = Arrays.copyOfRange(listOfChefs, 1, listOfChefs.length);
//                    listOfReasons = Arrays.copyOfRange(listOfReasons, 1, listOfReasons.length);
//                    Toast.makeText(getApplicationContext(), "correct.", Toast.LENGTH_SHORT).show();
//                }
//
//                else {
//                    Toast.makeText(getApplicationContext(), "fail.", Toast.LENGTH_SHORT).show();
//                }
//
//                Intent E3Admin = new Intent(getApplicationContext(), E3AdminLoggedInScreen.class);
//                startActivity(E3Admin);
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

                    //setting the database
                    if (DatabaseServices.tempListOfChefIDs != null && DatabaseServices.tempListOfReasons != null) {
                        DatabaseServices.updateChefStatus(DatabaseServices.tempListOfChefIDs[0], datePickerEditText.getText().toString());
                        Toast.makeText(getApplicationContext(), "Suspended Chef Until Specified Date!", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        Toast.makeText(getApplicationContext(), "Unable To Suspend!", Toast.LENGTH_SHORT).show();
                    }

                    Intent E3Admin = new Intent(getApplicationContext(), E3AdminLoggedInScreen.class);
                    startActivity(E3Admin);

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