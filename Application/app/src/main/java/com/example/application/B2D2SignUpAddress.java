package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;


public class B2D2SignUpAddress extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b2d2_signup_address);

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
                        addAddressInputsToArrayList(addressLine1, addressLine2, city, province, postalCode, userInfo, "User");
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

                        addAddressInputsToArrayList(addressLine1, addressLine2, city, province, postalCode, userInfo, "Billing");

                        // At this point, it's a customer coming from D3 and inputting a billing address
                        // We want to finish the sign up process here and the array list is complete
                        // Implement database logic here

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

    public static void addAddressInputsToArrayList(EditText addressLine1, EditText addressLine2,
                                                   EditText city, EditText province,
                                                   EditText postalCode, String[] userInfo,
                                                   String userOrBillingAddress){

        if (userOrBillingAddress.equals("User")){
            userInfo[5] = addressLine1.getText().toString();
            userInfo[6] = addressLine2.getText().toString();
            userInfo[7] = city.getText().toString();
            userInfo[8] = province.getText().toString();
            userInfo[9] = postalCode.getText().toString();
        }

        else{
            userInfo[14] = addressLine1.getText().toString();
            userInfo[15] = addressLine2.getText().toString();
            userInfo[16] = city.getText().toString();
            userInfo[17] = province.getText().toString();
            userInfo[18] = postalCode.getText().toString();
        }
    }
}