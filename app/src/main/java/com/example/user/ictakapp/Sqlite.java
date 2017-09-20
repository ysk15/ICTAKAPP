package com.example.user.ictakapp;

/**
 * Created by Ociuz 2 on 10/24/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by user on 22-10-2016.
 */
public class Sqlite extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ictak.db";
    public int s = 0;
    String idno;

    public Sqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (mtd(db, "tbuser") == true) {

        } else {
            String q = "CREATE TABLE tbuser" + "(id text)";
            db.execSQL(q);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Boolean mtd(SQLiteDatabase db, String tablename) {
        if (tablename == null || db == null || !db.isOpen()) {
            return false;
        }
        Cursor c = db.rawQuery("SELECT COUNT (*) FROM sqlite_master WHERE TYPE=? AND NAME=?", new String[]{"table", "tbuser"});

        if (!c.moveToFirst()) {
            return false;
        }
        int count = c.getInt(0);
        c.close();
        return count > 0;

    }

    public Boolean mtd1(SQLiteDatabase db, String tablename) {
        if (tablename == null || db == null || !db.isOpen()) {
            return false;
        }
        Cursor c = db.rawQuery("SELECT COUNT (*) FROM sqlite_master WHERE TYPE=? AND NAME=?", new String[]{"table", "tbdelivery"});

        if (!c.moveToFirst()) {
            return false;
        }
        int count = c.getInt(0);
        c.close();
        return count > 0;

    }


    public void userinsert( String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        db.insert("tbuser", null, cv);
        db.close();
    }

    public String[] CheckLogin() {
        String[] details;
        String username, usertype;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cu = db.rawQuery("SELECT * FROM tbuser", null);
        if (cu.moveToFirst()) {

            do {
                username = cu.getString(0);
                details = new String[]{username};
            }
            while (cu.moveToNext());
        } else {
            details = new String[]{""};
        }
        cu.close();
        db.close();
        return details;
    }

    public void delete(){
        SQLiteDatabase sd = this.getWritableDatabase();
        sd.delete("tbuser",null,null);
    }
}
