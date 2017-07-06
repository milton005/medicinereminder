package com.mindbees.medicinereminder.UI.Model;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.view.View;

import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.DataBase.DataSource;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by User on 15-12-2016.
 */

public class TaskAlarm {
    int ID;
    int Hour;
    int Minutes;
    String From_date;
    String to_date;
    String REmind;
    PendingIntent pi;
    String content;
    int DELAY;

    public void cancelAlarm(Context context,int id)
    {
//        DataSource db = DataSource.getInstance(context);
//        ID=id;
//        Intent notificationIntent = new Intent(context, OnAlarmReceiver.class);
//        notificationIntent.putExtra(OnAlarmReceiver.NOTIFICATION_ID, ID);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        pendingIntent.cancel();
//        alarmManager.cancel(pendingIntent);
//        PendingIntent pi = getPendingIntent(context, id);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.cancel(pi);
//        pi.cancel();
        Intent notificationIntent = new Intent(context, OnAlarmReceiver.class);
        notificationIntent.putExtra(OnAlarmReceiver.NOTIFICATION_ID, ID);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        pendingIntent.cancel();
        alarmManager.cancel(pendingIntent);

        //cancel Reminder Alarm
//        Intent intent =  new Intent(context, OnAlarmReceiver.class)
//                .putExtra(Task.EXTRA_TASK_ID, id)
//                .putExtra(TaskAlarm.ALARM_EXTRA, SettingsActivity.REMINDER_TIME);
//        pi = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        alarmManager.cancel(pi);
//        pi.cancel();
//
//        //cancel procrastinator Alarm
//        intent =  new Intent(context, OnAlarmReceiver.class)
//                .putExtra(Task.EXTRA_TASK_ID, id)
//                .putExtra(TaskAlarm.ALARM_EXTRA, SettingsActivity.ALARM_TIME);
//        pi = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        alarmManager.cancel(pi);
//        pi.cancel();
    }
    public void setAlarm(Context context, Reminder task){
//    {   	DataSource db = DataSource.getInstance(context);
//
//        Reminder task = db.getReminder(id);
        ID=task.getId();
         content=task.getName();
        boolean is_repeating=task.isRepeating();
        int interval=0;
        if(is_repeating)
        {
            interval=task.getRepeatInterval();

        }
        From_date=task.getFromDate();
        to_date=task.getDateDue();
        Hour=task.getHour();
        Minutes=task.getMinutes();
        setReminder(context,interval);

//
//    scheduleNotification(context,getNotification(context,content),interval);



    }

    private void setReminder(Context context,int interval) {
        DataSource db=DataSource.getInstance(context);
        Reminder task=db.getReminder(ID);
        Intent notificationIntent = new Intent(context, OnAlarmReceiver.class);
        notificationIntent.putExtra(OnAlarmReceiver.NOTIFICATION_ID, ID);
        notificationIntent.putExtra("Title",content);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (interval==0)
        {
            if(task.getRepeatType()==2) {
                DELAY=0;
                REmind=task.getDateCreated();
                if (!REmind.isEmpty())
                {
                    int d= Integer.parseInt(REmind);
                    DELAY=d;
                }
                Calendar cal = Calendar.getInstance();
                String[] date = From_date.split("/");
                int day = Integer.parseInt(date[0]);
                int month = (Integer.parseInt(date[1]) - 1);
                int year = Integer.parseInt(date[2]);
                cal.set(Calendar.DAY_OF_MONTH, day);
                cal.set(Calendar.MONTH, (month));
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.HOUR_OF_DAY, Hour-DELAY);
                cal.set(Calendar.MINUTE, Minutes);
                cal.set(Calendar.SECOND, 0);
                DELAY=0;
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);   // only for KITKAT and newer versions
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                }
            }
            else
            {
                Calendar cal = Calendar.getInstance();
                String[] date = From_date.split("/");
                int day = Integer.parseInt(date[0]);
                int month = (Integer.parseInt(date[1]) - 1);
                int year = Integer.parseInt(date[2]);
                cal.set(Calendar.DAY_OF_MONTH, day);
                cal.set(Calendar.MONTH, (month));
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.HOUR_OF_DAY, Hour);
                cal.set(Calendar.MINUTE, Minutes);
                cal.set(Calendar.SECOND, 0);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);   // only for KITKAT and newer versions
                } else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                }
            }
        }
        else
        {
            Calendar cal = Calendar.getInstance();
            String[]date=From_date.split("/");
            int day=Integer.parseInt(date[0]);
            int month=(Integer.parseInt(date[1])-1);
            int year=Integer.parseInt(date[2]);
            cal.set(Calendar.DAY_OF_MONTH,day);
            cal.set(Calendar.MONTH,(month));
            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.HOUR_OF_DAY,Hour);
            cal.set(Calendar.MINUTE,Minutes);
            cal.set(Calendar.SECOND,0);
            long delay=interval * 60 * 1000;

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if(task.getRepeatType()==3) {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, (cal.getTimeInMillis() + delay), delay, pendingIntent);
                }
                else {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() , delay, pendingIntent);
                }



            }
            else
            {
                if(task.getRepeatType()==3) {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, (cal.getTimeInMillis() + delay), delay, pendingIntent);
                }
                else {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() , delay, pendingIntent);
                }
            }

        }

    }

    public void updateAlarm(Context context,Reminder task)

    {

        ID=task.getId();
        Intent notificationIntent = new Intent(context, OnAlarmReceiver.class);
        notificationIntent.putExtra(OnAlarmReceiver.NOTIFICATION_ID, ID);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        pendingIntent.cancel();
        alarmManager.cancel(pendingIntent);

        content=task.getName();
        boolean is_repeating=task.isRepeating();
        int interval=0;
        if(is_repeating)
        {
            interval=task.getRepeatInterval();

        }
        From_date=task.getFromDate();
        to_date=task.getDateDue();
        Hour=task.getHour();
        Minutes=task.getMinutes();

        setReminder(context,interval);

    }
    public void setRepeatingAlarm(Context context,int id)
    {

    }
    public void updateRepeatAlarm(Context context,int id)
    {

    }
    private void scheduleNotification(Context context,Notification notification, int delay) {

        Intent notificationIntent = new Intent(context, OnAlarmReceiver.class);
        notificationIntent.putExtra(OnAlarmReceiver.NOTIFICATION_ID, ID);
        notificationIntent.putExtra(OnAlarmReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (delay==0)
        {
            Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
            String[]date=From_date.split("/");
            int day=Integer.parseInt(date[0]);
            int month=(Integer.parseInt(date[1])-1);
            int year=Integer.parseInt(date[3]);
            cal.set(Calendar.DAY_OF_MONTH,day);
            cal.set(Calendar.MONTH,(month));
            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.HOUR_OF_DAY,Hour);
            cal.set(Calendar.MINUTE,Minutes);
            cal.set(Calendar.SECOND,0);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        }
        else
        {
            Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
            String[]date=From_date.split("/");
            int day=Integer.parseInt(date[0]);
            int month=Integer.parseInt(date[1]);
            int year=Integer.parseInt(date[3]);
            cal.set(Calendar.DAY_OF_MONTH,day);
            cal.set(Calendar.MONTH,(month));
            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.HOUR_OF_DAY,Hour);
            cal.set(Calendar.MINUTE,Minutes);
            cal.set(Calendar.SECOND,0);
            long interval=delay * 60 * 1000;
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),interval,pendingIntent);
        }


    }
    private Notification getNotification(Context context,String content) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
//        builder.setSmallIcon();
        return builder.build();
    }
    public void cancelNotification(Context context, int taskID) {
        NotificationManager notificationManager = getNotificationManager(context);
        notificationManager.cancel(taskID);
    }
    NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
    PendingIntent getPendingIntent(Context context, int id) {
        Intent intent =  new Intent(context, OnAlarmReceiver.class)
                .putExtra(OnAlarmReceiver.NOTIFICATION_ID, id);
        return PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
