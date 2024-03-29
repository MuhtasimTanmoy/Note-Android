package com.example.t.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Note.db";
    private String TAG="DB";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS logIn " +
                        "(id integer primary key, status text)"
        );
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS users " +
                        "(id integer primary key, name text, type text, contactNo,address text, password text )"
        );

        Log.d(TAG,"table created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS logIn");
        db.execSQL("DROP TABLE IF EXISTS users");

        onCreate(db);
    }

    public int numberOfRows(String dbTable){
        SQLiteDatabase db = this.getReadableDatabase();
//        int numRows = (int) DatabaseUtils.queryNumEntries(db, "logIn");
        int numRows = (int) DatabaseUtils.queryNumEntries(db, dbTable);
        return numRows;
    }


    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertLogInStatus(String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", status);
        db.insert("logIn", null, contentValues);

        Log.d(TAG,"inserted  ");

        return true;
    }
//    name text, type text, contactNo,address text, password text
    public boolean insertUserDetails(String name, String contactNo, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("contactNo",contactNo);
        contentValues.put("password",password);


        db.insert("users", null, contentValues);

        Log.d(TAG,"inserted in users");

        return true;

    }

    public Cursor getLoginStatus(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from logIn where id=" + id + "", null);
//            Cursor res =  db.rawQuery( "select * from logIn ", null );
        return res;
    }

    public Cursor getUserDetails(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from users where id=" + id + "", null);
//            Cursor res =  db.rawQuery( "select * from logIn ", null );
        return res;
    }

    public Integer deleteContact(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("logIn",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public Integer deleteUserDetails(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG,"deleted from  users ");
        return db.delete("users",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public boolean updateStatus (Integer id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status",status);
        db.update("logIn", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

}
