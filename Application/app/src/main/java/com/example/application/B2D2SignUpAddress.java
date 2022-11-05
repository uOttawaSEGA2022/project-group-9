package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class B2D2SignUpAddress extends MainActivity {

    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference dataRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.b2d2_signup_address);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String tempIntentSource = "";
        String[] tempUserInfo = {};
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tempIntentSource = extras.getString("Source");
            tempUserInfo = extras.getStringArray("User Info");
        }

        final String role = tempUserInfo[0];

        final String intentSource = tempIntentSource;

        final String[] tempHigherOrderUserInfo = tempUserInfo;

        TextView screenTitle = findViewById(R.id.signUpAddressTitle);

        Button finishContinueSignUpBtn = findViewById(R.id.signUpAddressFinishContinueBtn);

        EditText addressLine1 = findViewById(R.id.signUpAddressLine1);
        EditText addressLine2 = findViewById(R.id.signUpAddressLine2);
        EditText city = findViewById(R.id.signUpAddressCity);
        EditText province = findViewById(R.id.signUpAddressProvince);
        EditText postalCode = findViewById(R.id.signUpAddressPostalCode);
        EditText[] editTexts = {addressLine1,addressLine2,city,province,postalCode};
        TextView errorMessages =findViewById(R.id.signUpAddressErrorMessages);

        if (role.equals("Customer")){
            screenTitle.setText(role + " Sign Up");
        }

        if (intentSource.equals("B1D1")){
            finishContinueSignUpBtn.setText("Continue");
        }

        finishContinueSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // After error checking implementation, move this code section after error checking
                authenticator authenticatorObject = new authenticator();
                boolean validateTextInputs = authenticatorObject.checkAddressCredentialsInputs(editTexts, errorMessages);


                if (validateTextInputs){
                    String[] userInfo = tempHigherOrderUserInfo;

                    if (intentSource.equals("B1D1")){
                        addAddressInputsToArray(addressLine1, addressLine2, city, province, postalCode, userInfo, "User");
                        if (role.equals("Customer")){
                            // Send to D3
                            Intent d3SignUpCustomerCCInfo = new Intent(getApplicationContext(), D3SignUpCustomerCCInfo.class);
                            d3SignUpCustomerCCInfo.putExtra("Customer Info", userInfo);
                            startActivity(d3SignUpCustomerCCInfo);

                        }

                        else{
                            // Send to B3
                            Intent b3SignUpCHefShortDescription = new Intent(getApplicationContext(), B3SignUpChefShortDescription.class);
                            b3SignUpCHefShortDescription.putExtra("Chef Info", userInfo);
                            startActivity(b3SignUpCHefShortDescription);
                        }
                    }
                    else{

                        addAddressInputsToArray(addressLine1, addressLine2, city, province, postalCode, userInfo, "Billing");

                        // At this point, it's a customer coming from D3 and inputting a billing address
                        // We want to finish the sign up process here and the array list is complete

                        String[] registerInfo = {"role", "first_name", "last_name", "email", "password",
                                "addressline1", "addressline2", "city", "province", "postalcode",
                                "nameoncard", "creditcardnumber", "cvvnumber", "expirationdate",
                                "addressline1", "addressline2", "city", "province", "postalcode"};

                        fAuth.createUserWithEmailAndPassword(userInfo[3], userInfo[4]).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    dataRef = database.getReference("Chef").child(fAuth.getCurrentUser().getUid());

                                    for (int i=0; i<userInfo.length; i++) {
                                        if(i == 0 || i == 4 || i == 11) continue;
                                        Log.d("chefInfo",registerInfo[i] + " " + userInfo[i]);
                                        dataRef.child(registerInfo[i]).setValue(userInfo[i]);
                                    }

                                    Toast.makeText(B2D2SignUpAddress.this, "sign up successfull!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(B2D2SignUpAddress.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        Intent e1CustomerLoggedInScreen = new Intent(getApplicationContext(), E1CustomerLoggedInScreen.class);
                        startActivity(e1CustomerLoggedInScreen);
                    }
                }
            }
        });

    }

    // At this point, the arrayList should have 10 elements, going through indices 0-9:
    /*
     * role
     * first name, last name, email, password
     * address line 1, address line 2, city, province, postal code
     * */

    public static void addAddressInputsToArray(EditText addressLine1, EditText addressLine2,
                                               EditText city, EditText province,
                                               EditText postalCode, String[] userInfo,
                                               String userOrBillingAddress){

        if (userOrBillingAddress.equals("User")){
            userInfo[5] = addressLine1.getText().toString();
            userInfo[6] = addressLine2.getText().toString();
            userInfo[7] = city.getText().toString();
            userInfo[8] = province.getText().toString();
            userInfo[9] = postalCode.getText().toString();

            //Yash's Debugging Code Checking If Array Is Valid
            Log.i("CHECKING INDICES", "Index 5-9 is updated same addresses!");
            System.out.println(userInfo[5]);
            System.out.println(userInfo[6]);
            System.out.println(userInfo[7]);
            System.out.println(userInfo[8]);
            System.out.println(userInfo[9]);
        }

        else{
            userInfo[14] = addressLine1.getText().toString();
            userInfo[15] = addressLine2.getText().toString();
            userInfo[16] = city.getText().toString();
            userInfo[17] = province.getText().toString();
            userInfo[18] = postalCode.getText().toString();

            //Yash's Debugging Code Checking If Array Is Valid
            Log.i("CHECKING INDICES", "Index 14-18 is updated not same addresses!");
            System.out.println(userInfo[14]);
            System.out.println(userInfo[15]);
            System.out.println(userInfo[16]);
            System.out.println(userInfo[17]);
            System.out.println(userInfo[18]);
        }
    }
}