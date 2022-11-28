package com.example.application;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class B4SignUpChefVoidCheque extends MainActivity{

    private final int GALLERY_REQ_CODE = 1000;
    ImageView imgGallery;

    Button btnGallery;

    StorageReference storageReference;   //to upload to firebase Storage, not realtime database

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b4_signup_chef_void_cheque);

        btnGallery = findViewById(R.id.chefSignUpVoidChequeLoadFromPhoneBtn);
        imgGallery = (ImageView) findViewById(R.id.chefSignUpVoidChequeImage);

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
        Button finishSignUpBtn = findViewById(R.id.chefSignUpVoidChequeFinishSignUpBtn);

        //onButton Click
        takePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement taking picture with camera logic here
                Toast.makeText(getApplicationContext(), "Take picture", Toast.LENGTH_SHORT).show();

                /*
                File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
                //Creating a Link type of thing
                Uri imgUri = Uri.fromFile(file);
                //Get Path
                String imgPath = file.getAbsolutePath();
                //Create a new view
                final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                //Start new Activity (Comes from the Lab 4 Tutorial)
                //startActivityForResult(intent, CAPTURE_IMAGE);
                //Comes from the working Lab 4 Profile Manager File line from Github
                startActivity(intent, CAPTURE_IMAGE);

                 */

            }
        });
        /*
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
            if (resultCode != Activity.RESULT_CANCELED) {
                if (requestCode == CAPTURE_IMAGE) {
                    ImageView imageView = (ImageView) findViewById(R.id.imgView);
                    imageView.setImageBitmap(BitmapFactory.decodeFile(imgPath));
                }
            }
        }
        */

        finishSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This is not an image, it's a drawable asset converted into string somehow
                // idk how to make it into an image, but this is the best we got
                chefInfo[11] = "Blank Image";
                chefInfo[12] = "False";
                chefInfo[13] = "Null";

                DatabaseServices databaseServices = new DatabaseServices();
                databaseServices.createUser(B4SignUpChefVoidCheque.this, chefInfo, "Chef");


                //uploading to firebase Storage
                storageReference = FirebaseStorage.getInstance().getReference("Chef").child(Math.random()+"").child("Void Check");
                storageReference.putFile(imageUri);
                //plug in name instead of to be changed later


                Intent e2ChefLoggedInScreen = new Intent(getApplicationContext(), E2ChefLoggedInScreen.class);
                e2ChefLoggedInScreen.putExtra("Chef Info", chefInfo);
                e2ChefLoggedInScreen.putExtra("Chef Info", chefInfo);
                e2ChefLoggedInScreen.putExtra("Email",chefInfo[3]);
                startActivity(e2ChefLoggedInScreen);
            }
        });

//      CODE FOR UPLOADING IMAGE DURING SIGN UP { JAY }

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){

            if(requestCode==GALLERY_REQ_CODE){

                //for gallery
                imageUri = data.getData();
                imgGallery.setImageURI(imageUri );
             }
        }
    }
}
