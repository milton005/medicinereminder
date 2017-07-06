package com.mindbees.medicinereminder.UI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.DataBase.DataSource;
import com.mindbees.medicinereminder.UI.Model.Reminder;
import com.mindbees.medicinereminder.UTILS.Constants;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by User on 27-12-2016.
 */

public class AppointmentsTab extends Fragment {
    public static TabLayout tabLayout;
    DataSource db;
    int activeno;
    int exno;
    String ActiveTitle,ExpiredTitle;
    ArrayList<Reminder> arrayList;
    ArrayList<Reminder>active;
    ArrayList<Reminder>expired;
    public static int int_items = 3 ;
    ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ex = inflater.inflate(R.layout.appointments_tab_layout, null);
        tabLayout = (TabLayout) ex.findViewById(R.id.tabsApo);
        viewPager = (ViewPager) ex.findViewById(R.id.viewpagerapo);
        getActivity().setTitle("Appointments");
        db=DataSource.getInstance(getContext());
        arrayList=new ArrayList<>();
        active=new ArrayList<>();
        expired=new ArrayList<>();
        arrayList=db.getAllReminder();
        for (Reminder task : arrayList) {
            Calendar cal = Calendar.getInstance();
            String[] date = task.getDateDue().split("/");
            int day = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int year = Integer.parseInt(date[2]);
            cal.set(Calendar.DAY_OF_MONTH, day);
            cal.set(Calendar.MONTH, ((month - 1)));
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.HOUR_OF_DAY, task.getHour());
            cal.set(Calendar.MINUTE, task.getMinutes());
            cal.set(Calendar.SECOND, 0);
            if (!(cal.getTimeInMillis()>System.currentTimeMillis())){
                int type=task.getRepeatType();
                if (type==2)
                {
                    expired.add(task);
                }



            }
            else
            {
                int type=task.getRepeatType();
                if (type==2)
                {
                    active.add(task);
                }
            }
        }
        activeno=active.size();
        if (active.isEmpty())
        {
            activeno=0;
        }
        exno=expired.size();
        if (expired.isEmpty())
        {
            exno=0;
        }
        SpannableStringBuilder builder = new SpannableStringBuilder();
        String active= "Active ";
        builder.append(active);
        String activeTemp="("+activeno+")";
        SpannableString spannableString=new SpannableString(activeTemp);
        spannableString.setSpan(new ForegroundColorSpan(Color.WHITE),0,activeTemp.length(),0);
        builder.append(spannableString);
        String Ac=builder.toString();
        ActiveTitle=Ac;


//        ActiveTitle="Active "+"("+activeno+")";
        ExpiredTitle="Expired "+"("+exno+")";
        viewPager.setAdapter(new AppointmentsTab.MyAdapter(getChildFragmentManager()));
        FloatingActionButton fab = (FloatingActionButton) ex.findViewById(R.id.fabapo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),AddAppointments.class);
                startActivity(intent);
            }
        });
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        try
        {
            Bundle extras=getArguments();
            final int fragment_Reminder_type=extras.getInt(Constants.FRAGMENT_REMINDER_TYPE,0);
            viewPager.setCurrentItem(fragment_Reminder_type);
//            new Handler().postDelayed(
//                    new Runnable(){
//                        @Override
//                        public void run() {
//                            tabLayout.getTabAt(fragment_Reminder_type).select();
//                        }
//                    }, 50);
        }catch (Exception e){}
        return ex;
    }

    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager childFragmentManager) {
            super(childFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0 :return  new AllAppointments();
                case 1 : return new ActiveAppointmentsListFragment();
                case 2 : return new ExpiredAppointmentsListFragment();


            }

            return null;
        }

        @Override
        public int getCount() {
            return int_items;

        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 :
                    return "All";
                case 1 :
                    return ActiveTitle;
                case 2:
                    return ExpiredTitle;

            }
            return null;

        }
    }
}
