package com.mindbees.medicinereminder.UI.Model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mindbees.medicinereminder.UI.DataBase.Services.ReminderService;
import com.mindbees.medicinereminder.UI.DataBase.Services.WakefulIntentService;

/**
 * Created by User on 16-12-2016.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        WakefulIntentService.acquireStaticLock(context); //acquire a partial WakeLock
        context.startService(new Intent(context, ReminderService.class));
    }
}
