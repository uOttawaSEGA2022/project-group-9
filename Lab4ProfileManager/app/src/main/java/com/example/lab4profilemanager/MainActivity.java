package com.example.lab4profilemanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView actionBarBackArrow = findViewById(R.id.actionBarBackArrow);
        actionBarBackArrow.setVisibility(View.INVISIBLE);

        Button openMaps = (Button) findViewById(R.id.btnOpenMapsID);

        openMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnOpenInGoogleMaps(view);
            }
        });




    }

    public void teamLogoOnClick(View view){
        Intent logoChangeActivity = new Intent(getApplicationContext(), LogoChangeActivity.class);
        startActivity(logoChangeActivity);
    }

    public void OnOpenInGoogleMaps (View view) {
        //Will open location on google maps
        EditText teamAddress = (EditText) findViewById(R.id.teamAddressID);
        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("http://maps.google.co.in/maps?q="+teamAddress.getText());
        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");
        // Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) return;
        //Getting the Avatar Image we show to our users
        ImageView avatarImage = (ImageView) findViewById(R.id.teamLogo);
        //Figuring out the correct image
        String drawableName;
        switch (data.getIntExtra("imageID", R.id.teamNameID)) {

            case R.id.imageView2:
                drawableName = "ic_logo_01";
                break;
            case R.id.imageView3:
                drawableName = "ic_logo_02";
                //Log.i("UniqueID","1");
                break;
            case R.id.imageView4:
                drawableName = "ic_logo_03";
                //Log.i("UniqueID","2");
                break;
            case R.id.imageView5:
                drawableName = "ic_logo_04";
                //Log.i("UniqueID","3");
                break;
            case R.id.imageView6:
                drawableName = "ic_logo_05";
                break;
            case R.id.imageView7:
                drawableName = "ic_logo_00";
                break;
            default:
                drawableName = "ic_logo_01";
                break;
        }
        int resID = getResources().getIdentifier(drawableName, "drawable", getPackageName());
        avatarImage.setImageResource(resID);
    }




}