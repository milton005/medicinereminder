package com.mindbees.medicinereminder.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;

import com.mindbees.medicinereminder.App.MedicineReminder;
import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Base.BaseActivity;
import com.mindbees.medicinereminder.UI.DataBase.DataSource;
import com.mindbees.medicinereminder.UTILS.Constants;

public class MedicineReminderActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    DataSource data_source;
    int fragment_reminder_type;
    private SelectItemCallBacks itemCallBacks;
    private int position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data_source = DataSource.getInstance(this);
        savePref(Constants.RESCHEDULE,0);
        setContentView(R.layout.activity_medicine_reminder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        try{
            if (getIntent().hasExtra(Constants.FRAG_TYPE)) {
                position = getIntent().getIntExtra(Constants.FRAG_TYPE, 0);
                fragment_reminder_type=getIntent().getIntExtra(Constants.FRAGMENT_REMINDER_TYPE,0);
            }
        }catch (Exception e)
        {

        }

        selectFragment();
//        try {
//            Bundle Extras = getIntent().getExtras();
//            int type = Extras.getInt("type");
//            switch (type) {
//                case 1:
//                    mFragmentTransaction.replace(R.id.frame, new TabFragment()).commit();
//                    break;
//                case 2:
//                    mFragmentTransaction.replace(R.id.frame, new AppointmentsTab()).commit();
//                    break;
//                default:
//                    mFragmentTransaction.replace(R.id.frame, new TabFragment()).commit();
//                    break;
//
//            }
//        }catch (Exception e)
//        {
//
//        }
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(MedicineReminderActivity.this,AddReminderActivity.class);
//                startActivity(intent);
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu m=navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
        navigationView.setNavigationItemSelectedListener(this);
        MenuItem menuItem = navigationView.getMenu().getItem(position-1);
        selectionMenuItem(menuItem);

    }
    private void applyFontToMenuItem(MenuItem mi) {
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/OpenSans_Bold.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , typeface), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }
    private void selectFragment() {
        switch (position-1) {
            case 1:
                TabFragment fragment=new TabFragment();
                setFragmentView2(fragment);
                break;
            case 2:
                AppointmentsTab fragmen1=new AppointmentsTab();
                setFragmentView2(fragmen1);
                break;
            case 3:SettingsFragment fragment1=new SettingsFragment();
                setFragmentView(fragment1);
                break;
            default:
                TabFragment fragment2=new TabFragment();
                setFragmentView(fragment2);
                break;


        }
    }

    private void setFragmentView2(Fragment fragment) {
        mFragmentTransaction=mFragmentManager.beginTransaction();
        Bundle data=new Bundle();

        data.putInt(Constants.FRAGMENT_REMINDER_TYPE,fragment_reminder_type);
        fragment.setArguments(data);
        mFragmentTransaction.replace(R.id.frame,fragment);
        mFragmentTransaction.commit();

    }

    private void selectionMenuItem(MenuItem menuItem) {
        if (menuItem.isChecked()) menuItem.setChecked(false);
        else menuItem.setChecked(true);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
          Intent intent=new Intent(MedicineReminderActivity.this,Home.class);
            startActivity(intent);
        }
    }
    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save some state!
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.medicine_reminder, menu);

        return true;
    }
    public void setFragmentView(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment)
                .commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.nav_Home)
        {
            finish();
            Intent intent=new Intent(MedicineReminderActivity.this,Home.class);

            intent.putExtra(Constants.FRAG_TYPE, 4);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            finish();

            overridePendingTransition(0, 0);
            startActivity(intent);


        }
        else if (id == R.id.nav_pill_reminder) {
            TabFragment fragment=new TabFragment();
           setFragmentView(fragment);
            // Handle the camera action
        } else if (id == R.id.nav_appointments) {
            AppointmentsTab fragment=new AppointmentsTab();
           setFragmentView(fragment);

        } else if (id == R.id.nav_water_reminder) {
            WaterReminderFragment fragment=new WaterReminderFragment();
            setFragmentView(fragment);

        } else if (id == R.id.nav_settings) {
            SettingsFragment fragment=new SettingsFragment();
            setFragmentView(fragment);

//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
