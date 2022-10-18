package com.example.lab5firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList;
    EditText editName;
    EditText editPrice;
    ArrayAdapter arrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);
        listView.setClickable(true);

        arrayList = new ArrayList<>();
        editName = (EditText) findViewById(R.id.enterNameID);

        arrayList.add("Monitor Dell $300");
        arrayList.add("Mouse $20");
        arrayList.add("Dell XPS 134 $700");

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(arrayAdapter);

        Button addProducts = (Button) findViewById(R.id.addProductID);

        //OnClick listener for Adding Products
        addProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewProduct(view);
            }
        });
        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView <? > arg0, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                arrayList.add("Goal");
                listView.setAdapter(arrayAdapter);

            }

        });
         */
    }

    public void addNewProduct (View view) {
        String newProductName = editName.getText().toString();
        String newProductPrice = editPrice.getText().toString();

        arrayList.add(newProductName + " $" + newProductPrice);
        listView.setAdapter(arrayAdapter);

    }


}
