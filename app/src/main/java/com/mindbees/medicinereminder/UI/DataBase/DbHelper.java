package com.mindbees.medicinereminder.UI.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 14-12-2016.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
//    private static final int RC1_DATABASE = 7;
public static final String DATABASE_NAME = "PillReminder.db";
    public static final String TABLE_TASKS = "reminder";


    public static final String KEY_ID = "id";										 // INTEGER PRIMARY KEY
    public static final String KEY_NAME = "name"; 									 // TEXT
    public static final String KEY_COMPLETION = "completion"; 						 // INTEGER, indirectly boo
    public static final String KEY_TIME_HOUR =  "hour";
    public static final String KEY_TIME_MINUTES = "minutes";
    public static final String KEY_IS_REPEATING = "isRepeating"; 					 // INTEGER, indirectly boolean
    public static final String KEY_REPEAT_TYPE = "repeatType"; 						 // INTEGER
    public static final String KEY_REPEAT_INTERVAL = "repeatInterval"; 				 // INTEGER
    public static final String KEY_CREATED_DATE="createdDate";
    public static final String KEY_FROM_DATE = "fromDate"; 					 // DATETIME
    public static final String KEY_MODIFICATION_DATE = "modificationDate"; 			 // DATETIME
    public static final String KEY_DUE_DATE = "dueDate";// DATETIME
    public static final String KEY_PID="pid";
    public static final String KEY_NOTES = "notes"; 								 // TEXT, can be null


    public DbHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }
    private void createReminderTable(SQLiteDatabase db) {
        String create_tasks_table = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_COMPLETION + " INTEGER,"
                + KEY_TIME_HOUR + " INTEGER,"
                + KEY_TIME_MINUTES + " INTEGER,"
                + KEY_IS_REPEATING + " INTEGER,"
                + KEY_REPEAT_TYPE + " INTEGER,"
                + KEY_REPEAT_INTERVAL + " INTEGER,"
                + KEY_CREATED_DATE + " DATE,"
                + KEY_FROM_DATE + " DATE,"
                + KEY_MODIFICATION_DATE + " DATE,"
                + KEY_DUE_DATE + " DATE,"
                + KEY_NOTES + " TEXT,"
                + KEY_PID + " INTEGER)";

        db.execSQL(create_tasks_table);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createReminderTable(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
