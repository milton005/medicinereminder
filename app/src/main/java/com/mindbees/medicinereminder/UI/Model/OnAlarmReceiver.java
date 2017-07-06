package com.mindbees.medicinereminder.UI.Model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.AddReminderActivity;
import com.mindbees.medicinereminder.UI.AlertActivity;
import com.mindbees.medicinereminder.UI.DataBase.DataSource;
import com.mindbees.medicinereminder.UI.DataBase.Services.ReminderService;
import com.mindbees.medicinereminder.UI.DataBase.Services.WakefulIntentService;
import com.mindbees.medicinereminder.UI.MedicineReminderActivity;
import com.mindbees.medicinereminder.UTILS.Constants;
import com.mindbees.medicinereminder.UTILS.Util;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by User on 15-12-2016.
 */
public class OnAlarmReceiver  extends BroadcastReceiver{
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";
    String content;
    int type;
    Uri defaultRingtoneUri;
    Reminder reminder;
    String Heading;
    int Cancel;
    int Sfrom;
    int Sto;
    @Override
    public void onReceive(Context context, Intent intent) {


//        WakefulIntentService.acquireStaticLock(context);
//        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

//        Notification notification = intent.getParcelableExtra(NOTIFICATION);
//        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
//        notificationManager.notify(id, notification);
        WakefulIntentService.acquireStaticLock(context); //acquire a partial WakeLock

        //send notification, bundle intent with taskID
//        NotificationHelper notification = new NotificationHelper();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt(NOTIFICATION_ID, 0);

        DataSource db = DataSource.getInstance(context);
        try {
            reminder = db.getReminder(id);
            content = reminder.getName();
            Cancel = reminder.getPid();
            type = reminder.getRepeatType();
            int Sleep_from = Util.getUtils().getPref(Constants.SET_SLEEP_FROM);
            int Sleep_to = Util.getUtils().getPref(Constants.SET_SLEEP_TO);
            switch (Sleep_from) {
                case 0:
                    Sfrom = 21;
                    break;
                case 1:
                    Sfrom = 22;
                    break;
                case 2:
                    Sfrom = 23;
                    break;
                case 3:
                    Sfrom = 0;
                    break;
                default:
                    Sfrom = 21;
                    break;
            }
            switch (Sleep_to) {
                case 0:
                    Sto = 6;
                    break;
                case 1:
                    Sto = 5;
                    break;
                case 2:
                    Sto = 7;
                    break;
                case 3:
                    Sto = 8;
                    break;
                default:
                    Sto = 6;
                    break;
            }


            if (type == 1) {
                Heading = "Medicine Reminder";
            }
            if (type == 2) {
                Heading = "Appointments";
            }
            if (type == 3) {
                Heading = "Water Reminder";
            }
            if (type == 4) {
                Heading = "Night Brushing";
            }
        } catch (Exception e) {

        }
        try {

            Calendar from = Calendar.getInstance();
            if (Sfrom == 0) {
                from.add(Calendar.DATE, 1);
                from.set(Calendar.HOUR_OF_DAY, 0);
                from.set(Calendar.MINUTE, 0);
                from.set(Calendar.SECOND, 0);
            } else {
                from.set(Calendar.HOUR_OF_DAY, Sfrom);
                from.set(Calendar.MINUTE, 0);
                from.set(Calendar.SECOND, 0);
            }
            Calendar to = Calendar.getInstance();
            to.add(Calendar.DATE, 1);
            to.set(Calendar.HOUR_OF_DAY, Sto);
            to.set(Calendar.MINUTE, 0);
            to.set(Calendar.SECOND, 0);
//          if(!(from.getTimeInMillis()>System.currentTimeMillis()&&System.currentTimeMillis()<to.getTimeInMillis())) {
            Calendar delay = Calendar.getInstance();
            int h = Util.getUtils().getPref(Constants.HOUR);
            int m = Util.getUtils().getPref(Constants.MINUTES);

            delay.set(Calendar.HOUR_OF_DAY, h);
            delay.set(Calendar.MINUTE, m + 1);
            delay.set(Calendar.SECOND,20);
            Util.getUtils().savePref(Constants.HOUR,0);
            Util.getUtils().savePref(Constants.MINUTES,0);
            if (delay.getTimeInMillis() < System.currentTimeMillis()) {
//
//
                int cancel_all = Util.getUtils().getPref(Constants.CANCEL_ALL);
                if (cancel_all != 1) {


                    if (Cancel == 0) {
                        if (!content.isEmpty()) {
                            int remindertype = reminder.getRepeatType();

                            Intent resultIntent = new Intent(context, AlertActivity.class);
//                            resultIntent.putExtra(Constants.FRAG_TYPE, remindertype);
                            Util.getUtils().savePref(Constants.ID, id);

                            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                            stackBuilder.addParentStack(MedicineReminderActivity.class);
                            stackBuilder.addNextIntent(resultIntent);
                            PendingIntent resultPendingIntent =
                                    stackBuilder.getPendingIntent(
                                            0,
                                            PendingIntent.FLAG_UPDATE_CURRENT
                                    );
                            Intent push = new Intent();
                            push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            push.setClass(context, AlertActivity.class);
//
                            int auto = Util.getUtils().getPref(Constants.AUTO_CANCEL);
                            boolean autocancel;
                            if (auto == 1) {
                                autocancel = true;
                            } else {
                                autocancel = false;
                            }
                            PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 234,
                                    push, PendingIntent.FLAG_UPDATE_CURRENT);
                            Notification notification = new NotificationCompat.Builder(context)
                                    .setCategory(Notification.CATEGORY_CALL)
                                    .setContentTitle(Heading)
                                    .setContentText(content)
                                    .setAutoCancel(autocancel)
                                    .setSmallIcon(R.drawable.ic_notification)
                                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                                    .setContentIntent(resultPendingIntent)
//                                    .setFullScreenIntent(fullScreenPendingIntent, true)
                                    .setPriority(Notification.PRIORITY_HIGH)
                                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                                    .setLights(Color.RED, 3000, 3000).build();
//
//        notification.sendBasicNotification(context, reminder); // send basic notification
//
//        context.startService(new Intent(context, ReminderService.class)); //start TaskButlerService

            /*    NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.ic_notification)
                                .setContentTitle(Heading)
                                .setContentText(content)
                                .setPriority(NotificationCompat.PRIORITY_MAX)
                                .setDefaults(Notification.DEFAULT_VIBRATE)
                                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                .setCategory(NotificationCompat.CATEGORY_CALL);
//                        .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
//                        .setLights(Color.RED, 3000, 3000);
                NotificationCompat.BigTextStyle inboxStyle =
                        new NotificationCompat.BigTextStyle();

//     inboxStyle.bigText(message);
                mBuilder.setStyle(inboxStyle);
                Intent push = new Intent();
                push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                push.setClass(context, AddReminderActivity.class);
//
                PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 234,
                        push, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder
////                .setContentText("A Heads-Up notification for Lollipop and above")
                        .setFullScreenIntent(fullScreenPendingIntent, true);
                Intent resultIntent = new Intent(context, MedicineReminderActivity.class);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(MedicineReminderActivity.class);
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);*/
                            NotificationManager mNotificationManager =
                                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                            mNotificationManager.notify(id, notification);
                            int Remindertype = Util.getUtils().getPref(Constants.REMINDER_TYPE);
                            if (Remindertype == 0) {
                                defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            }
                            if (Remindertype == 1) {
                                defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                            }
                            if (Remindertype != 2) {

                                MediaPlayer mediaPlayer = new MediaPlayer();

                                try {
                                    mediaPlayer.setDataSource(context, defaultRingtoneUri);
                                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
                                    mediaPlayer.prepare();
                                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                                        @Override
                                        public void onCompletion(MediaPlayer mp) {
                                            mp.release();
                                        }
                                    });
                                    mediaPlayer.start();
                                } catch (IllegalArgumentException e) {
                                    e.printStackTrace();
                                } catch (SecurityException e) {
                                    e.printStackTrace();
                                } catch (IllegalStateException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
           }
            }catch(Exception e)
            {

            }
        }



}
