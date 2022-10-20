package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class D3SignUpCustomerCCInfo extends MainActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d3_signup_customer_cc_info);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        EditText nameOnCard = findViewById(R.id.signUpCustomerNameOnCard);
        EditText creditCardNumber = findViewById(R.id.signUpCustomerCreditCardNumber);
        EditText cvvNumber = findViewById(R.id.signUpCustomerCVVNumber);
        EditText expirationDate = findViewById(R.id.signUpCustomerExpirationDate);
        EditText[] editTexts = {nameOnCard,creditCardNumber,cvvNumber,expirationDate};
        TextView errorMessages = findViewById(R.id.signUpCustomerCCErrorMessages);


        String[] tempCustomerInfo = {};
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tempCustomerInfo = extras.getStringArray("Customer Info");
        }
        String[] customerInfo = tempCustomerInfo;

        CheckBox userAddressSameAsShippingAddress = findViewById(R.id.signUpCustomerCCInfoSameAddressCheckbox);
        Button finishSignUpContinue = findViewById(R.id.signUpCustomerCCInfoFinishOrContinueBtn);

        userAddressSameAsShippingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finishSignUpContinue.getText().equals("Continue")){
                    finishSignUpContinue.setText("Finish Sign Up");
                }
                else{
                    finishSignUpContinue.setText("Continue");
                }
            }
        });

        finishSignUpContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticator authenticatorObject = new authenticator();
                boolean validateTextInputs = authenticatorObject.checkCCInputs(editTexts, errorMessages);

                // If we implement error checking, move this line of code to after all the error checking
                addTextInputs(nameOnCard, creditCardNumber, cvvNumber, expirationDate, customerInfo);
                if (userAddressSameAsShippingAddress.isChecked()){

                    // Adding the billing address (same as user address)
                    String adressLine1 = customerInfo[5];
                    String adressLine2 = customerInfo[6];
                    String city = customerInfo[7];
                    String province = customerInfo[8];
                    String postalCode = customerInfo[9];

                    customerInfo[14] = adressLine1;
                    customerInfo[15] = adressLine2;
                    customerInfo[16] = city;
                    customerInfo[17] = province;
                    customerInfo[18] = postalCode;





                    // ArrayList is now complete, add ID and send to database, add logic for that here
                    // The arrayList has 19 elements, going through indices 0-18:
                    /*
                     * role (customer)
                     * first name, last name, email, password
                     * address line 1, address line 2, city, province, postal code (User address)
                     * name on card, credit card number, cvv number, expiration date
                     * address line 1, address line 2, city, province, postal code (Billing address)
                     * */


                    Intent e1CustomerLoggedInScreen = new Intent(getApplicationContext(), E1CustomerLoggedInScreen.class);
                    // We can send name and some info on the user to the log in screen here, or we can fetch from the database immediately
                    startActivity(e1CustomerLoggedInScreen);
                }
                else{
                    Intent b2d2SignUpAddress = new Intent(getApplicationContext(), B2D2SignUpAddress.class);

                    b2d2SignUpAddress.putExtra("User Info", customerInfo);
                    b2d2SignUpAddress.putExtra("Source", "D3");
                    startActivity(b2d2SignUpAddress);
                }
            }
        });
    }

    // At this point, the arrayList should have 14 elements, going through indices 0-13:
    /*
     * role (customer)
     * first name, last name, email, password
     * address line 1, address line 2, city, province, postal code
     * name on card, credit card number, cvv number, expiration date
     * */

    public static void addTextInputs(EditText nameOnCard, EditText creditCardNumber,
                                     EditText cvvNumber, EditText expirationDate,
                                     String[] customerInfo){

        customerInfo[10] = nameOnCard.getText().toString();
        customerInfo[11] = creditCardNumber.getText().toString();
        customerInfo[12] = cvvNumber.getText().toString();
        customerInfo[13] = expirationDate.getText().toString();

        //Yash's Debugging Code Checking If Array Is Valid
        Log.i("CHECKING INDICES", "Index 10-13 is updated for customer");
        System.out.println(customerInfo[10]);
        System.out.println(customerInfo[11]);
        System.out.println(customerInfo[12]);
        System.out.println(customerInfo[13]);
    }
}
