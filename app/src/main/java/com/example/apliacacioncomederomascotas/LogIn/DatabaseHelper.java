package com.example.apliacacioncomederomascotas.LogIn;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "login.db";
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_EMAIL = "email";

    public static final String TABLE_CALENDAR = "calendar";
    public static final String COLUMN_CALENDAR_ID = "calendar_id";
    public static final String COLUMN_CALENDAR_NAME = "name";
    public static final String COLUMN_CALENDAR_REPETITIONS = "repetitions";
    public static final String COLUMN_CALENDAR_HOUR = "hour";

    public static final String TABLE_CREDENTIALS = "credentials";
    public static final String COLUMN_CREDENTIALS_ID = "credentials_id";
    public static final String COLUMN_CREDENTIALS_FIRST_NAME = "credentials_first_name";
    public static final String COLUMN_CREDENTIALS_LAST_NAME = "credentials_last_name";
    public static final String COLUMN_CREDENTIALS_USERNAME = "credentials_username";

    public static final String TABLE_DISPENSATIONS = "dispensations";
    public static final String COLUMN_DISPENSATIONS_ID = "dispensations_id";
    public static final String COLUMN_DISPENSATIONS_DATE = "dispensations_date";
    public static final String COLUMN_DISPENSATIONS_HOUR = "dispensations_hour";
    public static final String COLUMN_DISPENSATIONS_CALENDAR_ID = "dispensations_calendar_id";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_USERNAME + " TEXT PRIMARY KEY," +
                    COLUMN_PASSWORD + " TEXT," +
                    COLUMN_FIRST_NAME + " TEXT," +
                    COLUMN_LAST_NAME + " TEXT," +
                    COLUMN_EMAIL + " TEXT)";

    private static final String SQL_CREATE_CALENDAR_TABLE =
            "CREATE TABLE " + TABLE_CALENDAR + " (" +
                    COLUMN_CALENDAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_CALENDAR_NAME + " TEXT," +
                    COLUMN_CALENDAR_REPETITIONS + " INTEGER," +
                    COLUMN_CALENDAR_HOUR + " TEXT," +
                    COLUMN_CREDENTIALS_USERNAME + " TEXT, " +
                    "FOREIGN KEY(" + COLUMN_CREDENTIALS_USERNAME + ") REFERENCES " + TABLE_CREDENTIALS + "(" + COLUMN_USERNAME + "))";

    private static final String SQL_CREATE_CREDENTIALS_TABLE =
            "CREATE TABLE " + TABLE_CREDENTIALS + " (" +
                    COLUMN_CREDENTIALS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_CREDENTIALS_FIRST_NAME + " TEXT," +
                    COLUMN_CREDENTIALS_LAST_NAME + " TEXT," +
                    COLUMN_USERNAME + " TEXT, " +
                    "FOREIGN KEY(" + COLUMN_USERNAME + ") REFERENCES " + TABLE_NAME + "(" + COLUMN_USERNAME + "))";

    private static final String SQL_CREATE_DISPENSATIONS_TABLE =
            "CREATE TABLE " + TABLE_DISPENSATIONS + " (" +
                    COLUMN_DISPENSATIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_DISPENSATIONS_DATE + " TEXT," +
                    COLUMN_DISPENSATIONS_HOUR + " TEXT," +
                    COLUMN_DISPENSATIONS_CALENDAR_ID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_DISPENSATIONS_CALENDAR_ID + ") REFERENCES " + TABLE_CALENDAR + "(" + COLUMN_CALENDAR_ID + "))";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String SQL_DELETE_CALENDAR_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_CALENDAR;
    private static final String SQL_DELETE_CREDENTIALS_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_CREDENTIALS;
    private static final String SQL_DELETE_DISPENSATIONS_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_DISPENSATIONS;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_CALENDAR_TABLE);
        db.execSQL(SQL_CREATE_CREDENTIALS_TABLE);
        db.execSQL(SQL_CREATE_DISPENSATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_CALENDAR_TABLE);
        db.execSQL(SQL_DELETE_CREDENTIALS_TABLE);
        db.execSQL(SQL_DELETE_DISPENSATIONS_TABLE);
        onCreate(db);
    }
    public boolean recreateTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
        return true;
    }
    //Usuarios
    public boolean updateUserInfo(String username, String newFirstName, String newLastName, String newEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, newFirstName);
        values.put(COLUMN_LAST_NAME, newLastName);
        values.put(COLUMN_EMAIL, newEmail);
        int rowsAffected = db.update(TABLE_NAME, values, COLUMN_USERNAME + "=?", new String[]{username});
        return rowsAffected > 0;
    }
    public String[] getUserInfo(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] userInfo = null;

        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_EMAIL},
                COLUMN_USERNAME + "=?", new String[]{username}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
            @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
            userInfo = new String[]{firstName, lastName, email};
            cursor.close();
        }

        return userInfo;
    }
    public boolean updatePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);
        int rowsAffected = db.update("users", values, "username = ?", new String[]{username});
        return rowsAffected > 0;
    }

}
