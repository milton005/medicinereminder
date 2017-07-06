package com.mindbees.medicinereminder.UI;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Base.BaseActivity;
import com.mindbees.medicinereminder.UI.DataBase.DataSource;
import com.mindbees.medicinereminder.UI.DataBase.DbHelper;
import com.mindbees.medicinereminder.UI.Model.Reminder;
import com.mindbees.medicinereminder.UI.Model.TaskAlarm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by User on 04-01-2017.
 */

public class SetWaterReminder extends BaseActivity {
   RelativeLayout ideal;
    Toolbar toolbar;
    CheckBox check;
    TextView do_u,ideal1,set_reminder,ideal2,set_at_specific;

    AppCompatSpinner spin;
    LinearLayout tip,set,cancel;
    DataSource db;
    int count;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    int intrval;
    int delay;
    private ArrayList<Reminder> arrayList;
    private ArrayList<Reminder>temp;
    String []interval={"No Interval","30 Minutes","1 Hour","2 Hour","3 Hour","4 Hour"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setwaterreminderactivity);
        initUi();
        initdb();
        settoolbar();
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.spinner_item, interval);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spin.setAdapter(adapter);
        setUpui();
        setclicks();

    }

    private void settoolbar() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Set water Reminder");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               onBackPressed();
                break;



            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setclicks() {
        ideal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spin.getSelectedItemPosition()==0)
                {
//                    check.setChecked(true);
                }
                else
                {
                    check.setChecked(false);
                    showSnackBar("Invalid",false);
                }
            }
        });
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(check.isChecked()&&(position!=0))
                {

                    showSnackBar("Please Un tick the ideal time",false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checksubmit();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkcancel();
            }
        });
        tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                PopupTips popupTips=PopupTips.newInstance();
                popupTips.show(mFragmentTransaction,"Popup");
            }
        });
    }

    private void checkcancel() {
        int id;
        if(count==1)
        { showSnackBar("Water Reminder Deleted",true);
            id=temp.get(0).getId();
            db.deleteReminder(id);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    Intent intent=new  Intent(SetWaterReminder.this,SetWaterReminder.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    overridePendingTransition(0,0);
                    finish();
                    overridePendingTransition(0,0);
                    startActivity(intent);
                }
            }, 2000);

        }
        else
        {
            showSnackBar("No Water Reminder Found",false);
        }
    }

    private void checksubmit() {
        if(checkideal())
        {
            addtask();

        }
    }

    private void addtask() {
        int id;
        Calendar cal=Calendar.getInstance(TimeZone.getDefault());
        String Date;
        int day= cal.get(Calendar.DAY_OF_MONTH);
        int month=cal.get(Calendar.MONTH);
        int year=cal.get(Calendar.YEAR);
        int Hour=cal.get(Calendar.HOUR_OF_DAY);
        int Minutes=cal.get(Calendar.MINUTE);
        Date=day+"/"+(month+1)+"/"+year;
        int Reminder_type=3;
        String Name="Water Reminder";
        spin.setFocusable(false);
        String Notes="Its time to take your water";
        if(count==1)
        {
            id=temp.get(0).getId();
            TaskAlarm alarm=new TaskAlarm();
            Reminder reminder=new Reminder(id,Name,false,Hour,Minutes,true,Reminder_type,delay,"",Date,"",Date,Notes,0);
            int i=db.updateReminder(reminder);


            if(i==1)
            {  showSnackBar("REminder updated",true);
                alarm.updateAlarm(SetWaterReminder.this,reminder);
                showSnackBar("REminder updated",true);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new  Intent(SetWaterReminder.this,SetWaterReminder.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        overridePendingTransition(0,0);
                        finish();
                        overridePendingTransition(0,0);
                        startActivity(intent);// Do something after 5s = 5000ms

                    }
                }, 2000);

            }
            else {
                showSnackBar("Update failed",false);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new  Intent(SetWaterReminder.this,SetWaterReminder.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        overridePendingTransition(0,0);
                        finish();
                        overridePendingTransition(0,0);
                        startActivity(intent);
                    }
                }, 2000);

            }

        }
        else {
            id=db.getNextID(DbHelper.TABLE_TASKS);
            TaskAlarm alarm=new TaskAlarm();
            Reminder reminder=new Reminder(id,Name,false,Hour,Minutes,true,Reminder_type,delay,"",Date,"",Date,Notes,0);
            db.addReminder(reminder);
            alarm.setAlarm(SetWaterReminder.this,reminder);
            showSnackBar("Reminder Added",true);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new  Intent(SetWaterReminder.this,SetWaterReminder.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    overridePendingTransition(0,0);
                    finish();
                    overridePendingTransition(0,0);
                    startActivity(intent);
                }
            }, 2000);

        }


    }

    private boolean checkideal() {
       String Delay=spin.getSelectedItem().toString();

        if(check.isChecked())
        {
            if (Delay.equals("No Interval"))
            {
                delay=90;
            }
            else {
                delay=1;
            }
        }
        else {
            if(Delay.equals("No Interval"))
            {
                delay=0;
            }
            if(Delay.equals("30 Minutes"))
            {
                delay=30;
            }
            if(Delay.equals("1 Hour"))
            {
                delay=60;
            }
            if(Delay.equals("2 Hour"))
            {
                delay=120;
            }
            if(Delay.equals("3 Hour"))
            {
                delay=180;
            }
            if(Delay.equals("4 Hour"))
            {
                delay=240;
            }
        }
        if(delay==0)
        {
            showSnackBar("Please select a interval time",false);
            return false;
        }
        if(delay==1)
        {
            showSnackBar("Please Untick ideal time",false);
            return false;
        }
        else {
            return true;
        }

    }



    private void setUpui() {
        if(count==1)
        {
         intrval=   temp.get(0).getRepeatInterval();
            if(intrval==0)
            {
                spin.setSelection(0);
            }
            if(intrval==30)
            {
                spin.setSelection(1);
            }
            if (intrval==60)
            {
                spin.setSelection(2);
            }
            if(intrval==90)
            {
                check.setChecked(true);

            }
            if(intrval==120)
            {
                spin.setSelection(3);
            }
            if(intrval==180)
            {
                spin.setSelection(4);
            }
            if(intrval==240)
            {
                spin.setSelection(5);
            }

        }
    }

    private void initdb() {
        db=DataSource.getInstance(SetWaterReminder.this);
        arrayList=new ArrayList<>();
        temp=new ArrayList<>();
        arrayList=db.getAllReminder();
        for (Reminder task : arrayList) {
            if(task.getRepeatType()==3)
            {
                temp.add(task);
        }
        }
        if(temp.isEmpty())
        {
            count=0;
        }
        else
        {
            count=1;
        }


    }

    private void initUi() {
        ideal= (RelativeLayout) findViewById(R.id.layout_idealtime);
        check= (CheckBox) findViewById(R.id.checkbox_ideal);
        spin= (AppCompatSpinner) findViewById(R.id.SpinWater);
        tip= (LinearLayout) findViewById(R.id.ButtonTips);
        set= (LinearLayout) findViewById(R.id.ButtonSetWaterReminder);
        cancel= (LinearLayout) findViewById(R.id.ButtonCancelWaterReminder);
        do_u= (TextView) findViewById(R.id.text_did_u);
        ideal1= (TextView) findViewById(R.id.ideal_time_for);
        set_reminder= (TextView) findViewById(R.id.setReminder_water_head);
        ideal2= (TextView) findViewById(R.id.textViewidealTime);
        set_at_specific= (TextView) findViewById(R.id.set_At_Specific);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/OpenSans_Bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/tex_gyre_adventor_bold.ttf");
        Typeface typeface2=Typeface.createFromAsset(getAssets(),"fonts/OpenSans_Regular.ttf");
        do_u.setTypeface(typeface);
        ideal1.setTypeface(typeface2);
        set_reminder.setTypeface(typeface);
        ideal2.setTypeface(typeface2);
        set_at_specific.setTypeface(typeface);
    }
}
