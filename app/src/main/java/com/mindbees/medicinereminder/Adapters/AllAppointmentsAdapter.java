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
 * Created by User on 18-01-2017.
 */

public class AllAppointmentsAdapter extends RecyclerView.Adapter<AllAppointmentsAdapter.MyHolder> {
    private ArrayList<Reminder> arrayList;
    private Context context;
    public AllAppointmentsAdapter(Context  context,ArrayList<Reminder>arrayList)
    {
        this.arrayList=arrayList;
        this.context=context;
    }
    @Override

    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_all_appointments,parent,false);
        return new MyHolder(view);
    }
    public Reminder getObect(int position){
        return arrayList.get(position);
    }
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        String name=arrayList.get(position).getName();
        int hour=arrayList.get(position).getHour();
        int minutes=arrayList.get(position).getMinutes();
        String notes=arrayList.get(position).getNotes();
        String time=updateTime(hour,minutes);
        String createddate=arrayList.get(position).getFromDate();
        String expirydate=arrayList.get(position).getDateDue();
        String interval=String.valueOf(arrayList.get(position).getRepeatInterval());
        holder.Appointmentname.setText(name);
        holder.time.setText(time);
        holder.description.setText(notes);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans_Bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(context.getAssets(),"fonts/tex_gyre_adventor_bold.ttf");
        Typeface typeface2=Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans_Regular.ttf");
        holder.Appointmentname.setTypeface(typeface);
        holder.Dhead.setTypeface(typeface2);
        holder.THead.setTypeface(typeface2);
        holder.date.setTypeface(typeface);
        holder.time.setTypeface(typeface);
        holder.description.setTypeface(typeface1);

        holder.date.setText(expirydate);
//        holder.interval.setText(interval);
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
        int cancel=arrayList.get(position).getPid();
        int cancel_all= Util.getUtils().getPref(Constants.CANCEL_ALL);
        boolean iscompleted=arrayList.get(position).isCompleted();
        if (iscompleted==true)
        {
            holder.status.setText("Completed");
            holder.imageView.setColorFilter(ContextCompat.getColor(context,R.color.colorPrimary));
        }
         else {


            if (cancel == 0) {
                if (cal.getTimeInMillis() > System.currentTimeMillis()) {
                    if (cancel_all == 0) {
                        holder.status.setText("Active");
                        holder.imageView.setColorFilter(ContextCompat.getColor(context,R.color.register_blue));
                    } else {
                        holder.status.setText("Cancelled");
                        holder.imageView.setColorFilter(ContextCompat.getColor(context,R.color.sub_text_black));
                    }


                } else {
                    holder.status.setText("EXPIRED");
                    holder.imageView.setColorFilter(ContextCompat.getColor(context,R.color.expired_orange));
                }
            } else {
                holder.status.setText("Cancelled");
                holder.imageView.setColorFilter(ContextCompat.getColor(context,R.color.sub_text_black));
            }
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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
    public class MyHolder extends RecyclerView.ViewHolder {
        TextView Appointmentname,time,date,description,status;
        ImageView imageView;
        TextView Dhead,THead;
        public MyHolder(View itemView) {
            super(itemView);
            Appointmentname= (TextView) itemView.findViewById(R.id.AllAppointmentname);
            time= (TextView) itemView.findViewById(R.id.AllAppointmenttime);
            date= (TextView) itemView.findViewById(R.id.AllAppointmentDate);
            description= (TextView) itemView.findViewById(R.id.AllAppointment_Description);
            status= (TextView) itemView.findViewById(R.id.AllStatusapointment);
            imageView= (ImageView) itemView.findViewById(R.id.AllStatusapointment_image);
            Dhead= (TextView) itemView.findViewById(R.id.AllAppointmentDateHead);
            THead= (TextView) itemView.findViewById(R.id.AllAppointmenttimeHead);
        }
    }
}
