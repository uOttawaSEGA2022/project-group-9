package com.example.databasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editUserId;
    EditText editUserName;
    EditText editUserPassword;

    DatabaseManager dbManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUserId = (EditText) findViewById(R.id.editTextID);
        editUserName = (EditText) findViewById(R.id.editTextUsername);
        editUserPassword = (EditText) findViewById(R.id.editTextPassword);

        dbManager = new DatabaseManager(this);

        try {
            dbManager.open();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnInsertPressed(View v) {
        dbManager.insert(editUserName.getText().toString(), editUserPassword.getText().toString());
    }

    public void btnFetchPressed(View v) {

        Cursor cursor = dbManager.fetch();

        if (cursor.moveToFirst()) {
            do {

                @SuppressLint("Range") String ID = cursor.getString(cursor.getColumnIndex(DatabaseHelper.userID));
                @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(DatabaseHelper.userName));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.userPassword));

                Log.i("DATABASE_TAG", "I have read ID :" + ID + " Username: " + username + "password: " + password);


            } while (cursor.moveToNext());
        }

    }

    public void btnUpdatePressed(View v) {
        dbManager.update(Long.parseLong(editUserId.getText().toString()), editUserName.getText().toString(),editUserPassword.getText().toString());
    }

    public void btnDeletePressed(View v) {
        dbManager.delete(Long.parseLong(editUserId.getText().toString()));
    }
}