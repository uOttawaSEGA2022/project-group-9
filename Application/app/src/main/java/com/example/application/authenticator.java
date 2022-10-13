package com.example.application;

import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class authenticator extends AppCompatActivity {

    public boolean checkNameCredentialsInputs(EditText[] editTexts, TextView errorMessages){
        EditText firstName = editTexts[0];
        EditText lastName = editTexts[1];
        EditText email = editTexts[2];
        EditText password = editTexts[3];
        EditText reenterPassword = editTexts[4];

        boolean nameValidation = this.checkFirstLastName(firstName, lastName, errorMessages);
        if (!nameValidation){
            return false;
        }

        boolean credentialValidation = this.checkCredentials(email, password, errorMessages);
        if (!credentialValidation){
            return false;
        }

        String stringPassword = password.getText().toString();
        String stringReenterPassword = reenterPassword.getText().toString();

        if (!stringPassword.equals(stringReenterPassword)){
            errorMessages.setText("Passwords do not match");
            return false;
        }

        return true;


    }

    public boolean checkFirstLastName(@NonNull EditText firstName, @NonNull EditText lastName, TextView errorMessages) {
        String specialCharacters = "(.*[!\"#$%&'()*+,-./:;<=>?@^_`{|}~].*)";

        String stringFirstName = firstName.getText().toString();
        String stringLastName = lastName.getText().toString();

        String[] fullName = {stringFirstName, stringLastName};

        for (String currentName : fullName) {
            if (currentName.equals("")){
                errorMessages.setText("Invalid Name");
                return false;
            }
            if (currentName.matches(specialCharacters)){
                errorMessages.setText("Invalid Name");
                return false;
            }
        }
        return true;
    }

        public boolean checkCredentials (EditText email, EditText password, @NonNull TextView emailPasswordErrorMessages){
            String stringEmail = email.getText().toString();
            String emailValidation = validateEmail(stringEmail);
            String stringPassword = password.getText().toString();
            String passwordValidation = validatePassword(stringEmail, stringPassword);
            if (!emailValidation.equals("Valid Email"))
                emailPasswordErrorMessages.setText(emailValidation);
            else if (!passwordValidation.equals("Valid Password"))
                emailPasswordErrorMessages.setText(passwordValidation);
            else {
                return true;
            }
            return false;
        }

        public String[] getCredentials (@NonNull EditText editTextEmail, @NonNull EditText editTextPassword) {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            return new String[]{email, password};
        }

        public String validateEmail (@NonNull String email){
            if (email.contains("@"))
                return "Valid Email";
            return "Invalid Email";
        }

        public String validatePassword (@NonNull String email, @NonNull String password){
            String numericCharacters = "(.*[0-9].*)";
            String lowerCaseCharacters = "(.*[a-z].*)";
            String upperCaseCharacters = "(.*[A-Z].*)";
            String specialCharacters = "(.*[@!#$%&].*)";

            if (password.equals(""))
                return "Password cannot be empty";

            else if (password.equals(email))
                return "Email and password cannot be the same";

            else if (password.length() < 8 || password.length() > 20)
                return "Password needs to be between 8 and 20 characters";

            else if (!password.matches(numericCharacters))
                return "Password must contain one number 0-9";

            else if (!password.matches(lowerCaseCharacters))
                return "Password must contain one lower case letter a-z";

            else if (!password.matches(upperCaseCharacters))
                return "Password must contain one upper case letter A-Z";

            else if (!password.matches(specialCharacters))
                return "Password must contain special character @,!,#,$,%,&";

            else
                return "Valid Password";
        }
}
