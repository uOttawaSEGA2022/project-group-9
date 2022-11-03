package com.example.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ViewData extends AppCompatActivity {
    FirebaseFirestore firestre;
    DatabaseReference dbp;
    int no;
    LinearLayout linlay;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        v=(View)getLayoutInflater().inflate(R.layout.activity_view_data,null,false);
        linlay=(LinearLayout)v.findViewById(R.id.linlay);
        firestre = FirebaseFirestore.getInstance();
        CollectionReference usersCollectionRef = firestre.collection("foods");
        dbp= FirebaseDatabase.getInstance().getReference("foods");
        DocumentReference d=FirebaseFirestore.getInstance().collection("foods").document("no of doc");
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
        /*d.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists())
                {
                    EditText ti;
                    ti=new EditText(linlay.getContext());
                    ti.setText("hey crunchy crunch");
                    ti.setTextSize(getResources().getDimension(R.dimen.height));
                    ti.setGravity(Gravity.CENTER);
                    linlay.addView(ti);
                    System.out.println("no");
                    no=Integer.parseInt(documentSnapshot.getString("no"));
                    System.out.println("no="+no);
                    for (int i=0;i<no;i++) {
                        DocumentReference dr = FirebaseFirestore.getInstance().collection("foods").document(Integer.toString(i));
                        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    System.out.println("hello");
                                    String name;
                                    name = documentSnapshot.getString("name");
                                    int price;
                                    price =0;// Integer.parseInt(documentSnapshot.getString("price"));
                                    LinearLayout.LayoutParams but;
                                    int width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                    but = new LinearLayout.LayoutParams(width, height);
                                    System.out.println("hey crunchy");
                                    EditText t;
                                    t = new EditText(linlay.getContext());
                                    t.setText(name + '\t' + Integer.toString(price));
                                    t.setTextSize(getResources().getDimension(R.dimen.height));
                                    t.setGravity(Gravity.CENTER);
                                    linlay.addView(t);
                                }
                            }
                        });
                    }
                }
            }
        });
        System.out.println("hi");
        EditText ti;
        ti=new EditText(linlay.getContext());
        ti.setText("hey crunchy crunch");
        ti.setTextSize(getResources().getDimension(R.dimen.height));
        ti.setGravity(Gravity.CENTER);
        linlay.addView(ti);
        for (int i=0;i<no;i++)
        {
            EditText t;
            t=new EditText(linlay.getContext());
            t.setText("hey crunchy crunch");
            t.setTextSize(getResources().getDimension(R.dimen.height));
            t.setGravity(Gravity.CENTER);
            linlay.addView(t);
            System.out.println("hello out");

        }*/
    }
}