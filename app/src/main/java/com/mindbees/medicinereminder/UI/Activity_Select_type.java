package com.mindbees.medicinereminder.UI;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment ;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UTILS.Constants;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by User on 20-01-2017.
 */

public class Activity_Select_type extends DialogFragment {

    ImageView imageIncome, imageExpense, closeBtn, imageReminder;
//    TextView textIncome, textExpense, textReminder;

    View view;
    Animation anim_iconIncome;
    Animation anim_iconExpense;
    Animation anim_iconReminder;
    int id,reminder_fragment_type;
    Timer timer;
    TimerTask timerTask;
    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.Theme_FullScren);

    }
    static Activity_Select_type newInstance()
    {
        Activity_Select_type p=new Activity_Select_type();

        return p;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.appointment_select_type,container,false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().requestFeature(1);

//        view.findViewById(R.id.button_close_tips).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent i=new Intent(getActivity(),Detalle.class);
////                startActivity(i);
//                dismiss();
//            }
//        });
        try {
            Bundle Extras=getArguments();
            id=Extras.getInt("Id");
            reminder_fragment_type=Extras.getInt(Constants.FRAGMENT_REMINDER_TYPE);

        }catch (Exception e)
        {

        }
        initUI(view);
                imageIncome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i=new Intent(getActivity(),EditAppointments.class);
                i.putExtra("Id",id);
                i.putExtra("Reschedule",1);
                i.putExtra(Constants.FRAGMENT_REMINDER_TYPE,reminder_fragment_type);
                startActivity(i);

            }
        });

        imageExpense.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i=new Intent(getActivity(),HISTORY_ACTIVITY.class);
                i.putExtra("Id",id);

                i.putExtra(Constants.FRAGMENT_REMINDER_TYPE,reminder_fragment_type);
                startActivity(i);

            }
        });

        imageReminder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i=new Intent(getActivity(),EditAppointments.class);
                i.putExtra("Id",id);
                i.putExtra(Constants.FRAGMENT_REMINDER_TYPE,reminder_fragment_type);
                startActivity(i);

            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
          dismiss();
            }
        });


        anim_iconReminder.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
//                textReminder.setTextColor(getResources().getColor(R.color.colorWhite));
                imageIncome.startAnimation(anim_iconIncome);
                imageIncome.setVisibility(View.VISIBLE);
            }
        });
        anim_iconIncome.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                imageExpense.setVisibility(View.VISIBLE);
//                textIncome.setTextColor(getResources().getColor(R.color.colorWhite));
                imageExpense.startAnimation(anim_iconExpense);

            }
        });
        anim_iconExpense.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
//                textExpense.setTextColor(getResources().getColor(R.color.colorWhite));

            }
        });

//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                validateform();
//            }
//        });

        return view;
    }

    private void initUI(View view) {
                imageIncome = (ImageView) view.findViewById(R.id.imageIncome);
        imageExpense = (ImageView) view.findViewById(R.id.imageExpense);
        imageReminder = (ImageView) view.findViewById(R.id.imageReminder);
//        textReminder = (TextView) findViewById(R.id.textReminder);
//        textIncome = (TextView) findViewById(R.id.textIncome);
//        textExpense = (TextView) findViewById(R.id.textexpense);

        closeBtn = (ImageView) view.findViewById(R.id.closeBtn);
        anim_iconIncome = AnimationUtils.loadAnimation(getActivity(), R.anim.type_icon_zoom);
      anim_iconExpense = AnimationUtils.loadAnimation(getActivity(), R.anim.type_icon_zoom);
        anim_iconReminder = AnimationUtils.loadAnimation(getActivity(), R.anim.type_icon_zoom);
                initializeTimerTask();
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        // TODO Auto-generated method stub
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.appointment_select_type);
//        try {
//            Bundle Extras=getIntent().getExtras();
//            id=Extras.getInt("Id");
//            reminder_fragment_type=Extras.getInt(Constants.FRAGMENT_REMINDER_TYPE);
//
//        }catch (Exception e)
//        {
//
//        }
//
//        imageIncome = (ImageView) findViewById(R.id.imageIncome);
//        imageExpense = (ImageView) findViewById(R.id.imageExpense);
//        imageReminder = (ImageView) findViewById(R.id.imageReminder);
////        textReminder = (TextView) findViewById(R.id.textReminder);
////        textIncome = (TextView) findViewById(R.id.textIncome);
////        textExpense = (TextView) findViewById(R.id.textexpense);
//
//        closeBtn = (ImageView) findViewById(R.id.closeBtn);
//
//        anim_iconIncome = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.type_icon_zoom);
//        anim_iconExpense = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.type_icon_zoom);
//        anim_iconReminder = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.type_icon_zoom);
//
////		startTimer();

//
//        imageIncome.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Intent i=new Intent(Activity_Select_type.this,EditAppointments.class);
//                i.putExtra("Id",id);
//                i.putExtra("Reschedule",1);
//                i.putExtra(Constants.FRAGMENT_REMINDER_TYPE,reminder_fragment_type);
//                startActivity(i);
//
//            }
//        });
//
//        imageExpense.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Intent i=new Intent(Activity_Select_type.this,HISTORY_ACTIVITY.class);
//                i.putExtra("Id",id);
//
//                i.putExtra(Constants.FRAGMENT_REMINDER_TYPE,reminder_fragment_type);
//                startActivity(i);
//
//            }
//        });
//
//        imageReminder.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Intent i=new Intent(Activity_Select_type.this,EditAppointments.class);
//                i.putExtra("Id",id);
//                i.putExtra(Constants.FRAGMENT_REMINDER_TYPE,reminder_fragment_type);
//                startActivity(i);
//
//            }
//        });
//
//        closeBtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                onBackPressed();
//            }
//        });
//
//
//        anim_iconReminder.setAnimationListener(new Animation.AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                // TODO Auto-generated method stub
////                textReminder.setTextColor(getResources().getColor(R.color.colorWhite));
//                imageIncome.startAnimation(anim_iconIncome);
//                imageIncome.setVisibility(View.VISIBLE);
//            }
//        });
//        anim_iconIncome.setAnimationListener(new Animation.AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                // TODO Auto-generated method stub
//                imageExpense.setVisibility(View.VISIBLE);
////                textIncome.setTextColor(getResources().getColor(R.color.colorWhite));
//                imageExpense.startAnimation(anim_iconExpense);
//
//            }
//        });
//        anim_iconExpense.setAnimationListener(new Animation.AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                // TODO Auto-generated method stub
////                textExpense.setTextColor(getResources().getColor(R.color.colorWhite));
//
//            }
//        });
//
//
//    }
//    @Override
//    protected void onPause() {
//        // TODO Auto-generated method stub
//        super.onPause();
////		stoptimertask();
//    }
//
//    @Override
//    protected void onDestroy() {
//        // TODO Auto-generated method stub
//        super.onDestroy();
////	    	stoptimertask();
//    }
//
//	/*public void startTimer() {
//        //set a new Timer
//        timer = new Timer();
//
//        //initialize the TimerTask's job
//        initializeTimerTask();
//
//        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
//        timer.schedule(timerTask, 600, 500); //
//    } */
//
//
//    /*public void stoptimertask() {
//        //stop the timer, if it's not already null
//        if (timer != null) {
//            timer.cancel();
//            timer = null;
//        }
//    } */
//
    public void initializeTimerTask() {


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms

                imageReminder.startAnimation(anim_iconReminder);
                imageReminder.setVisibility(View.VISIBLE);
            }
        }, 400);


        /*timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {

                    	stoptimertask();
                    }
                });
            }
        }; */
    }

}
