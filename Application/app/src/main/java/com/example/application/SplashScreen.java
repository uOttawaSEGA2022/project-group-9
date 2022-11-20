package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    private static final int splashScreenDelay=4000;
    TextView mightyBites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.splash_screen);
       mightyBites = findViewById(R.id.textView3);
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent splashScreenIntent = new Intent(SplashScreen.this, MainActivity.class);
               startActivity(splashScreenIntent);
               finish();
           }
       }, splashScreenDelay);

        Animation popAnimation = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.strong_pop_up);
        mightyBites.startAnimation(popAnimation);
    }


}
