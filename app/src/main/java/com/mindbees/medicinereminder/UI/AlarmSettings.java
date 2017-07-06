package com.mindbees.medicinereminder.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Base.BaseActivity;
import com.mindbees.medicinereminder.UTILS.Constants;

/**
 * Created by User on 10-01-2017.
 */

public class AlarmSettings extends BaseActivity {
    RadioGroup rg;
    Toolbar toolbar;
    SwitchCompat sw,sw2;
    Button save,cancel;
    int i=0;
    int pos;
    int autocancel=0;
    int fullscreen_alert=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_settings_layout);
        initui();
        settoolbar();
        try{

            i=getPref(Constants.REMINDER_TYPE);


            switch (i)
            {
                case 0:  ((RadioButton)rg.getChildAt(0)).setChecked(true);
                    break;
                case  1:  ((RadioButton)rg.getChildAt(1)).setChecked(true);
                    break;
                case 2:  ((RadioButton)rg.getChildAt(2)).setChecked(true);
                    break;
                default:((RadioButton)rg.getChildAt(0)).setChecked(true);
                    break;

            }
            autocancel=getPref(Constants.AUTO_CANCEL);
            if (autocancel==1)
            {
                sw.setChecked(true);
            }
            fullscreen_alert=getPref(Constants.FULL_SCREEN_ALERT);
//            if (fullscreen_alert==1)
//            {
////                sw2.setChecked(true);
//            }


        }catch (Exception e){

        }
        setClicks();

    }

    private void settoolbar() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Alarm Settings");
    }

    private void setClicks() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                pos=rg.indexOfChild(findViewById(checkedId));
                switch (pos)
                {
                    case 0:
                         i=0;
                        break;
                    case 1:
                        i=1;
                        break;
                    case 2:
                        i=2;
                        break;
                    default:
                        i=0;
                        break;

                }

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checksave();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                overridePendingTransition(0, 0);
                finish();

                overridePendingTransition(0, 0);
                startActivity(intent);
            }
        });

    }

    private void checksave() {
        if(i==0)
        {
            savePref(Constants.REMINDER_TYPE,0);
        }
     else if(i==1)
        {
            savePref(Constants.REMINDER_TYPE,1);
        }
     else if(i==2)
        {
            savePref(Constants.REMINDER_TYPE,2);

        }
        else {
            savePref(Constants.REMINDER_TYPE,0);
        }
        if(sw.isChecked())
        {
            savePref(Constants.AUTO_CANCEL,1);

        }
        else {
            savePref(Constants.AUTO_CANCEL,0);
        }
//        if (sw2.isChecked())
//        {
//            savePref(Constants.FULL_SCREEN_ALERT,1);
//        }
//        else
//        {
//            savePref(Constants.FULL_SCREEN_ALERT,0);
//        }
        showSnackBar("Settings Saved",true);
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
        rg= (RadioGroup) findViewById(R.id.radio_group);
        sw= (SwitchCompat) findViewById(R.id.switch_auto_cancel);
//        sw2= (SwitchCompat) findViewById(R.id.switch_full_alert);
        save= (Button) findViewById(R.id.button_alarmsettings_save);
        cancel= (Button) findViewById(R.id.button_alarmsettings_cancel);


    }
}
