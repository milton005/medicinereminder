package com.mindbees.medicinereminder.UI.Model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateFormat;

import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.MedicineReminderActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by User on 20-12-2016.
 */
public class NotificationHelper {
    public void sendBasicNotification(Context context, Reminder reminder) {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//        boolean vibrate = prefs.getBoolean(SettingsActivity.VIBRATE_ON_ALARM, true);
//        int alarmInterval;
//        int alarmUnits;

//        if (task.hasFinalDateDue()) {
//            alarmInterval = Integer.parseInt(prefs.getString(SettingsActivity.ALARM_TIME, SettingsActivity.DEFAULT_ALARM_TIME));
//            alarmUnits = Calendar.MINUTE;
//        } else {
//            alarmInterval = Integer.parseInt(prefs.getString(SettingsActivity.REMINDER_TIME, SettingsActivity.DEFAULT_REMINDER_TIME));
//            alarmUnits = Calendar.HOUR_OF_DAY;
//        }

//        Calendar next_reminder = GregorianCalendar.getInstance();
//        next_reminder.add(alarmUnits, alarmInterval);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setContentIntent(getPendingIntent(context, reminder.getId()))
                .setContentTitle("Medicine Reminder")
                .setContentText(reminder.getName())
//                .setDefaults(vibrate ? Notification.DEFAULT_ALL : Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS)
                .setSmallIcon(
                        R.drawable.ic_notification )
                .setTicker(reminder.getName())
                .setWhen(System.currentTimeMillis());
        @SuppressWarnings("deprecation")
        Notification notification = builder.getNotification();
        NotificationManager notificationManager = getNotificationManager(context);
        notificationManager.notify(reminder.getId(), notification);
    }
    /**
     * Basic Text Notification with Ongoing flag enabled for Task Butler, using NotificationCompat
     * @param context
     * @param id id of task, call task.getID() and pass it to this parameter
     * @deprecated Use sendBasicNotification for all notifications
     */
//    public void sendPersistentNotification(Context context, Task task) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
//                .setContentText(task.getNotes())
//                .setContentTitle(task.getName())
//                .setSmallIcon(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
//                        R.drawable.ic_notification : R.drawable.ic_notification_deprecated)
//                .setAutoCancel(true)
//                .setContentIntent(getPendingIntent(context,task.getID()))
//                .setWhen(System.currentTimeMillis())
//                .setOngoing(true)
//                .setDefaults(Notification.DEFAULT_ALL);
//        Notification notification = builder.getNotification();
//        NotificationManager notificationManager = getNotificationManager(context);
//        notificationManager.notify(task.getID(), notification);
//    }
    //get a PendingIntent
    PendingIntent getPendingIntent(Context context, int id) {
        Intent intent =  new Intent(context, MedicineReminderActivity.class);
        return PendingIntent.getActivity(context,id,intent,0);
    }
    //get a NotificationManager
    NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * Cancels an existing notification, if user modified the task. Make the
     * actual call from TaskAlarm.cancelNotification(Context, int)
     * @param context
     * @param taskID
     */
    public void cancelNotification(Context context, int taskID) {
        NotificationManager notificationManager = getNotificationManager(context);
        notificationManager.cancel(taskID);
    }

}
