package com.mindbees.medicinereminder.UI;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindbees.medicinereminder.Adapters.ActiveReminderAdapter;
import com.mindbees.medicinereminder.Adapters.All_Reminder_adapter;
import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Base.BaseFragment;
import com.mindbees.medicinereminder.UI.DataBase.DataSource;
import com.mindbees.medicinereminder.UI.Model.Reminder;
import com.mindbees.medicinereminder.UI.Model.TaskAlarm;
import com.mindbees.medicinereminder.UTILS.Constants;
import com.mindbees.medicinereminder.UTILS.ItemClickSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Created by User on 25-01-2017.
 */

public class All_Reminder_Fragment extends BaseFragment{
    private RecyclerView recyclerView;
    private ArrayList<Reminder> arrayList;
    private ArrayList<Reminder>reverse;
    private ArrayList<Reminder>activelist;
    private All_Reminder_adapter adapter;
    DataSource db;
    FragmentTransaction mFragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.all_reminder_layout,null);
        initui(v);

        arrayList=new ArrayList<>();
        activelist=new ArrayList<>();
        reverse=new ArrayList<>();
        db=DataSource.getInstance(getContext());
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

                int type=task.getRepeatType();
                if(type==1) {
                    activelist.add(task);
                }



        }
        reverse=activelist;
        Collections.reverse(reverse);
        initListner();
        setRecyclerView();
        return v;
    }

    private void setRecyclerView() {
        adapter = new All_Reminder_adapter(getActivity(), reverse);
        recyclerView.setAdapter(adapter);
    }

    private void initui(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleview_viewAllReminderlist);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
    }
    private void initListner() {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Reminder reminder= adapter.getObect(position);
                int id=reminder.getId();
                Bundle args = new Bundle();
                args.putInt("Id", id);
                args.putInt(Constants.FRAGMENT_REMINDER_TYPE,0);
                Activity_Select_type_2 p=Activity_Select_type_2.newInstance();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                p.setArguments(args);
                p.show(fm,"TaG");
//                Intent intent=new Intent(getActivity(),Activity_Select_type_2.class);
//                intent.putExtra("Id",id);
//                intent.putExtra(Constants.FRAGMENT_REMINDER_TYPE,0);
//                startActivity(intent);

            }
        });
        ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                Reminder reminder= adapter.getObect(position);
                final int id=reminder.getId();
                new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle)
                        .setTitle("Pill Reminder")
                        .setMessage("Do you want to delete Reminder ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.deleteReminder(id);
                                TaskAlarm alarm=new TaskAlarm();
                                alarm.cancelAlarm(getActivity(),id);
                                Intent intent = getActivity().getIntent();
                                intent.putExtra(Constants.FRAG_TYPE,2);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                getActivity().overridePendingTransition(0, 0);
                                getActivity().finish();

                                getActivity().overridePendingTransition(0, 0);
                                startActivity(intent);
                            }
                        }).setNegativeButton("No",null).show();
                return false;
            }
        });

    }

}
