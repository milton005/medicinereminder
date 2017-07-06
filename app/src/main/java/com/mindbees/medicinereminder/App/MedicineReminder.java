package com.mindbees.medicinereminder.App;

import android.app.Application;
import android.content.Intent;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.mindbees.medicinereminder.UI.DataBase.Services.ReminderService;
import com.mindbees.medicinereminder.UI.DataBase.Services.WakefulIntentService;
import com.mindbees.medicinereminder.UTILS.Constants;
import com.mindbees.medicinereminder.UTILS.Util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by User on 03-12-2016.
 */

public class MedicineReminder extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        new Util(this);
      if(  Util.getUtils().getPref("first_run",true)){
          Util.getUtils().savePref(Constants.CANCEL_ALL,0);
          Util.getUtils().savePref(Constants.AUTO_CANCEL,0);
          Util.getUtils().savePref(Constants.SET_SLEEP_FROM,0);
          Util.getUtils().savePref(Constants.SET_SLEEP_TO,0);
          Util.getUtils().savePref(Constants.REMINDER_TYPE,0);
          Util.getUtils().savePref(Constants.RESCHEDULE,0);
          Util.getUtils().savePref("first_run",false);


        }
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        int Minjutes=cal.get(Calendar.MINUTE);
        Util.getUtils().savePref(Constants.HOUR,hour);
        Util.getUtils().savePref(Constants.MINUTES,Minjutes);
        WakefulIntentService.acquireStaticLock(this);
        this.startService(new Intent(this, ReminderService.class));

    }
}
