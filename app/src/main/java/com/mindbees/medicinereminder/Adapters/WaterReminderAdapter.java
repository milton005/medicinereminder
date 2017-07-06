package com.mindbees.medicinereminder.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Model.Reminder;
import com.mindbees.medicinereminder.UTILS.Constants;
import com.mindbees.medicinereminder.UTILS.Util;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by User on 12-01-2017.
 */

public class WaterReminderAdapter extends RecyclerView.Adapter<WaterReminderAdapter.MyVIewHOlder> {
    private ArrayList<Reminder> arrayList;
    private Context context;
    public WaterReminderAdapter(Context context,ArrayList<Reminder>arrayList)
    {
        this.arrayList=arrayList;
        this.context=context;
    }
    public Reminder getObect(int position){
        return arrayList.get(position);
    }
    @Override
    public MyVIewHOlder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_water_reminder,parent,false);
        return new MyVIewHOlder(view);
    }

    @Override
    public void onBindViewHolder(MyVIewHOlder holder, int position) {
        String name=arrayList.get(position).getName();
        int hour=arrayList.get(position).getHour();
        int minutes=arrayList.get(position).getMinutes();
        String notes=arrayList.get(position).getNotes();
        String time=updateTime(hour,minutes);
        String createddate=arrayList.get(position).getFromDate();
        String expirydate=arrayList.get(position).getDateDue();
        int interval=arrayList.get(position).getRepeatInterval();
        if(interval<60)
        {
            holder.interval.setText(interval+" Minutes");
        }
        else
        {
            int h=interval/60;
            int m=interval%60;
            holder.interval.setText(h+" : "+m+" Hours");
        }
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans_Bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(context.getAssets(),"fonts/tex_gyre_adventor_bold.ttf");
        Typeface typeface2=Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans_Regular.ttf");
        holder.Head.setTypeface(typeface);
        holder.intervalHead.setTypeface(typeface1);
        holder.timeHead.setTypeface(typeface1);
        holder.time.setTypeface(typeface2);
        holder.interval.setTypeface(typeface2);
        holder.time.setText(time);
        Calendar cal=Calendar.getInstance();
        String[]date=arrayList.get(position).getDateDue().split("/");
        int day=Integer.parseInt(date[0]);
        int month=Integer.parseInt(date[1]);
        int year=Integer.parseInt(date[2]);
        cal.set(Calendar.DAY_OF_MONTH,day);
        cal.set(Calendar.MONTH,((month-1)));
        cal.set(Calendar.YEAR,year);

        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.HOUR_OF_DAY,23);
        int cancel=arrayList.get(position).getPid();
        int cancel_all= Util.getUtils().getPref(Constants.CANCEL_ALL);

        if (cancel==0) {
            if (cal.getTimeInMillis() > System.currentTimeMillis()) {
                if(cancel_all==0){
                    holder.status.setText("Active");
                  holder.statusimage.setColorFilter(ContextCompat.getColor(context,R.color.register_blue));
                }
                else {
                    holder.status.setText("Cancelled");
                    holder.statusimage.setColorFilter(ContextCompat.getColor(context, R.color.sub_text_black));
                }

            } else {
                holder.status.setText("EXPIRED");
              holder.statusimage.setColorFilter(ContextCompat.getColor(context,R.color.expired_orange));
            }
        }
        else
        {
            holder.status.setText("Cancelled");
           holder.statusimage.setColorFilter(ContextCompat.getColor(context,R.color.sub_text_black));
        }

    }
    private String updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";

        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String HrsTime_12 = new StringBuilder().append(hours).append(':')
                .append(minutes).append(timeSet).toString();

        return HrsTime_12;

    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyVIewHOlder extends RecyclerView.ViewHolder {
        TextView time,interval,status;
        TextView Head,timeHead,intervalHead;
        ImageView statusimage;
        public MyVIewHOlder(View itemView) {
            super(itemView);
            time= (TextView) itemView.findViewById(R.id.water_reminder_time_textview);
            interval= (TextView) itemView.findViewById(R.id.water_reminder_interval_textview);
            status= (TextView) itemView.findViewById(R.id.water_reminder_status_textview);
            Head= (TextView) itemView.findViewById(R.id.WaterreminderHead);
            timeHead= (TextView) itemView.findViewById(R.id.water_reminder_time_textviewhead);
            intervalHead= (TextView) itemView.findViewById(R.id.water_reminder_interval_textviewhead);
            statusimage= (ImageView) itemView.findViewById(R.id.water_reminder_status_image);


        }
    }
}
