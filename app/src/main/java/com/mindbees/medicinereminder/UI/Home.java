package com.mindbees.medicinereminder.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.mindbees.medicinereminder.App.MedicineReminder;
import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UTILS.Constants;

/**
 * Created by User on 05-12-2016.
 */

public class Home extends AppCompatActivity implements View.OnClickListener
{
    RelativeLayout medicine,appointments,water,night,settings;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);
        initui();
        setclicks();

    }

    private void setclicks() {
        medicine.setOnClickListener(this);
        appointments.setOnClickListener(this);
        water.setOnClickListener(this);
        night.setOnClickListener(this);
        settings.setOnClickListener(this);

    }
    private void setIntents(int position) {
        Intent i = new Intent(Home.this, MedicineReminderActivity.class);
        i.putExtra(Constants.FRAG_TYPE, position);
        startActivity(i);
    }
    private void initui() {
        medicine= (RelativeLayout) findViewById(R.id.layout_MedicineReminder);
        appointments= (RelativeLayout) findViewById(R.id.layout_appointments);
        water= (RelativeLayout) findViewById(R.id.layout_water_reminder);
        settings= (RelativeLayout) findViewById(R.id.layout_settings);
        night= (RelativeLayout) findViewById(R.id.layout_nightbrushing);
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.layout_MedicineReminder:
                setIntents(2);
                break;
            case R.id.layout_appointments:
                setIntents(3);
                break;
            case R.id.layout_water_reminder:
                Intent intent=new Intent( Home.this,SetWaterReminder.class);
                startActivity(intent);
                break;
            case R.id.layout_settings:
                setIntents(4);
                break;
            case R.id.layout_nightbrushing:
                Intent intent1=new Intent(Home.this,SetNightBrushing.class);
                startActivity(intent1);
                break;

            default:
                break;
        }

    }
}
