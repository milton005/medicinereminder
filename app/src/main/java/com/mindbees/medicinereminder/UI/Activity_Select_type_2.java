package com.mindbees.medicinereminder.UI;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment ;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
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
 * Created by User on 25-01-2017.
 */

public class Activity_Select_type_2 extends DialogFragment{
    ImageView imageIncome, imageExpense, closeBtn;
    TextView textIncome, textExpense;
    View view;
    FragmentManager mFragmentManager;
    Animation anim_iconIncome;
    Animation anim_iconExpense;
 Context context;
    Timer timer;
    TimerTask timerTask;
    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();
    int id,reminder_fragment_type;
    AlertDialog categoryDialog;
    boolean isIncome = false;
    boolean wantToCloseDialog = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.Theme_FullScren);

    }
    static Activity_Select_type_2 newInstance()
    {
        Activity_Select_type_2 p=new Activity_Select_type_2();

        return p;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_select_reminder,container,false);
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
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                validateform();
//            }
//        });
        imageExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),HISTORY_ACTIVITY.class);
                i.putExtra("Id",id);
                i.putExtra(Constants.FRAGMENT_REMINDER_TYPE,reminder_fragment_type);
                startActivity(i);
            }
        });
        imageIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),EditReminderActivity.class);
                i.putExtra("Id",id);
                i.putExtra(Constants.FRAGMENT_REMINDER_TYPE,reminder_fragment_type);
                startActivity(i);
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
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
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Intent i=new Intent(Activity_Select_type_2.this,EditReminderActivity.class);
//                i.putExtra("Id",id);
//                i.putExtra(Constants.FRAGMENT_REMINDER_TYPE,reminder_fragment_type);
//                startActivity(i);
//
//
//            }
//        });
        return view;
    }

    private void initUI(View view) {
        imageIncome = (ImageView) view.findViewById(R.id.imageIncome);
       imageExpense = (ImageView)view. findViewById(R.id.imageExpense);
//       textIncome = (TextView)view. findViewById(R.id.textIncome);
//       textExpense = (TextView)view. findViewById(R.id.textexpense);

        closeBtn = (ImageView)view. findViewById(R.id.closeBtn);

     anim_iconIncome = AnimationUtils.loadAnimation(getActivity(), R.anim.type_icon_zoom);
      anim_iconExpense = AnimationUtils.loadAnimation(getActivity(), R.anim.type_icon_zoom);
        initializeTimerTask();

    }

    private void initializeTimerTask() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                imageIncome.setVisibility(View.VISIBLE);
                imageIncome.startAnimation(anim_iconIncome);
            }
        }, 400);
    }
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_select_reminder);
//        imageIncome = (ImageView) findViewById(R.id.imageIncome);
//        imageExpense = (ImageView) findViewById(R.id.imageExpense);
//        textIncome = (TextView) findViewById(R.id.textIncome);
//        textExpense = (TextView) findViewById(R.id.textexpense);
//
//        closeBtn = (ImageView) findViewById(R.id.closeBtn);
//
//        anim_iconIncome = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.type_icon_zoom);
//        anim_iconExpense = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.type_icon_zoom);
//
//
//        initializeTimerTask();
//        try {
//            Bundle Extras=getIntent().getExtras();
//            id=Extras.getInt("Id");
//            reminder_fragment_type=Extras.getInt(Constants.FRAGMENT_REMINDER_TYPE);
//
//        }catch (Exception e)
//        {
//
//        }
//        imageIncome.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Intent i=new Intent(Activity_Select_type_2.this,EditReminderActivity.class);
//                i.putExtra("Id",id);
//                i.putExtra(Constants.FRAGMENT_REMINDER_TYPE,reminder_fragment_type);
//                startActivity(i);
//
//
//            }
//        });
//
//        imageExpense.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Intent i=new Intent(Activity_Select_type_2.this,HISTORY_ACTIVITY.class);
//                i.putExtra("Id",id);
//
//                i.putExtra(Constants.FRAGMENT_REMINDER_TYPE,reminder_fragment_type);
//                startActivity(i);
//
//            }
//        });
//
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
//                textExpense.setTextColor(getResources().getColor(R.color.colorWhite));
//
//            }
//        });
//    }
//    public void initializeTimerTask() {
//
//
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Do something after 5s = 5000ms
//                imageIncome.setVisibility(View.VISIBLE);
//                imageIncome.startAnimation(anim_iconIncome);
//            }
//        }, 400);
//    }
}
