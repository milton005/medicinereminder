package com.mindbees.medicinereminder.UI;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Base.BaseActivity;
import com.mindbees.medicinereminder.UI.DataBase.DataSource;
import com.mindbees.medicinereminder.UI.DataBase.DbHelper;
import com.mindbees.medicinereminder.UI.Model.Reminder;
import com.mindbees.medicinereminder.UI.Model.TaskAlarm;
import com.mindbees.medicinereminder.UTILS.Constants;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by User on 02-01-2017.
 */

public class EditAppointments extends BaseActivity {
    EditText Edname,Eddescription,Eddate,Edtime;
    RadioGroup radioGroup;
    Toolbar toolbar;
    TextView nameHead,desHead,dateHead,timeHead,remindmeat;
    TextView update,close;
    int id;
    String TODATE,remind;
    String Name,Description,Time;
    Date now,Fdate;
    int Today,Tomonth,Toyear;
    Reminder reminder;
    DataSource db;
    boolean Completed;
    private int Fhour;
    private int Fminutes;
    int ReminderType;
    CheckBox reschedule;
    CheckBox markAsCompleted;
    int Reminder_fragment_type;
    int Reschedule;
    LinearLayout mark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editappointments);
        Bundle Extras=getIntent().getExtras();
        initui();
        try {
            id=Extras.getInt("Id");
            Reminder_fragment_type=Extras.getInt(Constants.FRAGMENT_REMINDER_TYPE);
            Reschedule=Extras.getInt("Reschedule");
        }catch (Exception e){};

        db =DataSource.getInstance(EditAppointments.this);
        reminder=db.getReminder(id);
        Calendar calendar=Calendar.getInstance(TimeZone.getDefault());
        now=calendar.getTime();
        settoolbar();
        setupui();
        try{
//            int temp=getPref(Constants.RESCHEDULE);
            if(Reschedule==1)
            {
                update.setText("RESCHEDULE");
                mark.setVisibility(View.GONE);
                reschedule.setChecked(true);
                reschedule.setEnabled(false);
                Edname.setFocusable(false);
                markAsCompleted.setEnabled(false);
                getSupportActionBar().setTitle("Reschedule" +
                        "");


            }

        }catch (Exception e)
        {

        }
        setclicks();
        try {
            remind=reminder.getDateCreated();
            if (!remind.isEmpty())
            {
                int d=Integer.parseInt(remind);
                switch (d)
                {
                    case 1: ((RadioButton)radioGroup.getChildAt(1)).setChecked(true);
                        break;
                    case 2: ((RadioButton)radioGroup.getChildAt(2)).setChecked(true);
                        break;
                    default:
                        ((RadioButton)radioGroup.getChildAt(0)).setChecked(true);
                        break;
                }
            }

        }catch (Exception e){}

    }

    private void setclicks() {
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checksubmit();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReminderType=2;
                Name=Edname.getText().toString().trim();
                Description=Eddescription.getText().toString().trim();
                int cancel=1;
                Reminder reminder=new Reminder(id,Name,false,Fhour,Fminutes,false,ReminderType,0,remind,TODATE,"",TODATE,Description,cancel);
                int i= db.updateReminder(reminder);
                if (i==1)
                {
                    showSnackBar("Appointment Cancelled",true);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent=new Intent(EditAppointments.this,MedicineReminderActivity.class);
                            intent.putExtra(Constants.FRAG_TYPE,3);

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
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int pos;
                pos=radioGroup.indexOfChild(findViewById(checkedId));
                switch (pos)
                {
                    case 0:
                        remind="";
                        break;
                    case 1:
                        remind="1";
                        break;
                    case 2:
                        remind="2";
                        break;
                    default:
                        remind="";
                        break;

                }
            }
        });
    }

    private void checksubmit() {
        if(checkname()&&checkdescription()&&checkdate()&&checktime()&&checkcompleted())
        {
            UpdateTask();
        }
    }

    private boolean checkcompleted() {
        if (markAsCompleted.isChecked())
        {
           Completed=true;
            return  true;
        }
        else
        {
            Completed=false;
            return true;
        }
    }

    private void UpdateTask() {
        try {
            int temp = getPref(Constants.RESCHEDULE);
            ReminderType = 2;

            if (temp==1)
            {int id=db.getNextID(DbHelper.TABLE_TASKS);
                Reminder reminder = new Reminder(id, Name, false, Fhour, Fminutes, false, ReminderType, 0, remind, TODATE, "", TODATE, Description, 0);
                db.addReminder(reminder);
                TaskAlarm alarm=new TaskAlarm();
                alarm.setAlarm(EditAppointments.this,reminder);
                showSnackBar("Appointment Rescheduled", true);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(EditAppointments.this,MedicineReminderActivity.class);
                        intent.putExtra(Constants.FRAG_TYPE,3);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        overridePendingTransition(0,0);
                        finish();
                        overridePendingTransition(0,0);
                        startActivity(intent);
                    }
                }, 2000);
            }

           else {
                Reminder reminder = new Reminder(id, Name, Completed, Fhour, Fminutes, false, ReminderType, 0, remind, TODATE, "", TODATE, Description, 0);
                int i = db.updateReminder(reminder);
                if (Completed==true)
                {
                    showSnackBar("Appointment updated", true);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent=new Intent(EditAppointments.this,MedicineReminderActivity.class);
                            intent.putExtra(Constants.FRAG_TYPE,3);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            overridePendingTransition(0,0);
                            finish();
                            overridePendingTransition(0,0);
                            startActivity(intent);
                        }
                    }, 2000);
                }
                else {


                    TaskAlarm alarm = new TaskAlarm();
                    if (i == 1) {
                        alarm.updateAlarm(EditAppointments.this, reminder);
                        showSnackBar("Appointment updated", true);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent=new Intent(EditAppointments.this,MedicineReminderActivity.class);
                                intent.putExtra(Constants.FRAG_TYPE,3);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                overridePendingTransition(0,0);
                                finish();
                                overridePendingTransition(0,0);
                                startActivity(intent);
                            }
                        }, 2000);
                    } else {
                        showSnackBar("Update failed", false);
                    }
                }
            }
        }
        catch (Exception e)
        {

        }
    }

    private boolean checktime() {
        if ((Edtime.getText().toString().trim()).isEmpty())
        {
            showSnackBar("Please Update Time",false);
            return false;
        }else
        {
            return  true;
        }



    }

    private boolean checkdate() {
        TODATE=Eddate.getText().toString().trim();
        if(markAsCompleted.isChecked())
        {
            return true;
        }
        else {
            if (Fdate.before(now)) {
                showSnackBar("Please update the  date", false);
                return false;
            } else {

                return true;
            }
        }
    }

    private boolean checkdescription() {
        Description=Eddescription.getText().toString().trim();
        if (Description.isEmpty())
        {
            showSnackBar("Descrption must not be empty",false);
            return false;
        }
        else {
            return true;
        }
    }

    private boolean checkname() {
        Name=Edname.getText().toString().trim();
        if (Name.isEmpty())
        {
            showSnackBar("Name must not be empty",false);
            return false;
        }
        else
        {
            return  true;
        }
    }

    private void setupui() {
//        reschedule.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                int k=0;
//                try{
//                    int temp=getPref(Constants.RESCHEDULE);
//                    if(temp==1)
//                    {
//                        k=1;
//                    }
//                }catch (Exception e)
//                {
//
//                }
//                if(reschedule.isChecked())
//                {
//                   if(k!=1) {
//                       Intent intent = new Intent(EditAppointments.this, EditAppointments.class);
//                       intent.putExtra("Id",id);
//                       savePref(Constants.RESCHEDULE, 1);
//                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
//                               | Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                       overridePendingTransition(0, 0);
//                       finish();
//                       overridePendingTransition(0, 0);
//                       startActivity(intent);//
//                   }
//                }
//            }
//        });

        boolean completed= reminder.isCompleted();
        if(completed==true)
        {
            markAsCompleted.setChecked(true);
        }
        else
        {
            markAsCompleted.setChecked(false);
        }
        String Name=reminder.getName();
        String Description=reminder.getNotes();
        String Date=reminder.getDateDue();
        final String []Todate=reminder.getDateDue().split("/");
        TODATE=Date;
        final int Tday=Integer.parseInt(Todate[0]);
        final int Tmonth=Integer.parseInt(Todate[1]);
        final int Tyear=Integer.parseInt(Todate[2]);
        final int Hour=reminder.getHour();
        final int Minutes=reminder.getMinutes();
        Fhour=Hour;
        Fminutes=Minutes;
        Edname.setText(Name);
        Eddescription.setText(Description);
        Edtime.setFocusable(false);
        Eddate.setFocusable(false);
        Eddate.setText(Date);
        Edtime.setText(updateTime(Hour,Minutes));
        Calendar cale=Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH,Tday);
        cale.set(Calendar.MONTH,(Tmonth-1));
        cale.set(Calendar.YEAR,Tyear);
        Fdate=cale.getTime();
        Eddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datepicker=new DatePickerDialog(EditAppointments.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar cal=Calendar.getInstance();
                        cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        cal.set(Calendar.MONTH,(month));
                        cal.set(Calendar.YEAR,year);
                        Fdate=cal.getTime();
                        Today=dayOfMonth;
                        Tomonth=month;
                        Toyear=year;
                        Eddate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        TODATE=Today+"/"+(Tomonth+1)+"/"+Toyear;
//
//                        Calendar calender=Calendar.getInstance();
//                        calender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
//                        calender.set(Calendar.MONTH,(month));
//                        calender.set(Calendar.YEAR,year);
//                        calender.set(Calendar.HOUR_OF_DAY,0);
//                        calender.set(Calendar.MINUTE,0);
//                        calender.set(Calendar.SECOND,0);
//                        Fdate2=calender.getTime();


                    }
                },Tyear,(Tmonth-1),Tday);
                datepicker.setTitle("Select From Date");
                datepicker.show();
            }
        });
        Edtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timepicker=new TimePickerDialog(EditAppointments.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Fhour=hourOfDay;
                        Fminutes=minute;
                        Edtime.setText(updateTime(Fhour,Fminutes));

                    }
                },Hour,Minutes,true);
                timepicker.setTitle("Select Time");
                timepicker.show();
            }
        });

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

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(EditAppointments.this,MedicineReminderActivity.class);
        savePref(Constants.RESCHEDULE,0);
        intent.putExtra(Constants.FRAG_TYPE,3);
        intent.putExtra(Constants.FRAGMENT_REMINDER_TYPE,Reminder_fragment_type);
        startActivity(intent);
    }
    private void settoolbar() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("EditAppointments");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(EditAppointments.this,MedicineReminderActivity.class);
                savePref(Constants.RESCHEDULE,0);
                intent.putExtra(Constants.FRAGMENT_REMINDER_TYPE,Reminder_fragment_type);
                intent.putExtra(Constants.FRAG_TYPE,3);
                startActivity(intent);
                break;



            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initui() {
        Edname= (EditText) findViewById(R.id.EditAppointmentUpdateName);
        Eddescription= (EditText) findViewById(R.id.EditAppointmentUpdateDescription);
        Eddate= (EditText) findViewById(R.id.EditAppointmentUpdateFromDate);
        Edtime= (EditText) findViewById(R.id.EditAppointmentUpdateTime);
        mark= (LinearLayout) findViewById(R.id.linearlayout_markas_completed);
        update= (TextView) findViewById(R.id.ButtonUpdateAppointment);
        close= (TextView) findViewById(R.id.buttonCancelAppointment);
        reschedule= (CheckBox) findViewById(R.id.checkbox_reschedule);
        markAsCompleted= (CheckBox) findViewById(R.id.checkbox_markas_completed);
        nameHead= (TextView) findViewById(R.id.EditAppointmentUpdateNameHead);
        desHead= (TextView) findViewById(R.id.EditAppointmentUpdateDescriptionhead);
        dateHead= (TextView) findViewById(R.id.EditAppointmentUpdateFromDateHead);
        timeHead= (TextView) findViewById(R.id.EditAppointmentUpdateTimeHead);
        radioGroup= (RadioGroup) findViewById(R.id.radio_group_remindme_ed);
        remindmeat= (TextView) findViewById(R.id.textView_remind_me_at_ed);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/OpenSans_Bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/tex_gyre_adventor_bold.ttf");
        Typeface typeface2=Typeface.createFromAsset(getAssets(),"fonts/OpenSans_Regular.ttf");
        Edname.setTypeface(typeface2);
        Eddescription.setTypeface(typeface2);
        Eddate.setTypeface(typeface2);
        Edtime.setTypeface(typeface2);
        nameHead.setTypeface(typeface);
        desHead.setTypeface(typeface);
        dateHead.setTypeface(typeface);
        timeHead.setTypeface(typeface);
        markAsCompleted.setTypeface(typeface);


    }
}
