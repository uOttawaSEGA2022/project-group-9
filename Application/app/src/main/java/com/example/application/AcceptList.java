package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

public class AcceptList extends MainActivity {
    List<String> lst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_list);
        Intent intent=getIntent();
        int fr=intent.getIntExtra("OBJREF",-5);
        String name=intent.getStringExtra("NAME");
        TextView t;
        t=(TextView) findViewById(R.id.name);
        t.setText(name);
        EditText et;
        et=(EditText) findViewById(R.id.item);
        lst=new LinkedList<String>();
        Button b;
        b=(Button) findViewById(R.id.add);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lst.add(et.getText().toString());
            }
        });
        Button d;
        d=(Button) findViewById(R.id.done);
//        d.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent mainActivity = new Intent(getApplicationContext(), ChefAddMealActivity.class);
//                mainActivity.putExtra("NAME",name);
//                mainActivity.putExtra("LIST",Bus.savedata(lst));
//                mainActivity.putExtra("OBJREF", fr);
//                startActivity(mainActivity);
//
//            }
//        });
    }
}