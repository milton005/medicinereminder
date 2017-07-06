package com.mindbees.medicinereminder.UI;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Base.BaseActivity;
import com.mindbees.medicinereminder.UI.DataBase.DataSource;
import com.mindbees.medicinereminder.UI.DataBase.DbHelper;
import com.mindbees.medicinereminder.UI.Model.Reminder;
import com.mindbees.medicinereminder.UI.Model.TaskAlarm;
import com.mindbees.medicinereminder.UTILS.Constants;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by User on 28-12-2016.
 */

public class AddAppointments extends BaseActivity {
    private Toolbar toolbar;
    RadioGroup radioGroup;
    TextView nameHead, deshead, DateHead, Timehead, remindMe;
    String ApName, Apdesc, Apdate, Aptime;
    int Hour, Minutes, Tday, Tmonth, Tyear;
    int Date_ok = 0;
    private EditText name, description, date, time;
    DataSource db;
    int pos;
    String remind="";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_appointments_activity);
        initui();
        Onclicks();
        setupui();
        SetToolbar();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void Onclicks() {

    }

    private void setupui() {
        time.setFocusable(false);
        date.setFocusable(false);
        time.setText(getCrntTime());
        Calendar calender = Calendar.getInstance(TimeZone.getDefault());
        final int Year = calender.get(Calendar.YEAR);
        final int Month = calender.get(Calendar.MONTH);
        final int Date = calender.get(Calendar.DAY_OF_MONTH);
        final int hour = calender.get(Calendar.HOUR_OF_DAY);
        final int minutes = calender.get(Calendar.MINUTE);
        Tyear = Year;
        Tmonth = Month;
        Tday = Date;
        Hour = hour;
        Minutes = minutes;
        date.setText(Date + "/" + (Month + 1) + "/" + Year);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calender = Calendar.getInstance(TimeZone.getDefault());
                int Year = calender.get(Calendar.YEAR);
                int Month = calender.get(Calendar.MONTH);
                int Date = calender.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datepicker = new DatePickerDialog(AddAppointments.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Tday = dayOfMonth;
                        Tmonth = month;
                        Tyear = year;
                        date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.DAY_OF_MONTH, Tday);
                        cal.set(Calendar.MONTH, Tmonth);
                        cal.set(Calendar.YEAR, Tyear);
                        cal.set(Calendar.HOUR_OF_DAY, 23);
                        if (cal.getTimeInMillis() > System.currentTimeMillis()) {
                            Date_ok = 1;
                        } else {
                            showSnackBar("please select a valid date", false);
                        }

                    }
                }, Year, Month, Date);
                datepicker.setTitle("Select Date");
                datepicker.show();

            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timepicker = new TimePickerDialog(AddAppointments.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Hour = hourOfDay;
                        Minutes = minute;
                        time.setText(updateTime(hourOfDay, minute));

                    }
                }, hour, minutes, true);
                timepicker.setTitle("Select Time");
                timepicker.show();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(AddAppointments.this, MedicineReminderActivity.class);
                intent.putExtra(Constants.FRAG_TYPE, 3);
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
        if (checkname() && checkdescription() && checkdate() && checktime()) {
            AddTask();
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddAppointments.this, MedicineReminderActivity.class);
        intent.putExtra(Constants.FRAG_TYPE, 3);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.addmedicine_activity, menu);
        return true;
    }

    private void AddTask() {
        db = DataSource.getInstance(getApplicationContext());
        int id = db.getNextID(DbHelper.TABLE_TASKS);
        Apdate = Tday + "/" + (Tmonth + 1) + "/" + Tyear;
        int ReminderType = 2;

        Reminder reminder = new Reminder(id, ApName, false, Hour, Minutes, false, ReminderType, 0, remind, Apdate, "", Apdate, Apdesc, 0);
        db.addReminder(reminder);
        TaskAlarm alarm = new TaskAlarm();
        alarm.setAlarm(AddAppointments.this, reminder);
        showSnackBar("Appointment added Successfully", true);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AddAppointments.this, MedicineReminderActivity.class);
                intent.putExtra(Constants.FRAG_TYPE, 3);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0, 0);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
            }
        }, 2000);


    }

    private boolean checktime() {
        String Temptime = time.getText().toString().trim();
        if (Temptime.isEmpty()) {
            showSnackBar("Please select Time", false);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkdate() {
        String Tempdate = this.date.getText().toString().trim();
        if (Tempdate.isEmpty()) {
            showSnackBar("Please select a date", false);
            return false;
        } else {
            return true;
        }
    }

    private boolean checkdescription() {
        Apdesc = this.description.getText().toString().trim();

        if (Apdesc.isEmpty()) {
            showSnackBar("Description must not be empty", false);
            return false;
        } else {
            return true;
        }
    }


    private boolean checkname() {
        ApName = this.name.getText().toString().trim();
        if (ApName.isEmpty()) {
            showSnackBar("Appointment name must not be empty", false);

            return false;

        } else {

            return true;
        }
    }

    private void SetToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add appointments");
    }

    private void initui() {
        name = (EditText) findViewById(R.id.edittext_appointment_name);

        description = (EditText) findViewById(R.id.edittext_appointment_description);
        date = (EditText) findViewById(R.id.edittext_appointment_fromdate);
        time = (EditText) findViewById(R.id.edittext_appointment_time);
        nameHead = (TextView) findViewById(R.id.edittext_appointment_namehead);
        deshead = (TextView) findViewById(R.id.edittext_appointment_descriptionhead);
        DateHead = (TextView) findViewById(R.id.edittext_appointment_fromdatehead);
        Timehead = (TextView) findViewById(R.id.edittext_appointment_timeHead);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group_remindme);
        remindMe = (TextView) findViewById(R.id.textView_remind_me_at);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/OpenSans_Bold.ttf");
        Typeface typeface1 = Typeface.createFromAsset(getAssets(), "fonts/tex_gyre_adventor_bold.ttf");
        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "fonts/OpenSans_Regular.ttf");
        remindMe.setTypeface(typeface);
        deshead.setTypeface(typeface);
        nameHead.setTypeface(typeface);
        DateHead.setTypeface(typeface);
        Timehead.setTypeface(typeface);
        name.setTypeface(typeface2);
        description.setTypeface(typeface2);
        date.setTypeface(typeface2);
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("AddAppointments Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
