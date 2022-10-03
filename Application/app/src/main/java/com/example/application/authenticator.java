package com.example.application;

import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class authenticator extends AppCompatActivity {
    String customerOrChef;
    TextView emailPasswordErrorMessages;
    EditText editTextEmail;
    EditText editTextPassword;
    public authenticator(TextView emailPasswordErrorMessages, EditText editTextEmail, EditText editTextPassword) {
        this.emailPasswordErrorMessages = emailPasswordErrorMessages;
        this.editTextEmail = editTextEmail;
        this.editTextPassword = editTextPassword;
    }

    public boolean signIn(String[] credentials) {
        String email = credentials[0];
        String emailValidation = validateEmail(email);
        String password = credentials[1];
        String passwordValidation = validatePassword(email, password);
        if (!emailValidation.equals("Valid Email"))
            emailPasswordErrorMessages.setText(emailValidation);
        else if (!passwordValidation.equals("Valid Password"))
            emailPasswordErrorMessages.setText(passwordValidation);
        else{
            emailPasswordErrorMessages.setText("");
            return true;
        }
        return false;
    }

    public String[] getCredentials() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        return new String[]{email, password};
    }

    public String validateEmail(@NonNull String email){
        if (email.contains("@"))
            return "Valid Email";
        return "Invalid Email";
    }

    public String validatePassword(@NonNull String email, @NonNull String password){
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
