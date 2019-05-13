package com.example.shivam.creditmanage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {
    public DB(Context context) {
        super(context, "dummy_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users (_id integer primary key autoincrement, name text, email text, credit integer)");
        sqLiteDatabase.execSQL("create table transfers (from_user text, to_user text, credit_transfered integer)");

    }

    public void insertTransfer(String from,String to,int credit)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("from_user",from);
        contentValues.put("to_user",to);
        contentValues.put("credit_transfered",credit);
        sqLiteDatabase.insert("transfers",null ,contentValues);
    }
    public void insert(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name","Shivam");
        contentValues.put("email","shiv2299@gmail.com");
        contentValues.put("credit",14500);
        sqLiteDatabase.insert("users",null ,contentValues);
        contentValues.put("name","Mitesh");
        contentValues.put("email","mit@gmail.com");
        contentValues.put("credit",15000);
        sqLiteDatabase.insert("users",null ,contentValues);
        contentValues.put("name","Anas");
        contentValues.put("email","ab@outlook.com");
        contentValues.put("credit",12345);
        sqLiteDatabase.insert("users",null ,contentValues);
        contentValues.put("name","Ahmed");
        contentValues.put("email","gagan@yahoo.com");
        contentValues.put("credit",54122);
        sqLiteDatabase.insert("users",null ,contentValues);
        contentValues.put("name","Yagnesh");
        contentValues.put("email","ypatil@gmail.com");
        contentValues.put("credit",35699);
        sqLiteDatabase.insert("users",null ,contentValues);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from users",null);

    }
    public void update(int id,int credit){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update users set credit="+credit+" where _id="+id);
    }
}
