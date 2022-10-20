package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
                customerLogin();
                Toast.makeText(getApplicationContext(), "You are logging in as a customer", Toast.LENGTH_SHORT).show();
            }
        });

        chef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chefLogin();
                Toast.makeText(getApplicationContext(), "You are logging in as a chef", Toast.LENGTH_SHORT).show();
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminLogin();
                Toast.makeText(getApplicationContext(), "You are logging in as a admin", Toast.LENGTH_SHORT).show();
            }
        });
    }



    //IDK
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == android.R.id.home){
            finish();
        }
        return true;
    }



    //For Customer Login onclick
    public void customerLogin() {
        //Creates an intent of the screen to go to next
        Intent custIntent = new Intent(this, XLoginSignupScreen.class);
        //Create a Parameter
        custIntent.putExtra("CustomerOrChefOrAdmin", "Customer");
        //Start the new activity with parameter
        startActivity(custIntent);
    }

    //Same but for chef Login
    public void chefLogin() {
        Intent chefIntent = new Intent(this, XLoginSignupScreen.class);
        chefIntent.putExtra("CustomerOrChefOrAdmin", "Chef");
        startActivity(chefIntent);
    }

    public void adminLogin() {
        Intent adminIntent = new Intent(this, ALoginScreen.class);
        adminIntent.putExtra("CustomerOrChefOrAdmin", "Admin");
        startActivity(adminIntent);
    }
}