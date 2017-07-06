package com.mindbees.medicinereminder.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;

import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Base.BaseActivity;
import com.mindbees.medicinereminder.UI.DataBase.DataSource;
import com.mindbees.medicinereminder.UI.DataBase.DbHelper;
import com.mindbees.medicinereminder.UI.Model.OnAlarmReceiver;
import com.mindbees.medicinereminder.UI.Model.Reminder;
import com.mindbees.medicinereminder.UI.Model.TaskAlarm;
import com.mindbees.medicinereminder.UTILS.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by User on 13-12-2016.
 */

public class AddReminderActivity extends BaseActivity {
   private Toolbar toolbar;
    EditText name,description,fromdate,todate,time;
    TextView nameHead,DescriptionHead,FromDateHead,ToDateHead,TimeHead,Spinnerhead;
    AppCompatSpinner spinner;
    String delay;
    int Reminder_interval;
    int Reminder_type;
    boolean Is_repeating;
    int Fyear,Fmonth,Fday;
    int ToYear,ToMonth,ToDay;
    int hourOFDay, minOFHour;
    String FromDate,TODATE;
    DataSource db;
    int equal;
    Reminder reminder;
    Date Fdate,Tdate;
     private   Context context;

    String []interval={"No Interval","15 Minutes","30 Minutes","1 Hour","2 Hour","4 Hour","6 Hour","8 Hour","12 Hour","5 Minutes"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.addreminder);
        initui();
        Onclicks();
        SetToolbar();
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.spinner_item, interval);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);


    }

    private void AddTask() {
        db=DataSource.getInstance(getApplicationContext());
        int id=db.getNextID(DbHelper.TABLE_TASKS);
        if(equal==1)
        {
            if(Reminder_interval==0)
            {
                Is_repeating=false;
            }
            else
            {
                Is_repeating=true;
            }
        }
        String Name=name.getText().toString().trim();
        String DeScription=description.getText().toString().trim();
         FromDate=Fday+"/"+(Fmonth+1)+"/"+Fyear;
        TODATE=ToDay+"/"+(ToMonth+1)+"/"+ToYear;
        CheckSpinner();
        Reminder_type=1;
        Reminder reminder=new Reminder(id,Name,false,hourOFDay,minOFHour,Is_repeating,Reminder_type,Reminder_interval,"",FromDate,"",TODATE,DeScription,0);
        db.addReminder(reminder);
        TaskAlarm alarm = new TaskAlarm();
//        Intent notificationIntent = new Intent(AddReminderActivity.this, OnAlarmReceiver.class);
//        notificationIntent.putExtra(OnAlarmReceiver.NOTIFICATION_ID, id);
//        notificationIntent.putExtra("Title",Name);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(AddReminderActivity.this, id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        if (Reminder_interval==0)
//        {
//            Calendar cal=Calendar.getInstance();
//            cal.set(Calendar.DAY_OF_MONTH,ToDay);
//            cal.set(Calendar.MONTH,(ToMonth));
//            cal.set(Calendar.YEAR,ToYear);
//            cal.set(Calendar.HOUR_OF_DAY,hourOFDay);
//            cal.set(Calendar.MINUTE,minOFHour);
//            cal.set(Calendar.SECOND,0);
//            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
//        }
//        else {
//            Calendar cal=Calendar.getInstance();
//            cal.set(Calendar.DAY_OF_MONTH,Fday);
//            cal.set(Calendar.MONTH,(Fmonth));
//            cal.set(Calendar.YEAR,Fyear);
//            cal.set(Calendar.HOUR_OF_DAY,hourOFDay);
//            cal.set(Calendar.MINUTE,minOFHour);
//            cal.set(Calendar.SECOND,0);
//            long delay=Reminder_interval * 60 * 1000;
//            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),delay, pendingIntent);
//        }
        alarm.setAlarm(AddReminderActivity.this, reminder);
        showSnackBar("Reminder Added ",true);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(AddReminderActivity.this,MedicineReminderActivity.class);
                intent.putExtra(Constants.FRAG_TYPE,2);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0,0);
                finish();
                overridePendingTransition(0,0);
                startActivity(intent);
            }
        }, 2000);



    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(AddReminderActivity.this,MedicineReminderActivity.class);
        intent.putExtra(Constants.FRAG_TYPE,2);
        startActivity(intent);
    }
    private void CheckSpinner() {
     this.delay=spinner.getSelectedItem().toString().trim();
        if (this.delay.equals("No Interval"))
        {
            Reminder_interval=0;
        }
        if(this.delay.equals("15 Minutes"))
        {
            Reminder_interval=15;
        }
        if (this.delay.equals("30 Minutes"))
        {
            Reminder_interval=30;
        }
        if (this.delay.equals("45 Minutes"))
        {
            Reminder_interval=45;
        }
        if (this.delay.equals("1 Hour"))
        {
            Reminder_interval=60;
        }
        if (this.delay.equals("2 Hour"))
        {
            Reminder_interval=120;
        }
        if (this.delay.equals("3 Hour"))
        {
            Reminder_interval=180;
        }
        if (this.delay.equals("4 Hour"))
        {
            Reminder_interval=240;
        }
        if (this.delay.equals("5 Hour"))
        {
            Reminder_interval=300;
        }
        if (this.delay.equals("6 Hour"))
        {
            Reminder_interval=360;
        }
        if (this.delay.equals("8 Hour"))
        {
            Reminder_interval=480;
        }
        if (this.delay.equals("12 Hour"))
        {
            Reminder_interval=720;
        }
        if (this.delay.equals("5 Minutes"))
        {
            Reminder_interval=5;
        }

    }

    private void Onclicks() {
        fromdate.setFocusable(false);
        todate.setFocusable(false);
        time.setFocusable(false);
        time.setText(getCrntTime());
        Calendar calender=Calendar.getInstance(TimeZone.getDefault());
        int Year=calender.get(Calendar.YEAR);
        int Month=calender.get(Calendar.MONTH);
        int Date=calender.get(Calendar.DAY_OF_MONTH);
        Fday=Date;
        Fmonth=Month;
        Fyear=Year;
        fromdate.setText(Date+"/"+(Month+1)+"/"+Year);
        todate.setText(Date+"/"+(Month+1)+"/"+Year);
        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calender=Calendar.getInstance(TimeZone.getDefault());
                int Year=calender.get(Calendar.YEAR);
                int Month=calender.get(Calendar.MONTH);
                int Date=calender.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datepicker=new DatePickerDialog(AddReminderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        FromDate=dayOfMonth+"/"+(month+1)+"/"+year;
                        Calendar cal=Calendar.getInstance();
                        cal.setTimeInMillis(0);
                        cal.set(year, month, dayOfMonth, 0, 0, 0);
                        Fdate = cal.getTime();

//                        if(cal.getTimeInMillis()> System.currentTimeMillis())
//                        {
                            Fyear=year;
                            Fmonth=month;
                            Fday=dayOfMonth;
                            fromdate.setText(dayOfMonth+"/"+(month+1)+"/"+year);

//                        }
//                        else
//                        {
//                            showSnackBar("Please Select a Valid Date",false);
//                        }

                    }
                },Year,Month,Date);
                datepicker.setTitle("Select Date");
                datepicker.show();


            }
        });
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calender=Calendar.getInstance(TimeZone.getDefault());
                int Year=calender.get(Calendar.YEAR);
                int Month=calender.get(Calendar.MONTH);
                int Date=calender.get(Calendar.DAY_OF_MONTH);
                ToYear=Year;
                ToMonth=Month;
                ToDay=Date;
                DatePickerDialog datepicker=new DatePickerDialog(AddReminderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                        Calendar cal=Calendar.getInstance();
                        cal.setTimeInMillis(0);
                        cal.set(year, month, dayOfMonth, 0, 0, 0);
                        Tdate = cal.getTime();
                        if(Tdate.before(Fdate))
                        {
                            showSnackBar("Please Select a valid date",false);
                        }
                        if (Tdate==Fdate)
                        {
                            equal=1;
                        }
                        else
                        {
                            Is_repeating=true;
                            ToYear=year;
                            ToMonth=month;
                            ToDay=dayOfMonth;
                            todate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        }
                    }
                },Year,Month,Date);
                datepicker.setTitle("Select Date");
                datepicker.show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minut = mcurrentTime.get(Calendar.MINUTE);
                hourOFDay=hour;
                minOFHour=minut;
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        hourOFDay = selectedHour;
                        minOFHour = selectedMinute;
//						setAlarmTime(calObj, selectedHour, selectedMinute);
                        time.setText(updateTime(selectedHour,selectedMinute));
                    }
                }, hour, minut, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


    }
  private void initui() {
        name= (EditText) findViewById(R.id.edittext_reAdd_name);
        description= (EditText) findViewById(R.id.edittext_reAdd_description);
        fromdate= (EditText) findViewById(R.id.edittext_reAdd_fromdate);
        todate= (EditText) findViewById(R.id.edittext_reAdd_todate);
        time= (EditText) findViewById(R.id.edittext_reAdd_time);
        spinner= (AppCompatSpinner) findViewById(R.id.reAddSpin);
        nameHead= (TextView) findViewById(R.id.edittext_reAdd_name_head);
        DescriptionHead= (TextView) findViewById(R.id.edittext_reAdd_descriptionhead);
        FromDateHead= (TextView) findViewById(R.id.edittext_reAdd_fromdatehead);
        ToDateHead= (TextView) findViewById(R.id.edittext_reAdd_todatehead);
        TimeHead= (TextView) findViewById(R.id.edittext_reAdd_timehead);
        Spinnerhead= (TextView) findViewById(R.id.reAddSpinHead);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/OpenSans_Bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/tex_gyre_adventor_bold.ttf");
        Typeface typeface2=Typeface.createFromAsset(getAssets(),"fonts/OpenSans_Regular.ttf");
        nameHead.setTypeface(typeface);
        DescriptionHead.setTypeface(typeface);
        FromDateHead.setTypeface(typeface);
        ToDateHead.setTypeface(typeface);
        TimeHead.setTypeface(typeface);
        name.setTypeface(typeface2);
        description.setTypeface(typeface2);
        fromdate.setTypeface(typeface2);
        todate.setTypeface(typeface2);
        time.setTypeface(typeface2);


  }
    private String getCrntTime() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return updateTime(hour, minute);
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
    private void SetToolbar() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Medicine Reminder");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.addmedicine_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(AddReminderActivity.this,MedicineReminderActivity.class);
                intent.putExtra(Constants.FRAG_TYPE,2);
                startActivity(intent);
                break;
            case R.id.plus:
                checkSubmit();
                break;


            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkSubmit() {
        if(checkname()&&checkdiscription()&&checkfromdate()&&checktodate()&&checktime())
        {

            AddTask();
        }
    }

    private boolean checktime() {
        String Time=this.time.getText().toString().trim();
        if (Time.isEmpty())
        {
            showSnackBar("please Select Time",false);
            return false;
        }
        else {
            return true;
        }
    }

    private boolean checktodate() {

        TODATE=ToDay+"/"+ToMonth+"/"+ToYear;
        if(TODATE.isEmpty())
        {
            showSnackBar("please select To--date",false);
            return false;
        }
        else {
            return true;
        }
    }

    private boolean checkfromdate() {
        FromDate=Fday+"/"+Fmonth+"/"+Fyear;
        if(FromDate.isEmpty())
        {
            showSnackBar("please Select from date",false);
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean checkdiscription() {
        String description=this.description.getText().toString().trim();
        if (description.isEmpty())
        {
            showSnackBar("description must not be empty",false);
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean checkname() {
        String name=this.name.getText().toString().trim();
        if (name.isEmpty())
        {
            showSnackBar("Reminder name must not be empty",false);
            return false;
        }
        else
        {
            return true;
        }
    }

}
