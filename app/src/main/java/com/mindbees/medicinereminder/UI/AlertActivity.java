package com.mindbees.medicinereminder.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UTILS.Constants;
import com.mindbees.medicinereminder.UTILS.Util;

/**
 * Created by User on 28-12-2016.
 */

public class AlertActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
//        Bundle bundle=getIntent().getExtras();
//        int id=bundle.getInt(Constants.ID);
//        Util.getUtils().savePref(Constants.ID,id);
        AlertAlarm alert = new AlertAlarm();
        /** Opening the Alert Dialog Window. This will be opened when the alarm goes off */
        alert.show(getSupportFragmentManager(), "AlertAlarm");
    }
}
