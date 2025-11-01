package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Userdata.db";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create table users(username TEXT primary key, password TEXT)");
        MyDB.execSQL("create table expenses(id INTEGER primary key autoincrement, username TEXT, name TEXT, amount REAL, category TEXT, date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int ii) {
        MyDB.execSQL("drop table if exists users");
    }

    // Insert new user
    public Boolean insertData(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long result = MyDB.insert("users", null, values);
        return result != -1;
    }

    public Boolean insertExpense(String username, String name, double amount, String category, String date) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("name", name);
        values.put("amount", amount);
        values.put("category", category);
        values.put("date", date);
        long result = MyDB.insert("expenses", null, values);
        return result != -1;
    }

    public Cursor getExpenses(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        return MyDB.rawQuery("select * from expenses where username = ?", new String[]{username});
    }

    // Check if username exists
    public Boolean checkUsername(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        return cursor.getCount() > 0;
    }


    // Check username + password
    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where username = ? and password = ?", new String[]{username, password});
        return cursor.getCount() > 0;
    }

    // Delete a user by username
    public Boolean deleteUser(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        int result = MyDB.delete("users", "username = ?", new String[]{username});
        return result > 0; // true if deleted
    }

    // Update expense
    public boolean updateExpense(int id, String name, double amount, String category, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("amount", amount);
        values.put("category", category);
        values.put("date", date);

        int result = db.update("expenses", values, "id=?", new String[]{String.valueOf(id)});
        return result > 0; // true if at least one row updated
    }
    public boolean deleteExpense(String username, String expenseName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("expenses", "username=? AND name=?", new String[]{username, expenseName});
        return result > 0;
    }

}
