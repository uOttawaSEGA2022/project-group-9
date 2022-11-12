package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ChefAddMealActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");
        Button button = (Button) findViewById(R.id.donewithdesc);
        if (name == null)
        {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText e1 =(EditText) findViewById(R.id.dishname);
                    EditText e2 =(EditText) findViewById(R.id.dishtype);
                    EditText e3 =(EditText) findViewById(R.id.cuisinemeal);
                    EditText e4 =(EditText) findViewById(R.id.priceofmeal);
                    EditText e5 =(EditText) findViewById(R.id.descriptionofmeal);

                    // Continue logic here
                }
            });
        }
    }
}