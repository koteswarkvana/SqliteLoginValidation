package com.koti.apple.loginsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SaveLoginDatabase extends SQLiteOpenHelper {
    //database helper
    public static final String DATABASE_NAME_ = "users.db";
    public static final int DATABASE_VERSION = 1;

    //User table
    public static final String TABLE_USERS = "users";

    //users table rows
    public static final String USERS_COLUMN_ID = "ID";
    public static final String USERS_COLUMN_NAME = "name";
    public static final String USERS_COLUMN_PASSWORD = "password";

    //quarries
    //create
    public static final String CREATE_TABLES_USERS = "create table " + TABLE_USERS +
            "(" +
            USERS_COLUMN_ID + " integer primary key," +
            USERS_COLUMN_NAME + " text," +
            USERS_COLUMN_PASSWORD + " text)";

    //drop
    public static final String DROP_TABLES_USERS = "DROP TABLE IF EXISTS " + TABLE_USERS;

    //select rows
    public static final String SELECT_ALL_USERS = "SELECT * FROM " + TABLE_USERS;

    //array of columns
    private static String[] USER_COLUMNS = {USERS_COLUMN_ID, USERS_COLUMN_NAME, USERS_COLUMN_PASSWORD};


    public SaveLoginDatabase(Context context) {
        super(context, DATABASE_NAME_, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLES_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //delete old tables
        db.execSQL(DROP_TABLES_USERS);
        //creating tables again
        onCreate(db);
    }

    /**
     * it inserts user to table
     *
     * @param sqLiteDatabase writable db
     * @param name
     * @param password
     */
    public void addUser(SQLiteDatabase sqLiteDatabase, String name, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COLUMN_NAME, name);
        contentValues.put(USERS_COLUMN_PASSWORD, password);
        //insert
        sqLiteDatabase.insert(TABLE_USERS, null, contentValues);
        Log.e("info >> ", "addUser : inserted ");
    }

    public String getUserInfo(SQLiteDatabase sqLiteDatabase, String name1, String password1) {
        try {
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT name, password FROM users WHERE name = ? AND password = ?", new String[]{name1, password1});

            // To print the entair table data
//            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM users", null);
            String nameVal = null, passwordVal = null;
            if (cursor != null) {
                int i = 0;
                String nameCol[] = new String[cursor.getCount()];
                String passwordCol[] = new String[cursor.getCount()];
                if (cursor.moveToFirst()) {
                    do {
                        nameVal = cursor.getString(cursor.getColumnIndex("name"));
                        passwordVal = cursor.getString(cursor.getColumnIndex("password"));
                        Log.e("info >> ", "name  :  " + nameVal);
                        Log.e("info >> ", "password  :  " + passwordVal);
                    } while (cursor.moveToNext());
                    cursor.close();
                }
            }
            return nameVal + passwordVal;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * returns all users
     */
  /*  public List<UserModel> getAllUsers(SQLiteDatabase sqLiteDatabase) {
        List<UserModel> userModels = new ArrayList<>();
        //get cur
        Cursor cursor = sqLiteDatabase.query(TABLE_USERS, USER_COLUMNS, null, null, null, null, USERS_COLUMN_ID + " ASC");
        while (cursor.moveToNext()) {
            UserModel userModel = new UserModel();
            userModel.setId(cursor.getInt(cursor.getColumnIndex(USERS_COLUMN_ID)));
            userModel.setName(cursor.getString(cursor.getColumnIndex(USERS_COLUMN_NAME)));
            userModel.setEmail(cursor.getString(cursor.getColumnIndex(USERS_COLUMN_EMAIL)));
            userModel.setMobile(cursor.getString(cursor.getColumnIndex(USERS_COLUMN_MOBILE)));
            userModels.add(userModel);

        }

        return userModels;
    }*/
    public void updateUser(SQLiteDatabase sqLiteDatabase, int id, String name, String email, String mobile) {
        /*ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COLUMN_NAME, name);
        contentValues.put(USERS_COLUMN_MOBILE, mobile);
        contentValues.put(USERS_COLUMN_EMAIL, email);
        sqLiteDatabase.update(
                TABLE_USERS,
                contentValues,
                USERS_COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});*/
    }

    public void deleteUser(SQLiteDatabase sqLiteDatabase, int id) {
        sqLiteDatabase.delete(
                TABLE_USERS,
                USERS_COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});

    }

    public void deleteAllUsrs(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.delete(TABLE_USERS, null, null);
    }
}