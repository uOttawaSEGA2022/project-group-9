package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AddMeal extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        db=FirebaseDatabase.getInstance();
        dr=db.getReference();
        Intent intent = getIntent();
        String name;
        name=intent.getStringExtra("NAME");
        Button b;
        b=(Button) findViewById(R.id.donewithdesc);
        if (name==null)
        {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText e1;
                    EditText e2;
                    EditText e3;
                    EditText e4;
                    EditText e5;
                    e1=(EditText) findViewById(R.id.dishname);
                    e2=(EditText) findViewById(R.id.dishtype);
                    e3=(EditText) findViewById(R.id.cuisinemeal);
                    e4=(EditText) findViewById(R.id.priceofmeal);
                    e5=(EditText) findViewById(R.id.descriptionofmeal);
                    Meal m=new Meal(e1.getText().toString(),e2.getText().toString(),e3.getText().toString(),Double.parseDouble(e4.getText().toString()),e5.getText().toString(),"NOT IMPLEMENTED");
                    Bus.savedata(m);
                    Intent intent1=new Intent(getApplicationContext(), AcceptList.class);
                    intent1.putExtra("NAME","INGREDIANTS");
                    intent1.putExtra("OBJREF",Bus.savedata(m));
                    startActivity(intent1);
                }
            });
        }
        else if (name.equals("INGREDIANTS"))
        {
            Meal m;
            m=(Meal) Bus.getdata(intent.getIntExtra("OBJREF",-5));
            m.addingrediants((List<String>) Bus.getdata(intent.getIntExtra("LIST",-5)));
            Intent intent2=new Intent(getApplicationContext(), AcceptList.class);
            intent2.putExtra("NAME","ALLERGENS");
            intent2.putExtra("OBJREF",Bus.savedata(m));
            startActivity(intent2);
        }
        else
        {
            Meal m;
            m=(Meal) Bus.getdata(intent.getIntExtra("OBJREF",-5));
            m.addallergens((List<String>) Bus.getdata(intent.getIntExtra("LIST",-5)));
            dr.child("menu").child(m.getName()).setValue(m);
            Intent intent3=new Intent(getApplicationContext(), E2ChefLoggedInScreen.class);
            startActivity(intent3);
        }
    }
}