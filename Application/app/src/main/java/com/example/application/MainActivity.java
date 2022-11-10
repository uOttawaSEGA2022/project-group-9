package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//On launch activity
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sets up action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            //Title and subtitle (subtitle is different for each screen)
            actionBar.setTitle("Mealer");
            actionBar.setSubtitle("Log in");
            //Setting a Logo
            actionBar.setLogo(R.drawable.logo);
            //Use Logo instead of Icon
            actionBar.setDisplayUseLogoEnabled(true);
            //Set Display
            actionBar.setDisplayShowHomeEnabled(true);

        }




        //Connects Button to ID and Java File
        Button customer = (Button) findViewById(R.id.customerButton);
        Button chef = (Button) findViewById(R.id.chefButton);
        Button admin  = (Button) findViewById(R.id.adminButton);

        //On Click Listener
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customerClicked();
                Toast.makeText(getApplicationContext(), "You are a customer", Toast.LENGTH_SHORT).show();
            }
        });

        chef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chefClicked();
                Toast.makeText(getApplicationContext(), "You are a chef", Toast.LENGTH_SHORT).show();
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminClicked();
                Toast.makeText(getApplicationContext(), "You are a admin", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == android.R.id.home){
            finish();
        }
        return true;
    }




    //For Customer Login onclick
    public void customerClicked() {
        //Creates an intent of the screen to go to next
        Intent custIntent = new Intent(this, XLoginSignupScreen.class);
        //Create a Parameter
        custIntent.putExtra("CustomerOrChefOrAdmin", "Customer");
        //Start the new activity with parameter
        startActivity(custIntent);
    }

    //Same but for chef Login
    public void chefClicked() {
        Intent chefIntent = new Intent(this, XLoginSignupScreen.class);
        chefIntent.putExtra("CustomerOrChefOrAdmin", "Chef");
        startActivity(chefIntent);
    }

    public void adminClicked() {
        //Intent adminIntent = new Intent(this, ALoginScreen.class);

        /*
        //This code until the last 3 lines in the method is for creating complaints in the DB, some issue here
        String[] userInfo;
        userInfo = new String[2];
        userInfo[0] = "lWujifoQ5TMM9fWoBzlRAr8duNr2";
        userInfo[1] = "Placed Chicken in a vegan meal";
        String[] registerInfo = {"email", "password"};

        fAuth.createUserWithEmailAndPassword(userInfo[0], userInfo[1]).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    dataRef = database.getReference("Complaints").child(fAuth.getCurrentUser().getUid());

                    for (int i=0; i<userInfo.length; i++) {
                        Log.d("chefInfo",registerInfo[i] + " " + userInfo[i]);
                        dataRef.child(registerInfo[i]).setValue(userInfo[i]);
                    }

                }
            }
        });

         */


        Intent adminIntent = new Intent(this, ALoginScreen.class);
        adminIntent.putExtra("CustomerOrChefOrAdmin", "Admin");
        startActivity(adminIntent);
    }
}