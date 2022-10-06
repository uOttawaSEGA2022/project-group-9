package com.example.databasedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import java.sql.SQLDataException;

public class DatabaseManager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager (Context ctx) {
        context = ctx;
    }

    public DatabaseManager open() throws SQLDataException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void insert (String username, String password ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.userName,username);
        contentValues.put(DatabaseHelper.userPassword,password);
        database.insert(DatabaseHelper.databaseTable,null,contentValues);

    }

    public Cursor fetch() {
        String[] columns = new String[] {DatabaseHelper.userID,DatabaseHelper.userName,DatabaseHelper.userPassword};
        Cursor cursor = database.query(DatabaseHelper.databaseTable,columns,null,null,null,null,null);

        if(cursor!=null) {
            cursor.moveToFirst();
        }

            return cursor;
    }

    public int update(long id, String username, String password ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.userName,username);
        contentValues.put(DatabaseHelper.userPassword,password);
        int ret = database.update(DatabaseHelper.databaseTable,contentValues,DatabaseHelper.userID + "=" + id,null);
        return ret;

    }

    public void delete (long id) {
        database.delete(DatabaseHelper.databaseTable,DatabaseHelper.userID+ "=" + id,null);
    }
}
