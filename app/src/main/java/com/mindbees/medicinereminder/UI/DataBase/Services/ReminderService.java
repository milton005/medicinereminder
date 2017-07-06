package com.mindbees.medicinereminder.UI.DataBase.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.mindbees.medicinereminder.UI.DataBase.DataSource;
import com.mindbees.medicinereminder.UI.Model.Reminder;
import com.mindbees.medicinereminder.UI.Model.TaskAlarm;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by User on 15-12-2016.
 */

public class ReminderService extends Service {
//    public ReminderService() {super("ReminderService");
//    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DataSource db = DataSource.getInstance(this); //get access to the instance of TasksDataSource
        TaskAlarm alarm = new TaskAlarm();


        List<Reminder> tasks = db.getAllReminder(); //Get a list of all the tasks there
        for (Reminder task : tasks) {
            try{
                alarm.cancelAlarm(this, task.getId()); // Cancel existing alarm



                //Procrastinator and Reminder alarm
//            if(task.isPastDue()){
//                alarm.setReminder(this, task.getId());
//            }

                //handle repeat alarms
//            if(task.isRepeating() && task.isCompleted()){
//                alarm.setRepeatingAlarm(this, task.getId());
//            }
                Calendar cal = Calendar.getInstance();
                String[]date=task.getDateDue().split("/");
                int day=Integer.parseInt(date[0]);
                int month=Integer.parseInt(date[1]);
                int year=Integer.parseInt(date[2]);
                cal.set(Calendar.DAY_OF_MONTH,day);
                cal.set(Calendar.MONTH,((month-1)));
                cal.set(Calendar.YEAR,year);
                cal.set(Calendar.HOUR_OF_DAY,task.getHour());
                cal.set(Calendar.MINUTE,task.getMinutes());
                cal.set(Calendar.SECOND,0);
                if(task.getRepeatType()==3||(task.getDateDue().equals(task.getFromDate())&&task.getRepeatInterval()!=0))
                {

                    Calendar cale = Calendar.getInstance();


                    cale.set(Calendar.DAY_OF_MONTH, day);
                    cale.set(Calendar.MONTH, (month - 1));
                    cale.set(Calendar.YEAR, year);
                    cale.set(Calendar.MINUTE, 59);
                    cale.set(Calendar.HOUR_OF_DAY, 23);
                    if (cale.getTimeInMillis() >= System.currentTimeMillis()) {
                        alarm.setAlarm(getApplicationContext(), task);
                    }

                }
                else {
                    //regular alarms

                    if (!task.isCompleted() && (cal.getTimeInMillis() >= System.currentTimeMillis())) {
                        alarm.setAlarm(getApplicationContext(), task);
                    }
                }
            }catch (Exception e)
            {

            }

        }
        return super.onStartCommand(intent, flags, startId);
    }
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        DataSource db = DataSource.getInstance(this); //get access to the instance of TasksDataSource
//     TaskAlarm alarm = new TaskAlarm();
//
//
//        List<Reminder> tasks = db.getAllReminder(); //Get a list of all the tasks there
//        for (Reminder task : tasks) {
//          try{
//              alarm.cancelAlarm(this, task.getId()); // Cancel existing alarm
//
//
//
//            //Procrastinator and Reminder alarm
////            if(task.isPastDue()){
////                alarm.setReminder(this, task.getId());
////            }
//
//            //handle repeat alarms
////            if(task.isRepeating() && task.isCompleted()){
////                alarm.setRepeatingAlarm(this, task.getId());
////            }
//            Calendar cal = Calendar.getInstance();
//            String[]date=task.getDateDue().split("/");
//            int day=Integer.parseInt(date[0]);
//            int month=Integer.parseInt(date[1]);
//            int year=Integer.parseInt(date[2]);
//            cal.set(Calendar.DAY_OF_MONTH,day);
//            cal.set(Calendar.MONTH,((month-1)));
//            cal.set(Calendar.YEAR,year);
//            cal.set(Calendar.HOUR_OF_DAY,task.getHour());
//            cal.set(Calendar.MINUTE,task.getMinutes());
//            cal.set(Calendar.SECOND,0);
//            if(task.getRepeatType()==3||(task.getDateDue().equals(task.getFromDate())&&task.getRepeatInterval()!=0))
//            {
//
//                    Calendar cale = Calendar.getInstance();
//
//
//                    cale.set(Calendar.DAY_OF_MONTH, day);
//                    cale.set(Calendar.MONTH, (month - 1));
//                    cale.set(Calendar.YEAR, year);
//                    cale.set(Calendar.MINUTE, 59);
//                    cale.set(Calendar.HOUR_OF_DAY, 23);
//                    if (cale.getTimeInMillis() >= System.currentTimeMillis()) {
//                        alarm.setAlarm(getApplicationContext(), task);
//                    }
//
//            }
//            else {
//                //regular alarms
//
//                if (!task.isCompleted() && (cal.getTimeInMillis() >= System.currentTimeMillis())) {
//                    alarm.setAlarm(getApplicationContext(), task);
//                }
//            }
//          }catch (Exception e)
//          {
//
//          }
//
//        }
//        super.onHandleIntent(intent);
//    }
}
