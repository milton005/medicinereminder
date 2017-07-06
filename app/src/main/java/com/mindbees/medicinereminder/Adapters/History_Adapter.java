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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindbees.medicinereminder.R;
import com.mindbees.medicinereminder.UI.Model.Reminder;
import com.mindbees.medicinereminder.UTILS.Constants;
import com.mindbees.medicinereminder.UTILS.Util;

import java.util.ArrayList;
import java.util.Calendar;

import static com.mindbees.medicinereminder.R.id.history_Reminder_expiry;

/**
 * Created by User on 23-01-2017.
 */

public class History_Adapter extends RecyclerView.Adapter<History_Adapter.MyVIEWHOLDER> {
    private ArrayList<Reminder> arrayList;
    private Context context;
    public History_Adapter(ArrayList<Reminder>arrayList,Context context)
    {
        this.context=context;
        this.arrayList=arrayList;
    }
    public Reminder getObect(int position){
        return arrayList.get(position);
    }
    @Override
    public MyVIEWHOLDER onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_history,parent,false);
        return new MyVIEWHOLDER(view);
    }

    @Override
    public void onBindViewHolder(MyVIEWHOLDER holder, int position) {
        String name=arrayList.get(position).getName();
        int hour=arrayList.get(position).getHour();
        int minutes=arrayList.get(position).getMinutes();
        String notes=arrayList.get(position).getNotes();
        String time=updateTime(hour,minutes);
        boolean iscompleted=arrayList.get(position).isCompleted();
        String createddate=arrayList.get(position).getFromDate();
        String expirydate=arrayList.get(position).getDateDue();
        int interval=arrayList.get(position).getRepeatInterval();
        int type=arrayList.get(position).getRepeatType();
        if (type==1)
        {
            holder.layoutRem.setVisibility(View.VISIBLE);
//            holder.layout.setVisibility(View.VISIBLE);
            holder.layoutApp.setVisibility(View.GONE);

            if(interval<60)
            {
                holder.Rinterval.setText(interval+" Minutes");
            }
            else
            {
                int h=interval/60;
                int m=interval%60;
                holder.Rinterval.setText(h+" : "+m+" Hours");
            }
            holder.Rcreated.setText(createddate);
            holder.Rexpiry.setText(expirydate);
            holder.RtimeRem.setText(time);
            holder.Rdescription.setText(notes);
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
            if (iscompleted==true)
            {
                holder.AStatus.setText("Completed");
                holder.AStatusimage.setColorFilter(ContextCompat.getColor(context,R.color.colorPrimary));
            }
            else {
                if (cancel == 0) {
                    if (cal.getTimeInMillis() > System.currentTimeMillis()) {
                        if (cancel_all == 0) {
                            holder.RStatus.setText("Active");
                            holder.Rstatusimage.setColorFilter(ContextCompat.getColor(context, R.color.register_blue));
                        } else {
                            holder.RStatus.setText("Cancelled");
                            holder.Rstatusimage.setColorFilter(ContextCompat.getColor(context, R.color.sub_text_black));
                        }


                    } else {
                        holder.RStatus.setText("EXPIRED");
                        holder.Rstatusimage.setColorFilter(ContextCompat.getColor(context, R.color.expired_orange));
                    }
                } else {
                    holder.RStatus.setText("Cancelled");
                    holder.Rstatusimage.setColorFilter(ContextCompat.getColor(context, R.color.sub_text_black));
                }
            }

        }
        if (type==2)
        {
            holder.ADate.setText(expirydate);
            holder.ATime.setText(time);
            holder.ADescription.setText(notes);
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
            iscompleted=arrayList.get(position).isCompleted();
            if (iscompleted==true)
            {
                holder.AStatus.setText("Completed");
                holder.AStatusimage.setColorFilter(ContextCompat.getColor(context,R.color.colorPrimary));
            }
            else {


                if (cancel == 0) {
                    if (cal.getTimeInMillis() > System.currentTimeMillis()) {
                        if (cancel_all == 0) {
                            holder.AStatus.setText("Active");
                            holder.AStatusimage.setColorFilter(ContextCompat.getColor(context,R.color.register_blue));
                        } else {
                            holder.AStatus.setText("Cancelled");
                            holder.AStatusimage.setColorFilter(ContextCompat.getColor(context,R.color.sub_text_black));
                        }


                    } else {
                        holder.AStatus.setText("EXPIRED");
                        holder.AStatusimage.setColorFilter(ContextCompat.getColor(context,R.color.expired_orange));
                    }
                } else {
                    holder.AStatus.setText("Cancelled");
                    holder.AStatusimage.setColorFilter(ContextCompat.getColor(context,R.color.sub_text_black));
                }
            }

        }
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans_Bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(context.getAssets(),"fonts/tex_gyre_adventor_bold.ttf");
        Typeface typeface2=Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans_Regular.ttf");
        holder.AStatus.setTypeface(typeface);
        holder.AStatusHEad.setTypeface(typeface);
        holder.RStatusHEad.setTypeface(typeface);
        holder.RStatus.setTypeface(typeface);
        holder.RtimeRem.setTypeface(typeface1);
        holder.Rdescription.setTypeface(typeface1);
        holder.ADescription.setTypeface(typeface1);
        holder.AdateHead.setTypeface(typeface2);
        holder.ATimeHead.setTypeface(typeface2);
        holder.ADate.setTypeface(typeface);
        holder.ATime.setTypeface(typeface);
        holder.Rcreated.setTypeface(typeface);
        holder.Rexpiry.setTypeface(typeface);
        holder.Rinterval.setTypeface(typeface);
        holder.RcreatedHead.setTypeface(typeface2);
        holder.RexpiryHead.setTypeface(typeface2);
        holder.RintervalHead.setTypeface(typeface2);
//        Calendar cal=Calendar.getInstance();
//        String[]date=arrayList.get(position).getDateDue().split("/");
//        int day=Integer.parseInt(date[0]);
//        int month=Integer.parseInt(date[1]);
//        int year=Integer.parseInt(date[2]);
//        cal.set(Calendar.DAY_OF_MONTH,day);
//        cal.set(Calendar.MONTH,((month-1)));
//        cal.set(Calendar.YEAR,year);
//
//        cal.set(Calendar.MINUTE,59);
//        cal.set(Calendar.HOUR_OF_DAY,23);
//        int cancel=arrayList.get(position).getPid();
//        int cancel_all= Util.getUtils().getPref(Constants.CANCEL_ALL);
//
//        if (iscompleted==true)
//        {
//            holder.Status.setText("Completed");
//            holder.Status.setTextColor(Color.GREEN);
//        }
//        else {
//
//
//            if (cancel == 0) {
//
//                if (cal.getTimeInMillis() > System.currentTimeMillis()) {
//                    if (cancel_all == 0) {
//                        holder.Status.setText("Active");
//                        holder.Status.setTextColor(Color.GREEN);
//                    } else
//                        holder.Status.setText("Cancelled");
//                    holder.Status.setTextColor(Color.BLUE);
//
//                } else {
//                    holder.Status.setText("EXPIRED");
//                    holder.Status.setTextColor(Color.RED);
//                }
//            } else {
//                holder.Status.setText("Cancelled");
//                holder.Status.setTextColor(Color.BLUE);
//            }
//        }
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

    public class MyVIEWHOLDER extends RecyclerView.ViewHolder {
        TextView ATime,ADate,ADescription,AStatus;
        TextView ATimeHead,AdateHead,AStatusHEad;
        ImageView AStatusimage;
        TextView        Rcreated,Rexpiry,RtimeRem,Rinterval,RStatusHEad,RStatus,Rdescription,RcreatedHead,RexpiryHead,RintervalHead;
        ImageView Rstatusimage;
        LinearLayout layout;
       LinearLayout layoutRem,layoutApp;
        public MyVIEWHOLDER(View itemView) {
            super(itemView);
            ATime= (TextView) itemView.findViewById(R.id.HIstorytime);


            ADate= (TextView) itemView.findViewById(R.id.HIstoryCreatedDate);
            ADescription= (TextView) itemView.findViewById(R.id.History_Description);
            AStatus= (TextView) itemView.findViewById(R.id.History_Status);
            ATimeHead= (TextView) itemView.findViewById(R.id.HIstorytimeHead);
            AdateHead= (TextView) itemView.findViewById(R.id.HIstoryCreatedDatehead);
            AStatusHEad= (TextView) itemView.findViewById(R.id.History_Statushead);
            AStatusimage= (ImageView) itemView.findViewById(R.id.History_Statusimage);

            layoutRem= (LinearLayout) itemView.findViewById(R.id.history_Reminder_layout);
            layoutApp= (LinearLayout) itemView.findViewById(R.id.history_apointment_layout);

            Rinterval= (TextView) itemView.findViewById(R.id.History_Interval);
            RintervalHead= (TextView) itemView.findViewById(R.id.History_IntervalHead);
            Rcreated= (TextView) itemView.findViewById(R.id.history_Reminder_created);
            RcreatedHead= (TextView) itemView.findViewById(R.id.history_Reminder_createdhead);
            Rexpiry= (TextView) itemView.findViewById(history_Reminder_expiry);
            RexpiryHead= (TextView) itemView.findViewById(R.id.history_Reminder_expiryHead);
            RStatus= (TextView) itemView.findViewById(R.id.History_StatusREM);
            RStatusHEad=(TextView) itemView.findViewById(R.id.History_StatusREMHEAD);
            Rdescription=(TextView) itemView.findViewById(R.id.History_REMDescription);
            RtimeRem= (TextView) itemView.findViewById(R.id.history_Reminder_time);

            Rstatusimage= (ImageView) itemView.findViewById(R.id.History_StatusREmHeadimage);

        }
    }
}
