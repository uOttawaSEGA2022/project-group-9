package com.example.lab4profilemanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView actionBarBackArrow = findViewById(R.id.actionBarBackArrow);
        actionBarBackArrow.setVisibility(View.INVISIBLE);
    }

    public void teamLogoOnClick(View view){
        Intent logoChangeActivity = new Intent(getApplicationContext(), LogoChangeActivity.class);
        startActivity(logoChangeActivity);
    }
}