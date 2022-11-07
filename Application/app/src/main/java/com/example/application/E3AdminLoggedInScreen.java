package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class E3AdminLoggedInScreen extends MainActivity {

    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference dataRef;
    List<Complaint> listOfComplaints;
    ListView listViewComplaints;


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

        //All code for the listView can be seen here
        //https://www.youtube.com/watch?v=aUFdgLSEl0g&ab_channel=CodingPursuit
        //So Admin can view complaints
        String tempListOfChefIDs[] = {"lWujifoQ5TMM9fWoBzlRAr8duNr2", "lWujifoQ5TMM9fWoBzlRAr8duNr2"};
        String tempListOfReasons[] = {"Burnt my Food", "Claimed food was halel when it wasn't"};
        listViewComplaints = (ListView) findViewById(R.id.listViewComplaints);
        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getApplicationContext(),tempListOfChefIDs,tempListOfReasons);
        listViewComplaints.setAdapter(customBaseAdapter);


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


        //So the Admin can view complaints
        //dataRef to work your way down the tree
        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dataRef = FirebaseDatabase.getInstance().getReference().child("Complaints");
        listOfComplaints = new ArrayList<>();

        //Creates Complaint Class and Arraylist to store all complaints
        dataRef.addValueEventListener(new ValueEventListener() {

            @Override
            //When data changes
            public void onDataChange(DataSnapshot dataSnapShot) {
                //Clearing the previous artist list

                //for every complaint entry on firebase
                for (DataSnapshot postSnapshot : dataSnapShot.getChildren()) {
                    Complaint newComplaint = new Complaint();
                    newComplaint.setChefID(String.valueOf(postSnapshot.child("chefID")));
                    newComplaint.setReason(String.valueOf(postSnapshot.child("reason")));

                    listOfComplaints.add(newComplaint);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        logOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);            }
        });

        suspenseChefPermanently.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Suspense Top Chef Permanentlys", Toast.LENGTH_SHORT).show();

            }
        });

        dismissComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Top Complaint Dismissed", Toast.LENGTH_SHORT).show();

            }
        });

        suspenseChefUntilSelectedDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Makes sure the log in is good
                Toast.makeText(getApplicationContext(), "Suspense Chef until date provided", Toast.LENGTH_SHORT).show();

                if (authenticatorObject.validateSuspensionDateFormat(datePicker,errorMessages)) {
                    //Set Chef Suspensed Date from datePicker
                    datePicker.getText().toString();
                    //IDK how to set the data in the data base
                }
        }

        });

    }

}