package com.mindbees.medicinereminder.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Base.BaseActivity;
import com.mindbees.medicinereminder.UI.Base.BaseFragment;
import com.mindbees.medicinereminder.UTILS.Constants;
import com.mindbees.medicinereminder.UTILS.Util;

import java.util.zip.Inflater;

/**
 * Created by User on 09-01-2017.
 */

public class SettingsFragment extends BaseFragment{

    CardView ChangePassword1,deleteAll,AlarmSettings,help;
    LinearLayout logout;
    TextView changeP,AlarmsettingsTExt,ResetText,SetSleepTime,LogouText,helptext;
    SwitchCompat cancel_all;
    TextView Cancel,sleep_default;
    FragmentManager mFragmentManager;
    int cancel=0;
    Spinner set_time;
    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;
    FragmentTransaction mFragmentTransaction;
    private static SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.settings_layout,null);
        initUi(v);
        String prefsFile = getContext().getPackageName();
        try {
            int interval1 = getPref(Constants.SET_SLEEP_FROM);
            int interval2 = getPref(Constants.SET_SLEEP_TO);
            if (interval1 == 0 && interval2 == 0) {
                sleep_default.setText(": Default");
            }
            else {
                String from = null;
                String to = null;
                switch (interval1) {
                    case 0:
                        from = "9 pm";
                        break;
                    case 1:
                        from = "10 pm";
                        break;
                    case 2:
                        from = "11 pm";
                        break;
                    case 3:
                        from = "12 am";
                        break;
                    default:
                        break;
                }
                switch (interval2) {
                    case 0:
                        to = "6 am";
                        break;
                    case 1:
                        to = "5 am";
                        break;
                    case 2:
                        to = "7 am";
                        break;
                    case 3:
                        to = "8 am";
                        break;
                    default:
                        break;
                }
                sleep_default.setText(": "+from + " to " + to);
            }
        }catch (Exception e){}


        setupui();
        setClicks(v);
        getActivity().setTitle("Settings");
        return (v);


    }



    private void setupui() {
        try {

            cancel=getPref(Constants.CANCEL_ALL);
            if(cancel==1)
            {
                cancel_all.setChecked(true);
            }
            else
            {
                cancel_all.setChecked(false);

            }
        }catch (Exception e)
        {

        }

    }

    private void setClicks(View v) {
        ChangePassword1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentManager = getActivity().getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                ChangePassword p=ChangePassword.NewInstance();
                p.show(mFragmentTransaction,"changePassword");
            }
        });
        AlarmSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),AlarmSettings.class);
                startActivity(intent);
            }
        });
        sleep_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentManager = getActivity().getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
               PopUpSetTime p=  PopUpSetTime.newInstance();
                p.show(mFragmentTransaction,"setTime");

            }
        });
//        if(cancel_all.isChecked()&&(cancel!=1))
//        {
//         new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle)
//                 .setTitle("CANCEL  ALL")
//                 .setMessage("Are you sure want to Cancel All reminders")
//                 .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                     @Override
//                     public void onClick(DialogInterface dialog, int which) {
//                         savePref(Constants.CANCEL_ALL,1);
//                         showSnackBar("All Reminders Cancelled",true);
//                     }
//                 }).setNegativeButton("NO",null)
//                 .show();
//        }
//        if(!cancel_all.isEnabled()&&(cancel==1))
//        {
//            savePref(Constants.CANCEL_ALL,0);
//            showSnackBar("Reminders Re activated",true);
//        }
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle)
                        .setTitle("DELETE ALL REMINDER")
                        .setMessage("Are you Sure want to delete all Reminders?\n\nWarning:  ALL Reminder data will be lost")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
            }
        });
        cancel_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cancel=getPref(Constants.CANCEL_ALL);
                if(isChecked)
                {
                    if(cancel!=1)
                    {
                        new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle)
                 .setTitle("CANCEL  ALL")
                 .setMessage("Are you sure want to cancel All reminders?")
                 .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         savePref(Constants.CANCEL_ALL,1);
                         showSnackBar("All Reminders Cancelled",true);
                     }
                 }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cancel_all.setChecked(false);
                            }
                        })
                 .show();
                    }
                }
                else
                {
                    if(cancel==1)
                    {
                        savePref(Constants.CANCEL_ALL,0);
                        showSnackBar("Reminders Re activated",true);
                    }
                }
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Tutorial_Activity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle)
                        .setTitle("Reminds")
                        .setMessage("Do you want to log out ?")
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                clearCachedValues();

                            }

                        }).setNegativeButton("No", null).show();
            }
        });
    }

    private void clearCachedValues() {
        if(Util.getUtils().getPref(Constants.TAG_LOGGED_IN_FB,false))
        {
            LoginManager.getInstance().logOut();
        }
        Util.getUtils().savePref(Constants.TAG_LOGGED_IN_FB,false);
        Util.getUtils().savePref(Constants.TAG_ISLOGGED_IN,false);
        Intent intent = new Intent(getActivity(), LoginActivityType.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }


    private void initUi(View v) {
        ChangePassword1= (CardView) v.findViewById(R.id.layout_changeapssword);
        cancel_all= (SwitchCompat) v.findViewById(R.id.switch_cancel_all);
        deleteAll= (CardView) v.findViewById(R.id.layout_delete_All_Reminders);
        AlarmSettings= (CardView) v.findViewById(R.id.layout_Alarm_settings);
        logout= (LinearLayout) v.findViewById(R.id.layout_logout);
        help= (CardView) v.findViewById(R.id.layout_help);
        helptext= (TextView) v.findViewById(R.id.textView_help);
        Cancel= (TextView) v.findViewById(R.id.textViewCancelAllReminders);
        sleep_default= (TextView) v.findViewById(R.id.textView_Set_sleep_default);
        changeP= (TextView) v.findViewById(R.id.textView_change_password);
        AlarmsettingsTExt= (TextView) v.findViewById(R.id.textView_AlarmSettings);
        ResetText= (TextView) v.findViewById(R.id.textView_Reset);
        SetSleepTime= (TextView) v.findViewById(R.id.textView_Set_sleep_time);
        LogouText= (TextView) v.findViewById(R.id.textView_Logout);
        Typeface typeface=Typeface.createFromAsset(getContext().getAssets(),"fonts/OpenSans_Bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getContext().getAssets(),"fonts/tex_gyre_adventor_bold.ttf");
        Typeface typeface2=Typeface.createFromAsset(getContext().getAssets(),"fonts/OpenSans_Regular.ttf");
        changeP.setTypeface(typeface);
        Cancel.setTypeface(typeface);
        AlarmsettingsTExt.setTypeface(typeface);
        ResetText.setTypeface(typeface);
        SetSleepTime.setTypeface(typeface);
        sleep_default.setTypeface(typeface);
        LogouText.setTypeface(typeface);
        helptext.setTypeface(typeface);



    }

//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        int interval1=getPref(Constants.SET_SLEEP_FROM);
//        int interval2=getPref(Constants.SET_SLEEP_TO);
//        if (interval1==0&&interval2==0)
//        {
//            sleep_default.setText(": Default");
//        }
//        String from = null;
//        String to = null;
//        switch (interval1)
//        {
//            case 0:from="9 pm";
//                break;
//            case 1:from="10 pm";
//                break;
//            case 2:from="11 pm";
//                break;
//            case 3:from="12 am";
//                break;
//            default:
//                break;
//        }
//        switch (interval2)
//        {
//            case 0:to="6 am";
//                break;
//            case 1:to="5 am";
//                break;
//            case 2:to="7 am";
//                break;
//            case 3:to="8 am";
//                break;
//            default:
//                break;
//        }
//        sleep_default.setText(from +" to "+to);
//
//    }
}
