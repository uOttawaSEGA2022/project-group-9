package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class B4SignUpChefVoidCheque extends MainActivity{

    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b4_signup_chef_void_cheque);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String[] tempChefInfo = {};
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tempChefInfo = extras.getStringArray("Chef Info");
        }
        String[] chefInfo = tempChefInfo;

        Button takePictureBtn = findViewById(R.id.chefSignUpVoidChequeTakePictureBtn);
        Button loadFromPhoneBtn = findViewById(R.id.chefSignUpVoidChequeLoadFromPhoneBtn);
        Button finishSignUpBtn = findViewById(R.id.chefSignUpVoidChequeFinishSignUpBtn);

        ImageView voidCheque = findViewById(R.id.chefSignUpVoidChequeImage);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        String[] registerInfo = {"role", "firstname", "lastname", "", "", "addressline1", "addressline2", "city", "province", "postalcode", "shortdesc", ""};

        takePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement taking picture with camera logic here
                Toast.makeText(getApplicationContext(), "Take picture", Toast.LENGTH_SHORT).show();
            }
        });

        loadFromPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Load picture", Toast.LENGTH_SHORT).show();
            }
        });

        finishSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This is not an image, it's a drawable asset converted into string somehow
                // idk how to make it into an image, but this is the best we got
                chefInfo[11] = voidCheque.getDrawable().toString();

                fAuth.createUserWithEmailAndPassword(chefInfo[3], chefInfo[4]).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            dataRef = database.getReference("Chef").child(fAuth.getCurrentUser().getUid());

                            for (int i=0; i<chefInfo.length; i++) {
                                if(i == 0 || i == 3 || i == 4 || i == 11) continue;
                                Log.d("chefInfo",registerInfo[i] + " " + chefInfo[i]);
                                dataRef.child(registerInfo[i]).setValue(chefInfo[i]);
                            }

                            Toast.makeText(B4SignUpChefVoidCheque.this, "sign up successfull!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(B4SignUpChefVoidCheque.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

                Intent e2ChefLoggedInScreen = new Intent(getApplicationContext(), E2ChefLoggedInScreen.class);
                e2ChefLoggedInScreen.putExtra("Chef Info", chefInfo);
                startActivity(e2ChefLoggedInScreen);
            }
        });
    }
}
