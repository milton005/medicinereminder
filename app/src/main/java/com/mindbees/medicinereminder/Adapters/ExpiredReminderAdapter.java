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
import android.widget.TextView;

import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Model.Reminder;
import com.mindbees.medicinereminder.UTILS.Constants;
import com.mindbees.medicinereminder.UTILS.Util;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by User on 27-12-2016.
 */

public class ExpiredReminderAdapter extends RecyclerView.Adapter<ExpiredReminderAdapter.MyHolder> {
    private ArrayList<Reminder> arrayList;
    private Context context;
    public ExpiredReminderAdapter(Context context, ArrayList<Reminder>arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
    }
    public Reminder getObect(int position){
        return arrayList.get(position);
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_expiredviewreminderlist,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        String name=arrayList.get(position).getName();
        int hour=arrayList.get(position).getHour();
        int minutes=arrayList.get(position).getMinutes();
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
        holder.Remindername.setText(name);
        holder.time.setText(time);
        holder.created_date.setText(createddate);
        holder.expirydate.setText(expirydate);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans_Bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(context.getAssets(),"fonts/tex_gyre_adventor_bold.ttf");
        Typeface typeface2=Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans_Regular.ttf");
        holder.expirydate.setTypeface(typeface);
        holder.created_date.setTypeface(typeface);
        holder.interval.setTypeface(typeface);
        holder.time.setTypeface(typeface1);
        holder.Remindername.setTypeface(typeface);
        holder.eHead.setTypeface(typeface2);
        holder.cHead.setTypeface(typeface2);
        holder.iHead.setTypeface(typeface2);
        Calendar cal=Calendar.getInstance();
        String[]date=arrayList.get(position).getDateDue().split("/");
        int day=Integer.parseInt(date[0]);
        int month=Integer.parseInt(date[1]);
        int year=Integer.parseInt(date[2]);
        cal.set(Calendar.DAY_OF_MONTH,day);
        cal.set(Calendar.MONTH,((month-1)));
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.HOUR_OF_DAY,arrayList.get(position).getHour());
        cal.set(Calendar.MINUTE,arrayList.get(position).getMinutes());
        cal.set(Calendar.SECOND,0);
        int cancel_all = Util.getUtils().getPref(Constants.CANCEL_ALL);
        int cancel=arrayList.get(position).getPid();
        if(interval==0) {
            if (cancel == 0) {
                if (cal.getTimeInMillis() > System.currentTimeMillis()) {
                    if (cancel_all == 0) {
                        holder.status.setText("Active");
                        holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.register_blue));
                    } else {
                        holder.status.setText("Cancelled");
                        holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.sub_text_black));
                    }


                } else {
                    holder.status.setText("EXPIRED");
                    holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.expired_orange));
                }
            } else {
                holder.status.setText("Cancelled");
                holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.sub_text_black));
            }

        }
        else
        {
            Calendar next=Calendar.getInstance();
            String[] date1 = arrayList.get(position).getDateDue().split("/");
            int day1 = Integer.parseInt(date1[0]);
            int month1 = Integer.parseInt(date1[1]);
            int year1= Integer.parseInt(date1[2]);
            next.set(Calendar.DAY_OF_MONTH, day1);
            next.set(Calendar.MONTH, ((month1 - 1)));
            next.set(Calendar.YEAR, year1);
            next.set(Calendar.HOUR_OF_DAY,23);
            next.set(Calendar.MINUTE,59);
            next.set(Calendar.SECOND,0);
            if (cancel == 0) {
                if (next.getTimeInMillis() > System.currentTimeMillis()) {
                    if (cancel_all == 0) {
                        holder.status.setText("Active");
                        holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.register_blue));
                    } else {
                        holder.status.setText("Cancelled");
                        holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.sub_text_black));
                    }


                } else {
                    holder.status.setText("EXPIRED");
                    holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.expired_orange));
                }
            } else {
                holder.status.setText("Cancelled");
                holder.imageView.setColorFilter(ContextCompat.getColor(context, R.color.sub_text_black));
            }
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

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView Remindername,time,created_date,expirydate,interval,status;
        ImageView imageView;
        TextView cHead,eHead,iHead;
        public MyHolder(View itemView) {
            super(itemView);
            Remindername= (TextView) itemView.findViewById(R.id.expiredmedicineremindername);
            time= (TextView) itemView.findViewById(R.id.expiremedicineremindernametime);
            created_date= (TextView) itemView.findViewById(R.id.expiredmedicineremindercreated_date);
            expirydate= (TextView) itemView.findViewById(R.id.expiredmedicinereminderexpirydate);
            interval= (TextView) itemView.findViewById(R.id.expiredmedicinereminderinterval);
            status= (TextView) itemView.findViewById(R.id.expiredStatus);
            imageView= (ImageView) itemView.findViewById(R.id.expiredStatusimage);
            cHead= (TextView) itemView.findViewById(R.id.expiredmedicineremindercreated_datehead);
            eHead= (TextView) itemView.findViewById(R.id.expiredmedicinereminderexpirydatehead);
            iHead= (TextView) itemView.findViewById(R.id.expiredmedicinereminderintervalhead);
        }
    }
}
