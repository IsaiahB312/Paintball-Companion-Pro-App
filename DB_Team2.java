package com.example.paintballcompanionpro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB_Team2 extends SQLiteOpenHelper {

    public DB_Team2(Context context) {
        super(context, "team2.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE team2(NAME TEXT PRIMARY KEY)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int j) {
        DB.execSQL("DROP TABLE IF EXISTS team2");
    }

    public Boolean insertPlayer(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        long result = DB.insert("team2", null, contentValues);

        if (result == -1) {
            return false;
        }

        else {
            return true;
        }
    }

    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM team2", null);
        return cursor;
    }

    public Boolean deleteData(String Name) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM team2 WHERE NAME = ?", new String[]{Name});
        if (cursor.getCount() > 0) {
            long result = myDB.delete("team2","NAME = ?", new String[]{Name});
            if (result == -1) return false;
            else
                return true;
        }
        else
            return false;
    }
}
