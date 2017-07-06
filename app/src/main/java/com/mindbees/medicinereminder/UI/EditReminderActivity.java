package com.mindbees.medicinereminder.UI;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatSpinner;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.support.v7.widget.Toolbar;

import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Base.BaseActivity;
import com.mindbees.medicinereminder.UI.DataBase.DataSource;
import com.mindbees.medicinereminder.UI.Model.Reminder;
import com.mindbees.medicinereminder.UI.Model.TaskAlarm;
import com.mindbees.medicinereminder.UTILS.Constants;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by User on 22-12-2016.
 */

public class EditReminderActivity extends BaseActivity {
    Toolbar toolbar;
    TextView Update,Cancel;
    EditText EdName,EdDescription,EdFromDate,EdToDate,EdTime;
    Spinner spinnerUpdate;
    TextView nameHead,desHead,FDateHead,TdateHead,TimeHead,SpinHead;
    Reminder reminder;
    int fromday,frommomth,fromyear,today,tomonth,toyear,Fhour,Fminutes;
    Date Fdate,Tdate,now,Fdate2,Tdate2;
    int position;
    String FromDATE,TOdate,NAME,NOTES,delay;
    int id;
    boolean Is_repeating;
    DataSource db;
    int Reminder_fragment_type;

    int Reminterval,ReminderType;
    String []interval={"No Interval","15 Minutes","30 Minutes","1 Hour","2 Hour","4 Hour","6 Hour","8 Hour","12 Hour","5 Minutes"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editreminder);
        Bundle Extras=getIntent().getExtras();
        try {
            id=Extras.getInt("Id");
            Reminder_fragment_type=Extras.getInt(Constants.FRAGMENT_REMINDER_TYPE);
        }catch (Exception e){};

        db =DataSource.getInstance(EditReminderActivity.this);
        reminder=db.getReminder(id);
        initUi();
        setupUI();
        Calendar calendar=Calendar.getInstance(TimeZone.getDefault());
         now=calendar.getTime();
        settoolbar();
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.spinner_item, interval);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerUpdate.setAdapter(adapter);
        setclicks();
        checkSpinner();
        spinnerUpdate.setSelection(position);

    }

    private void setclicks() {
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSubmit();
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkname()&&checkdescription()&&checkfromdate()&&checktodate()&&checktime())
                {
                    CancelReminder();
                }
            }
        });
    }

    private void CancelReminder() {
        ReminderType=1;
        int cancel=1;
        Reminder reminder=new Reminder(id,NAME,false,Fhour,Fminutes,Is_repeating,ReminderType,Reminterval,"",FromDATE,"",TOdate,NOTES,cancel);
        int i=db.updateReminder(reminder);
        if(i==1)
        {
            showSnackBar("Reminder Cancelled",true);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(EditReminderActivity.this,MedicineReminderActivity.class);
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
    }


    private void checkSubmit() {
        if(checkname()&&checkdescription()&&checkfromdate()&&checktodate()&&checktime())
        {
            UpdateTask();
        }
    }

    private void UpdateTask() {
        ReminderType=1;
        Reminder reminder=new Reminder(id,NAME,false,Fhour,Fminutes,Is_repeating,ReminderType,Reminterval,"",FromDATE,"",TOdate,NOTES,0);
        int i=db.updateReminder(reminder);
        TaskAlarm alarm=new TaskAlarm();
        if (i==1) {
            alarm.updateAlarm(EditReminderActivity.this, reminder);
            showSnackBar("Reminder Updated",true);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(EditReminderActivity.this,MedicineReminderActivity.class);
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
        else
        {
            showSnackBar("Update failed",false);
        }



    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(EditReminderActivity.this,MedicineReminderActivity.class);
        intent.putExtra(Constants.FRAG_TYPE,2);
        intent.putExtra(Constants.FRAGMENT_REMINDER_TYPE,Reminder_fragment_type);
        startActivity(intent);
    }
    private boolean checktime() {
        String Time=EdTime.getText().toString().trim();
        delay=spinnerUpdate.getSelectedItem().toString().trim();
        if (this.delay.equals("No Interval"))
        {
            Reminterval=0;
        }
        if(this.delay.equals("15 Minutes"))
        {
            Reminterval=15;
        }
        if (this.delay.equals("30 Minutes"))
        {
            Reminterval=30;
        }
        if (this.delay.equals("45 Minutes"))
        {
            Reminterval=45;
        }
        if (this.delay.equals("1 Hour"))
        {
            Reminterval=60;
        }
        if (this.delay.equals("2 Hour"))
        {
            Reminterval=120;
        }
        if (this.delay.equals("3 Hour"))
        {
            Reminterval=180;
        }
        if (this.delay.equals("4 Hour"))
        {
            Reminterval=240;
        }
        if (this.delay.equals("5 Hour"))
        {
            Reminterval=300;
        }
        if (this.delay.equals("6 Hour"))
        {
            Reminterval=360;
        }
        if (this.delay.equals("8 Hour"))
        {
            Reminterval=480;
        }
        if (this.delay.equals("12 Hour"))
        {
            Reminterval=720;
        }
        if (this.delay.equals("5 Minutes"))
        {
            Reminterval=5;
        }
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
        if(Tdate.before(Fdate))
        {
            showSnackBar("Please select a valid date",false);
            return false;
        }
        else
        {

            return true;
        }
    }

    private boolean checkfromdate() {

        if(Fdate.before(now))
        {
            showSnackBar("Please update the from date",false);
            return false;
        }
        else
        {

            return true;
        }
    }

    private boolean checkdescription() {
        NOTES=EdDescription.getText().toString().trim();
        if (NOTES.isEmpty())
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
        NAME=EdName.getText().toString().trim();
        if(NAME.isEmpty())
        {
            showSnackBar("Reminder name must not be empty",false);
            return false;
        }
      else
        {
            return true;
        }

    }

    private void settoolbar() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Medicine Reminder");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(EditReminderActivity.this,MedicineReminderActivity.class);
                intent.putExtra(Constants.FRAG_TYPE,2);
                intent.putExtra(Constants.FRAGMENT_REMINDER_TYPE,Reminder_fragment_type);
                startActivity(intent);
                break;



            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setupUI() {
        String name=reminder.getName();
        String notes=reminder.getNotes();
        final String []fromdate=reminder.getFromDate().split("/");
        final int Fday=Integer.parseInt(fromdate[0]);
        final int Fmonth=Integer.parseInt(fromdate[1]);
        final int Fyear=Integer.parseInt(fromdate[2]);
        final String []Todate=reminder.getDateDue().split("/");
        final int Tday=Integer.parseInt(Todate[0]);
        final int Tmonth=Integer.parseInt(Todate[1]);
        final int Tyear=Integer.parseInt(Todate[2]);
        final int Hour=reminder.getHour();
        final int Minutes=reminder.getMinutes();
        checkSpinner();
        Fhour=Hour;
        Fminutes=Minutes;
        EdName.setText(name);
        EdDescription.setText(notes);
        EdFromDate.setFocusable(false);
        EdToDate.setFocusable(false);
        EdTime.setFocusable(false);
        FromDATE=Fday+"/"+(Fmonth)+"/"+Fyear;
        TOdate=Tday+"/"+(Tmonth)+"/"+Tyear;

        EdFromDate.setText(Fday+"/"+Fmonth+"/"+Fyear);
        EdToDate.setText(Tday+"/"+Tmonth+"/"+Tyear);
        EdTime.setText(updateTime(Hour,Minutes));
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH,Fday);
        cal.set(Calendar.MONTH,(Fmonth-1));
        cal.set(Calendar.YEAR,Fyear);
        Fdate=cal.getTime();
        Calendar cale=Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH,Tday);
        cale.set(Calendar.MONTH,(Tmonth-1));
        cale.set(Calendar.YEAR,Tyear);
        Tdate=cale.getTime();
        EdFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datepicker=new DatePickerDialog(EditReminderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar cal=Calendar.getInstance();
                        cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        cal.set(Calendar.MONTH,(month));
                        cal.set(Calendar.YEAR,year);
                        Fdate=cal.getTime();
                      fromday=dayOfMonth;
                        frommomth=month;
                        fromyear=year;
                        EdFromDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        FromDATE=fromday+"/"+(frommomth+1)+"/"+fromyear;
                        Calendar calender=Calendar.getInstance();
                        calender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        calender.set(Calendar.MONTH,(month));
                        calender.set(Calendar.YEAR,year);
                        calender.set(Calendar.HOUR_OF_DAY,0);
                        calender.set(Calendar.MINUTE,0);
                        calender.set(Calendar.SECOND,0);
                        Fdate2=calender.getTime();


                    }
                },Fyear,(Fmonth-1),Fday);
                datepicker.setTitle("Select From Date");
                datepicker.show();
            }
        });
        EdToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datepicker=new DatePickerDialog(EditReminderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Calendar cal=Calendar.getInstance();
                        cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        cal.set(Calendar.MONTH,month);
                        cal.set(Calendar.YEAR,year);
                        Tdate=cal.getTime();
                        Calendar calender=Calendar.getInstance();
                        calender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        calender.set(Calendar.MONTH,(month));
                        calender.set(Calendar.YEAR,year);
                        calender.set(Calendar.HOUR_OF_DAY,0);
                        calender.set(Calendar.MINUTE,0);
                        calender.set(Calendar.SECOND,0);
                        Tdate2=calender.getTime();
                        if(Tdate.before(Fdate))
                        {
                            showSnackBar("Please Select a Valid date",false);
                        }
                        else if(Tdate2==Fdate2)
                        {
                            Is_repeating=false;
                            today=dayOfMonth;
                            tomonth=month;
                            toyear=year;
                            EdToDate.setText(today+"/"+(tomonth+1)+"/"+toyear);
                            TOdate=today+"/"+(tomonth+1)+"/"+toyear;
                        }
                        else
                        {Is_repeating=true;
                            today=dayOfMonth;
                            tomonth=month;
                            toyear=year;
                            EdToDate.setText(today+"/"+(tomonth+1)+"/"+toyear);
                            TOdate=today+"/"+(tomonth+1)+"/"+toyear;
                        }



                    }
                },Tyear,(Tmonth-1),Tday);
                datepicker.setTitle("Select To-Date");
                datepicker.show();

            }
        });
        EdTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timepicker=new TimePickerDialog(EditReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Fhour=hourOfDay;
                        Fminutes=minute;
                        EdTime.setText(updateTime(Fhour,Fminutes));

                    }
                },Hour,Minutes,true);
                timepicker.setTitle("Select Time");
                timepicker.show();
            }
        });


//        spinnerUpdate.setSelection(position);

//        spinnerUpdate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(position==0)
//                {
//                    Reminterval=0;
//                }
//                if (position==1)
//                {
//                    Reminterval=15;
//                }
//                if(position==2)
//                {
//                    Reminterval=30;
//                }
//                if (position==3)
//                {
//                    Reminterval=45;
//
//                }
//                if (position==4)
//                {
//                    Reminterval=60;
//                }
//                if(position==5)
//                {
//                    Reminterval=120;
//
//                }
//                if (position==6)
//                {
//                    Reminterval=180;
//                }
//                if (position==7)
//                {
//                    Reminterval=240;
//                }
//                if (position==8)
//                {
//                    Reminterval=300;
//                }
//                if(position==9)
//                {
//                    Reminterval=360;
//                }
//                if(position==10)
//                {
//                    Reminterval=480;
//                }
//                if (position==11){
//                    Reminterval=720;
//                }
//                if(position==12)
//                {
//                    Reminterval=5;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });



    }

    private void checkSpinner() {
        int RemInterval=reminder.getRepeatInterval();
        if(RemInterval==0)
        {
            position=0;
        }
        if (RemInterval==15)
        {
            position=1;
        }
        if(RemInterval==30)
        {
            position=2;
        }
//        if (RemInterval==45)
//        {
//            position=3;
//
//        }
        if (RemInterval==60)
        {
            position=3;
        }
        if(RemInterval==120)
        {
            position=4;

        }
//        if (RemInterval==180)
//        {
//            position=6;
//        }
        if (RemInterval==240)
        {
            position=5;
        }
//        if (RemInterval==300)
//        {
//            position=8;
//        }
        if(RemInterval==360)
        {
            position=6;
        }
        if(RemInterval==480)
        {
            position=7;
        }
        if (RemInterval==720){
            position=8;
        }
        if(RemInterval==5)
        {
            position=9;
        }


    }

    private void initUi() {
        Update= (TextView) findViewById(R.id.ButtonUpdateReminder);
        Cancel= (TextView) findViewById(R.id.buttonCancelReminder);
        EdName= (EditText) findViewById(R.id.EditReminderUpdateName);
        EdDescription= (EditText) findViewById(R.id.EditReminderUpdateDescription);
        EdFromDate= (EditText) findViewById(R.id.EditReminderUpdateFromDate);
        EdToDate= (EditText) findViewById(R.id.EditReminderUpdateToDate);
        EdTime= (EditText) findViewById(R.id.EditReminderUpdateTime);
        spinnerUpdate= (Spinner) findViewById(R.id.reEditSpin);
        nameHead= (TextView) findViewById(R.id.EditReminderUpdateNamehead);
        desHead= (TextView) findViewById(R.id.EditReminderUpdateDescriptionHead);
        FDateHead= (TextView) findViewById(R.id.EditReminderUpdateFromDateHead);
        TdateHead= (TextView) findViewById(R.id.EditReminderUpdateToDateHead);
        TimeHead= (TextView) findViewById(R.id.EditReminderUpdateTimeHead);
        SpinHead= (TextView) findViewById(R.id.reEditSpinhead);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/OpenSans_Bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/tex_gyre_adventor_bold.ttf");
        Typeface typeface2=Typeface.createFromAsset(getAssets(),"fonts/OpenSans_Regular.ttf");
        nameHead.setTypeface(typeface);
        desHead.setTypeface(typeface);
        FDateHead.setTypeface(typeface);
        TdateHead.setTypeface(typeface);
        TimeHead.setTypeface(typeface);
        SpinHead.setTypeface(typeface);
        Update.setTypeface(typeface);
        Cancel.setTypeface(typeface);
        EdName.setTypeface(typeface2);
        EdDescription.setTypeface(typeface2);
        EdFromDate.setTypeface(typeface2);
        EdToDate.setTypeface(typeface2);
        EdTime.setTypeface(typeface2);

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
}
