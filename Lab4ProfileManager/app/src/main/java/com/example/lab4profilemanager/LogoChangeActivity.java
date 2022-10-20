package com.example.lab4profilemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class LogoChangeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_logo);

        ImageView actionBarBackArrow = findViewById(R.id.actionBarBackArrow);

        //go back to the main activity
        actionBarBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }

    /*
    Sets Team Icon, common method

    Forwards the ID of the image that the user clicked/pressed BACK to the main activity
     */
    public void SetTeamIcon(View view) {

        //Creating a Return intent to pass to the Main Activity
        Intent returnIntent = new Intent();

        //Figuring out which image was clicked
        ImageView selectedImage = (ImageView) view;

        //Adding stuff to the return intent
        returnIntent.putExtra("imageID", selectedImage.getId());
        setResult(RESULT_OK, returnIntent);

        //Finishing Activity and return to main screen!
        finish();
    }
}