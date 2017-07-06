package com.mindbees.medicinereminder.UI;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

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
 * Created by User on 12-01-2017.
 */

public class SetNightBrushing extends BaseActivity
{    TextView time;
    TimePicker timePicker;
    TextView do_u,ideal,setReminder;
    Button save;
    DataSource db;
    int HOUR;
    String Date;
    int MINUTES;
    Toolbar toolbar;
    private ArrayList<Reminder> arrayList;
    private ArrayList<Reminder>temp;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_night_brushing);
        initui();
        setupUi();
        settoolbar();
        Calendar calendar=Calendar.getInstance();
        int m=calendar.get(Calendar.MONTH);
        int d=calendar.get(Calendar.DAY_OF_MONTH);
        int y=calendar.get(Calendar.YEAR);
        String date1=d+"/"+(m+1)+"/"+y;
        try {


            String date2 = temp.get(0).getFromDate();
            if (date1.equals(date2)) {
                save.setText("Update");
            }
        }catch (Exception e)
        {

        }
    }

    private void setupUi() {
        try {
            db = DataSource.getInstance(SetNightBrushing.this);
            arrayList = new ArrayList<>();
            temp = new ArrayList<>();
            arrayList = db.getAllReminder();
            for (Reminder task : arrayList) {
                if (task.getRepeatType() == 4) {
                    temp.add(task);
                }
            }
            if (temp.isEmpty()) {
                count = 0;
            } else {
                count = 1;
            }
            if (count == 1) {
                int hour = temp.get(0).getHour();
                int minutes = temp.get(0).getMinutes();
                Calendar calendar = Calendar.getInstance();
                int m = calendar.get(Calendar.MONTH);
                int d = calendar.get(Calendar.DAY_OF_MONTH);
                int y = calendar.get(Calendar.YEAR);
                String date1 = d + "/" + (m + 1) + "/" + y;
                String date2 = temp.get(0).getFromDate();
                if (date1.equals(date2)) {
                    time.setText(updateTime(hour, minutes));
                } else {
                    time.setText("No time set");
                }
            } else {
                time.setText("No time set");
            }
            time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calender = Calendar.getInstance(TimeZone.getDefault());
                    final int Year = calender.get(Calendar.YEAR);
                    final int Month = calender.get(Calendar.MONTH);
                    final int Date = calender.get(Calendar.DAY_OF_MONTH);
                    final int hour = calender.get(Calendar.HOUR_OF_DAY);
                    final int minutes = calender.get(Calendar.MINUTE);
                    TimePickerDialog timepicker = new TimePickerDialog(SetNightBrushing.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            HOUR = hourOfDay;

                            MINUTES = minute;
                            time.setText(updateTime(hourOfDay, minute));

                        }
                    }, hour, minutes, true);
                    timepicker.setTitle("Select Time");
                    timepicker.show();
                }
            });
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                if(date1.equals(temp.get(0).getFromDate()))
//                {
//                    showSnackBar("Update",true);
//                }
                    checkTime();
                }
            });
        }catch (Exception e){

        }

    }

    private void checkTime() {
        String check=String.valueOf(HOUR);
        if(check==null||check.isEmpty())
        {
            showSnackBar("Please Select Time",false);

        }
        else {
            addtask();
        }

    }

    private void addtask() {
        try {
            int id;
            Calendar cal = Calendar.getInstance(TimeZone.getDefault());
            String Date;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);

            Date = day + "/" + (month + 1) + "/" + year;
            int Reminder_type = 4;
            String Name = "Night Brushing";
            int delay = 0;
            String Notes = "Its time for your night brushing";
            if (count == 1) {
                id = temp.get(0).getId();
                TaskAlarm alarm = new TaskAlarm();
                Reminder reminder = new Reminder(id, Name, false, HOUR, MINUTES, false, Reminder_type, delay, "", Date, "", Date, Notes, 0);
                int i = db.updateReminder(reminder);


                if (i == 1) {
                    showSnackBar("REminder updated", true);
                    alarm.updateAlarm(SetNightBrushing.this, reminder);
                    showSnackBar("REminder updated", true);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SetNightBrushing.this, SetNightBrushing.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(intent);// Do something after 5s = 5000ms

                        }
                    }, 2000);

                } else {
                    showSnackBar("Update failed", false);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(SetNightBrushing.this, SetNightBrushing.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(intent);
                        }
                    }, 2000);

                }

            } else {
                id = db.getNextID(DbHelper.TABLE_TASKS);
                TaskAlarm alarm = new TaskAlarm();
                Reminder reminder = new Reminder(id, Name, false, HOUR, MINUTES, false, Reminder_type, delay, "", Date, "", Date, Notes, 0);
                db.addReminder(reminder);
                alarm.setAlarm(SetNightBrushing.this, reminder);
                showSnackBar("Reminder Added", true);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SetNightBrushing.this, SetNightBrushing.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        overridePendingTransition(0, 0);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(intent);
                    }
                }, 2000);

            }
        }catch (Exception e)
        {

        }

    }

    private String updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";

        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String HrsTime_12 = new StringBuilder().append(hours).append(':')
                .append(minutes).append(timeSet).toString();

        return HrsTime_12;

    }
    private void settoolbar() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Set Night Brushing");
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
    private void initui() {

        save= (Button) findViewById(R.id.Button_save_night_Brushing);
        time= (TextView) findViewById(R.id.night_brushing_textview_time);
        do_u= (TextView) findViewById(R.id.text_did_u_night);
        ideal= (TextView) findViewById(R.id.ideal_time_for_night);
        setReminder= (TextView) findViewById(R.id.setReminder_Night_hEad);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/OpenSans_Bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/tex_gyre_adventor_bold.ttf");
        Typeface typeface2=Typeface.createFromAsset(getAssets(),"fonts/OpenSans_Regular.ttf");
        do_u.setTypeface(typeface);
        ideal.setTypeface(typeface2);
        setReminder.setTypeface(typeface);
        time.setTypeface(typeface2);

    }

}
