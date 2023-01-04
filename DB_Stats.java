package com.example.paintballcompanionpro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_Stats extends SQLiteOpenHelper {

    public DB_Stats(Context context) {
        super(context, "Stats.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE IF NOT EXISTS STATS(User TEXT PRIMARY KEY, Eliminations INTEGER, Headshots INTEGER, Flag_Captures INTEGER, Deaths INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int j) {
        DB.execSQL("DROP TABLE IF EXISTS STATS");
    }

    public Boolean insertStats(String User, int Eliminations, int Headshots, int Flag_Captures, int Deaths) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("User", User);
        contentValues.put("Eliminations", Eliminations);
        contentValues.put("Headshots", Headshots);
        contentValues.put("Flag_Captures", Flag_Captures);
        contentValues.put("Deaths", Deaths);
        long result = DB.insert("STATS", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean updateEliminations(String User, int Eliminations) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Eliminations", Eliminations);
        Cursor cursor = myDB.rawQuery("SELECT Eliminations FROM STATS WHERE User = ?", new String[]{User});
        if (cursor.getCount() > 0) {
            long result = myDB.update("STATS", contentValues, "User = ?", new String[]{User});
            if (result == -1) return false;
            else
                return true;
        } else
            return false;
    }

    public Boolean updateHeadshots(String User, int Headshots) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Headshots", Headshots);
        Cursor cursor = myDB.rawQuery("SELECT Headshots FROM STATS WHERE User = ?", new String[]{User});
        if (cursor.getCount() > 0) {
            long result = myDB.update("STATS", contentValues, "User = ?", new String[]{User});
            if (result == -1) return false;
            else
                return true;
        } else
            return false;
    }

    public Boolean updateFlag_Captures(String User, int Flag_Captures) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Flag_Captures", Flag_Captures);
        Cursor cursor = myDB.rawQuery("SELECT Flag_Captures FROM STATS WHERE User = ?", new String[]{User});
        if (cursor.getCount() > 0) {
            long result = myDB.update("STATS", contentValues, "User = ?", new String[]{User});
            if (result == -1) return false;
            else
                return true;
        } else
            return false;
    }

    public Boolean updateDeaths(String User, int Deaths) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Deaths", Deaths);
        Cursor cursor = myDB.rawQuery("SELECT Deaths FROM STATS WHERE User = ?", new String[]{User});
        if (cursor.getCount() > 0) {
            long result = myDB.update("STATS", contentValues, "User = ?", new String[]{User});
            if (result == -1) return false;
            else
                return true;
        } else
            return false;
    }

    public Cursor getData(String U) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM STATS WHERE User = ?", new String[]{U});
        return cursor;
    }
}
