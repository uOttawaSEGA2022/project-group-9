package com.example.databasedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String databaseName = "MyCompanyDB";
    static final int databaseVersion = 1;

    static final String databaseTable = "Users";
    static final String userID = "ID";
    static final String userName = "Name";
    static final String userPassword = "Password";

    public static final String createDBQuery = "CREATE TABLE " +  databaseTable + " ( " + userID + " INTEGER PRIMARY KEY AUTOINCREMENT " + userID + "Text Not Null" + userPassword + ");";



    public DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createDBQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table if EXISTS " + databaseTable);
    }
}
