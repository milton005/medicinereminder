package com.mindbees.medicinereminder.UI.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.sax.RootElement;

import com.mindbees.medicinereminder.UI.Model.Reminder;

import java.util.ArrayList;

/**
 * Created by User on 15-12-2016.
 */

public class DataSource  {
    private SQLiteDatabase db;
    private DbHelper handler;
    private static  DataSource instance;
    private DataSource(Context context)
    {
        handler=new DbHelper(context);
    }
    public static synchronized DataSource getInstance(Context context) {
        instance = new DataSource(context);
        return instance;
    }

    private void open() throws SQLException {
        db = handler.getWritableDatabase();
    }
    private void close() {
        handler.close();
    }
    private void  read()
    {
        db=handler.getReadableDatabase();
    }
    public void addReminder(Reminder reminder) {
        open();
        ContentValues values = new ContentValues();
        values.put(DbHelper.KEY_ID, reminder.getId());
        values.put(DbHelper.KEY_NAME, reminder.getName());
        values.put(DbHelper.KEY_COMPLETION, reminder.isCompleted());
        values.put(DbHelper.KEY_TIME_HOUR,reminder.getHour());
        values.put(DbHelper.KEY_TIME_MINUTES,reminder.getMinutes());
//        values.put(DatabaseHandler.KEY_PRIORITY, task.getPriority());
//        values.put(DatabaseHandler.KEY_CATEGORY, task.getCategory());
//        values.put(DatabaseHandler.KEY_HAS_DUE_DATE, task.hasDateDue());
//        values.put(DatabaseHandler.KEY_HAS_FINAL_DUE_DATE, task.hasFinalDateDue());
        values.put(DbHelper.KEY_IS_REPEATING, reminder.isRepeating());
        values.put(DbHelper.KEY_REPEAT_TYPE, reminder.getRepeatType());
        values.put(DbHelper.KEY_REPEAT_INTERVAL, reminder.getRepeatInterval());
        values.put(DbHelper.KEY_CREATED_DATE, reminder.getDateCreated());
        values.put(DbHelper.KEY_FROM_DATE, reminder.getFromDate());
        values.put(DbHelper.KEY_MODIFICATION_DATE, reminder.getDateModified());
        values.put(DbHelper.KEY_DUE_DATE, reminder.getDateDue());

        values.put(DbHelper.KEY_NOTES, reminder.getNotes());
        values.put(DbHelper.KEY_PID, reminder.getPid());

        // Inserting Row
        db.insert(DbHelper.TABLE_TASKS, null, values);
        close();
    }
    /**
     * Update the database information about a task
     * @param
     * @return number of rows affected
     */
    public int updateReminder( Reminder reminder) {
        open();
        ContentValues values = new ContentValues();
        values.put(DbHelper.KEY_NAME, reminder.getName());
        values.put(DbHelper.KEY_COMPLETION, reminder.isCompleted());
        values.put(DbHelper.KEY_TIME_HOUR,reminder.getHour());
        values.put(DbHelper.KEY_TIME_MINUTES,reminder.getMinutes());
//        values.put(DatabaseHandler.KEY_CATEGORY, task.getCategory());
//        values.put(DbHelper.KEY_HAS_DUE_DATE, task.hasDateDue());
//        values.put(DatabaseHandler.KEY_HAS_FINAL_DUE_DATE, task.hasFinalDateDue());
        values.put(DbHelper.KEY_IS_REPEATING, reminder.isRepeating());
        values.put(DbHelper.KEY_REPEAT_TYPE, reminder.getRepeatType());
        values.put(DbHelper.KEY_REPEAT_INTERVAL, reminder.getRepeatInterval());
        values.put(DbHelper.KEY_CREATED_DATE, reminder.getDateCreated());
        values.put(DbHelper.KEY_FROM_DATE, reminder.getFromDate());
        values.put(DbHelper.KEY_MODIFICATION_DATE, reminder.getDateModified());
        values.put(DbHelper.KEY_DUE_DATE, reminder.getDateDue());

        values.put(DbHelper.KEY_NOTES, reminder.getNotes());
        values.put(DbHelper.KEY_PID, reminder.getPid());
        // updating row
        int i = db.update(DbHelper.TABLE_TASKS, values,
                DbHelper.KEY_ID + " = ?", new String[] { String.valueOf(reminder.getId()) });
        close();
        return i;
    }
    public void deleteReminder(int id) {
        open();
        db.delete(DbHelper.TABLE_TASKS,
                DbHelper.KEY_ID + " = " + id, null);
        close();
    }
    public int deleteAllReminders() {
        open();
        int i = db.delete(DbHelper.TABLE_TASKS, null, null);
        close();
        return i;
    }
    public Reminder getReminder(int id) {
        open();
        Cursor cursor = db.query(DbHelper.TABLE_TASKS, new String[] {
                        DbHelper.KEY_ID,
                        DbHelper.KEY_NAME,
                        DbHelper.KEY_COMPLETION,
                        DbHelper.KEY_TIME_HOUR,
                        DbHelper.KEY_TIME_MINUTES,
                        DbHelper.KEY_IS_REPEATING,
                        DbHelper.KEY_REPEAT_TYPE,
                        DbHelper.KEY_REPEAT_INTERVAL,
                        DbHelper.KEY_CREATED_DATE,
                        DbHelper.KEY_FROM_DATE,
                        DbHelper.KEY_MODIFICATION_DATE,
                        DbHelper.KEY_DUE_DATE,
                        DbHelper.KEY_NOTES,
                        DbHelper.KEY_PID},
                DbHelper.KEY_ID + " = " + id,
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            Reminder reminder = new Reminder(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)>0,
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5)>0,
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getString(12),
                    cursor.getInt(13));
            close();
            cursor.close();
            return reminder;
        } else {
            close();
            cursor.close();
            return null;
        }
    }
    public ArrayList<Reminder> getAllReminder() {
        ArrayList<Reminder> ReminderList = new ArrayList<Reminder>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + DbHelper.TABLE_TASKS;

        open();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Reminder task = new Reminder(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2)>0,
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5)>0,
                        cursor.getInt(6),
                        cursor.getInt(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        cursor.getString(12),
                        cursor.getInt(13));


                // Adding task to list
                ReminderList.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        close();
        // return task list
        return ReminderList;
    }
    public int getNextID(String table) {

        String selectQuery = "SELECT MAX(" + DbHelper.KEY_ID +
                ") FROM " + table;
        open();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            int i = cursor.getInt(0) + 1;
            cursor.close();
            close();
            return i;
        }
        else{
            cursor.close();
            close();
            return 1;
        }
    }
}
