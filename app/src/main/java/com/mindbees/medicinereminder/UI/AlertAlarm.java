package com.mindbees.medicinereminder.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.mindbees.medicinereminder.UI.DataBase.DataSource;
import com.mindbees.medicinereminder.UI.Model.Reminder;
import com.mindbees.medicinereminder.UTILS.Constants;
import com.mindbees.medicinereminder.UTILS.Util;

/**
 * Created by User on 28-12-2016.
 */

public class AlertAlarm extends DialogFragment {
    DataSource db;
    Reminder reminder;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int id= Util.getUtils().getPref(Constants.ID);
        db =DataSource.getInstance(getActivity());
        reminder=db.getReminder(id);
        int type=reminder.getRepeatType();
        String title="Medicine Reminder";
        String name=reminder.getName();
        String message="Did you";
        if (type==1)
        {
            title="Medicine Reminder";
            message="Did you take your "+ name + " ?";
        }
        if (type==2)
        {
            title="Appointments";
            message="Did you take your Appointment -"+ name + " ?";
        }
        if (type==3)
        {
            title="water Reminder";
            message="Did you drink  water   ?";
        }
        if (type==4)
        {
            title="Night Brushing";
            message="Did you take your "+ name + " ?";
        }

        /** Turn Screen On and Unlock the keypad when this alert dialog is displayed */
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        /** Creating a alert dialog builder */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        /** Setting title for the alert dialog */
        builder.setTitle(title);

        /** Making it so notification can only go away by pressing the buttons */
        setCancelable(false);

//        final String pill_name = getActivity().getIntent().getStringExtra("pill_name");

        builder.setMessage(message);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                AlertActivity act = (AlertActivity)getActivity();
//                act.doPositiveClick(pill_name);
                getActivity().finish();
            }
        });

//        builder.setNeutralButton("Snooze", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                /** Exit application on click OK */
//                AlertActivity act = (AlertActivity)getActivity();
//                act.doNeutralClick(pill_name);
//                getActivity().finish();
//            }
//        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /** Exit application on click OK */
                AlertActivity act = (AlertActivity)getActivity();
//                act.doNegativeClick();
                getActivity().finish();
            }
        });

        return builder.create();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }
}
