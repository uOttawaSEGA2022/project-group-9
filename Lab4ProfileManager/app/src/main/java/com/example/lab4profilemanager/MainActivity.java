package com.example.lab4profilemanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

        //Back Arrow
        ImageView actionBarBackArrow = findViewById(R.id.actionBarBackArrow);
        actionBarBackArrow.setVisibility(View.INVISIBLE);

        Button openMaps = (Button) findViewById(R.id.btnOpenMapsID);

        //OnClick listener for Google Maps Button
        openMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnOpenInGoogleMaps(view);
            }
        });




    }

    /*
    To direct the user to the logo change screen

    HOW IT WORKS:
    Create a new instance of the Intent object with the constructor that has 2 parameters

    First Parameter...
    ApplicationContext : Refers to where the application is derived from by using getApplicationContext()

    Second Parameter...
    Class the intent is requesting

    startActivityForResult(logoChangeActivity, 0) : This method starts another activity from the current activity,
    in this situation the activity we are requesting for to get the result from is logoChangeActiviy so we can direct the user
    to change his/her logo. The 0 refers to the response code

    From there it will redirect the user to the logo change activity class and it will return to the activity where the method was called (so current activity)
    and call onActivityResult method
     */
    public void teamLogoOnClick(View view){
        Intent logoChangeActivity = new Intent(getApplicationContext(), LogoChangeActivity.class);
        startActivityForResult(logoChangeActivity, 0);
    }

    //To Open Google Maps
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
    /*
    This  overrode method retrieves/receives the result (result is the changed logo)

    Each switch case statement takes in a specific logo ID and if that switch statement is executed
    then the specific logo (with the ID) will be kept as the users logo

    Has a bunch of Log.d statements for debugging!
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("TESTING", "THIS FUNCTION IS ACTUALLY RUNNING OMG!");
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) return;
        //Getting the Avatar Image we show to our users
        ImageView avatarImage = (ImageView) findViewById(R.id.teamLogo);
        //Retrieves the correct image using switch statements
        String drawableName = "ic_logo_00";
        switch (data.getIntExtra("imageID", R.id.imageView2)) {
            case R.id.imageView2:
                drawableName = "ic_logo_01";
                Log.d("Image Chosen", "Function is working");
                break;
            case R.id.imageView3:
                drawableName = "ic_logo_02";
                Log.d("Image Chosen", "Function is working");
                break;
            case R.id.imageView4:
                drawableName = "ic_logo_03";
                Log.d("Image Chosen", "Function is working");
                break;
            case R.id.imageView5:
                drawableName = "ic_logo_04";
                Log.d("Image Chosen", "Function is working");
                break;
            case R.id.imageView6:
                drawableName = "ic_logo_05";
                Log.d("Image Chosen", "Function is working");
                break;
            case R.id.imageView7:
                drawableName = "ic_logo_00";
                Log.d("Image Chosen", "Function is working");
                break;
            default:
                drawableName = "ic_logo_01";
                Log.d("Image Chosen", "Function is working");
                break;
        }

        //Sets up resources and applies the change for the team logo using variable from switch statements called drawableName (which holds the logo ID)
        int resID = getResources().getIdentifier(drawableName, "drawable", getPackageName());
        avatarImage.setImageResource(resID);
    }




}