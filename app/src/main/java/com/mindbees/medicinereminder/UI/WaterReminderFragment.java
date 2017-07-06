package com.mindbees.medicinereminder.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindbees.medicinereminder.Adapters.WaterReminderAdapter;
import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Base.BaseFragment;
import com.mindbees.medicinereminder.UI.DataBase.DataSource;
import com.mindbees.medicinereminder.UI.Model.Reminder;
import com.mindbees.medicinereminder.UTILS.ItemClickSupport;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by User on 12-01-2017.
 */

public class WaterReminderFragment extends BaseFragment {
    private ArrayList<Reminder>waterlist;
    private RecyclerView recyclerView;
    private ArrayList<Reminder> arrayList;
    WaterReminderAdapter adapter;
    FloatingActionButton floatingActionButton;
    DataSource db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.water_reminder_layout,null);

        initui(v);
        arrayList=new ArrayList<>();
        getActivity().setTitle("Water Reminder");
        waterlist=new ArrayList<>();
        db=DataSource.getInstance(getContext());
        arrayList=db.getAllReminder();
        for (Reminder task : arrayList) {
//            Calendar cal = Calendar.getInstance();
//            String[] date = task.getDateDue().split("/");
//            int day = Integer.parseInt(date[0]);
//            int month = Integer.parseInt(date[1]);
//            int year = Integer.parseInt(date[2]);
//            cal.set(Calendar.DAY_OF_MONTH, day);
//            cal.set(Calendar.MONTH, ((month - 1)));
//            cal.set(Calendar.YEAR, year);
//            cal.set(Calendar.HOUR_OF_DAY, task.getHour());
//            cal.set(Calendar.MINUTE, task.getMinutes());
//            cal.set(Calendar.SECOND, 0);
//            if (cal.getTimeInMillis() > System.currentTimeMillis()) {
                int type=task.getRepeatType();
                if(type==3) {
                    waterlist.add(task);
                }


            }
        initListner();
        setRecyclerView();
        return (v);
    }

    private void setRecyclerView() {
        adapter=new WaterReminderAdapter(getActivity(),waterlist);
        recyclerView.setAdapter(adapter);
    }

    private void initListner() {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Reminder reminder= adapter.getObect(position);
                int id=reminder.getId();
                Intent intent=new Intent(getActivity(),SetWaterReminder.class);

                startActivity(intent);

            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),SetWaterReminder.class);
                startActivity(intent);
            }
        });
    }

    private void initui(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.water_Reminderlist);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        floatingActionButton= (FloatingActionButton) v.findViewById(R.id.fabwater);

    }
}
