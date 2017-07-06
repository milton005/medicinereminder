package com.mindbees.medicinereminder.UI;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mindbees.medicinereminder.Adapters.History_Adapter;
import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Base.BaseActivity;
import com.mindbees.medicinereminder.UI.DataBase.DataSource;
import com.mindbees.medicinereminder.UI.Model.Reminder;
import com.mindbees.medicinereminder.UTILS.Constants;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by User on 23-01-2017.
 */

public class HISTORY_ACTIVITY extends BaseActivity {
    Toolbar toolbar;
    DataSource db;
    RecyclerView recyclerView;
    Reminder current;
    String Name;
    int id;
    History_Adapter adapter;
    int type;
    private ArrayList<Reminder> arrayList;
    private ArrayList<Reminder>activelist;
    private ArrayList<Reminder>reverse;
    TextView NAME;
    int Fragment_reminder_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        initUi();

        activelist=new ArrayList<>();
        arrayList=new ArrayList<>();
        reverse=new ArrayList<>();
        db=DataSource.getInstance(HISTORY_ACTIVITY.this);
        arrayList=db.getAllReminder();
        try
        {
            Bundle Extras=getIntent().getExtras();
             id=Extras.getInt("Id");
            Fragment_reminder_type=Extras.getInt(Constants.FRAGMENT_REMINDER_TYPE);
            current=db.getReminder(id);
//            activelist.add(current);
            type=current.getRepeatType();

            Name=current.getName();
            for (Reminder task : arrayList) {
                if(task.getRepeatType()==type) {
                    if (Name.equals(task.getName())) {

//                        if (id == (task.getId())) {

//                        } else {
                            activelist.add(task);
//                        }
                    }
                }

            }
            reverse=activelist;
            Collections.reverse(reverse);

            SetUPUI();
            settoolbar();


        }
        catch (Exception e)
        {

        }
    }

    private void settoolbar() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("History");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
//                Intent intent=new Intent(EditAppointments.this,MedicineReminderActivity.class);
//                savePref(Constants.RESCHEDULE,0);
//                intent.putExtra(Constants.FRAGMENT_REMINDER_TYPE,Reminder_fragment_type);
//                intent.putExtra(Constants.FRAG_TYPE,3);
//                startActivity(intent);
                break;



            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void SetUPUI() {
        adapter=new History_Adapter(reverse,HISTORY_ACTIVITY.this);
        recyclerView.setAdapter(adapter);
        NAME.setText(Name);


    }

    private void initUi() {
        recyclerView = (RecyclerView) findViewById(R.id.recycleview_HistoryApo_list);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(HISTORY_ACTIVITY.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        NAME= (TextView) findViewById(R.id.History_NAMe);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/OpenSans_Bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/tex_gyre_adventor_bold.ttf");
        Typeface typeface2=Typeface.createFromAsset(getAssets(),"fonts/OpenSans_Regular.ttf");
        NAME.setTypeface(typeface);
    }
}
