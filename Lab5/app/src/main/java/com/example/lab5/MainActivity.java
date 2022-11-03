package com.example.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore firestre;
    Button b;
    Button v;
    EditText n;
    EditText p;
    MainActivity ma;
    int no;
    LinearLayout linlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ma = this;
        EditText ti;

        linlay = (LinearLayout) findViewById(R.id.linlay1);


        n = (EditText) findViewById(R.id.name);
        p = (EditText) findViewById(R.id.price);
        b = (Button) findViewById(R.id.add);
        v = (Button) findViewById(R.id.seedata);
        firestre = FirebaseFirestore.getInstance();
        firestre.collection("foods").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> l=queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d:l)
                {
                     System.out.println(d.getString("name"));
                     System.out.println(d.get("price"));
                }
            }
        });
        firestre = FirebaseFirestore.getInstance();
        firestre.collection("foods").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> l=queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d:l)
                {
                    EditText ti;
                    ti=new EditText(linlay.getContext());
                    ti.setText(d.getString("name")+'\t'+Long.toString((long)d.get("price")));
                    ti.setTextSize(getResources().getDimension(R.dimen.height));
                    ti.setGravity(Gravity.CENTER);
                    linlay.addView(ti);
                    System.out.println(d.getString("name"));
                    System.out.println(d.get("price"));
                }
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> mp = new HashMap();
                mp.put("name", n.getText().toString());
                mp.put("price", Integer.parseInt(p.getText().toString()));
                firestre.collection("foods").document(Integer.toString(no)).set(mp);
                n.setText("");
                p.setText("");
                Intent i = new Intent(ma, MainActivity.class);
                startActivity(i);
            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firestre.collection("foods").whereEqualTo("name",n.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        DocumentSnapshot ds=task.getResult().getDocuments().get(0);
                        String di=ds.getId();
                        firestre.collection("foods").document(di).delete();
                        Intent i = new Intent(ma, MainActivity.class);
                        startActivity(i);
                    }
                });
            }
        });

    }

    private void seeData() {
        Intent i = new Intent(ma, ViewData.class);
        startActivity(i);
    }

}