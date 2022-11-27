package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


public class E3AdminLoggedInScreen extends MainActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e3_admin_logged_in_screen);

        Button logOff = (Button) findViewById(R.id.logOffButton);
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

        //allows complaints to be clickable
        listViewComplaints.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickComplaints = new Intent(getApplicationContext(), AdminActionComplaint.class);
                startActivity(clickComplaints);
            }
        });
    }
}