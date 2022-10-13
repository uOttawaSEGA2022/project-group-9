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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle("Mealer");
            actionBar.setSubtitle("Log in");
            actionBar.setLogo(R.drawable.logo);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

        }



        Button customer = (Button) findViewById(R.id.customerButton);
        Button chef = (Button) findViewById(R.id.chefButton);

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
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == android.R.id.home){
            finish();
        }
        return true;
    }



    public void customerLogin() {
        Intent custIntent = new Intent(this, XLoginSignupScreen.class);
        custIntent.putExtra("CustomerOrChef", "Customer");
        startActivity(custIntent);
    }

    public void chefLogin() {
        Intent chefIntent = new Intent(this, XLoginSignupScreen.class);
        chefIntent.putExtra("CustomerOrChef", "Chef");
        startActivity(chefIntent);
    }
}