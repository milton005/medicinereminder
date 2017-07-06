package com.mindbees.medicinereminder.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Base.BaseActivity;
import com.mindbees.medicinereminder.UTILS.Constants;

/**
 * Created by User on 25-01-2017.
 */

public class Splash_Activity extends BaseActivity {
    private static final long TAG_TIMER_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        initializeTimerTask();
    }

    private void initializeTimerTask() {



            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (getPref(Constants.TAG_ISLOGGED_IN, false)) {

                        Intent i = new Intent(Splash_Activity.this, Home.class);
                        startActivity(i);
//                    MainActivity.start(SplashActivity.this);
                    } else {
                        startActivity(new Intent(Splash_Activity.this, LoginActivityType.class));
//                    InitialActivity.start(SplashActivity.this);
                    }
                    finish();
                }


            }, TAG_TIMER_DELAY);
        }

}
