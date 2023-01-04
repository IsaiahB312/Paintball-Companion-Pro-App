package com.example.paintballcompanionpro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_Login extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DB_Login(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("CREATE TABLE USERS(Username TEXT PRIMARY KEY, Password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int j) {
        myDB.execSQL("DROP TABLE IF EXISTS USERS");
    }

    public Boolean insertData(String Username, String Password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username", Username);
        contentValues.put("Password", Password);
        long result = myDB.insert("USERS", null, contentValues);
        if(result == -1) return false;
        else
            return true;
    }

    public Boolean checkUsername(String Username) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM USERS WHERE USERNAME = ?", new String[] {Username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkUsernameAndPassword(String Username, String Password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM USERS WHERE Username = ? AND Password = ?", new String[] {Username, Password});
        if (cursor.getCount() > 0) return true;
        else
            return false;
    }
}
