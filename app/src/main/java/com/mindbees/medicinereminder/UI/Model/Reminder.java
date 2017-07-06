package com.mindbees.medicinereminder.UI.Model;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by User on 14-12-2016.
 */

public class Reminder {
  private int id;
    private   String name;
    private   boolean isCompleted;
    private    boolean isRepeating;
    private int repeatType;
    private   int repeatInterval;
    private int hour;
    private int minutes;

    private   String dateCreated;
    private   String dateModified;
    private String fromDate;
    private String dateDue;
    private   String notes;
    private  int Pid;
    private Calendar dateCreatedCal;
    private Calendar dateModifiedCal;
    private Calendar dateDueCal;

    public  Reminder(int id,String name,boolean isCompleted,int hour,int minutes,boolean isRepeating,int repeatType,int repeatInterval,String dateCreated,String fromDate,String dateModified,String dateDue,String notes,int Pid)
    {this.id=id;
        this.name=name;
        this.isCompleted=isCompleted;
        this.hour=hour;
        this.minutes=minutes;
        this.isRepeating=isRepeating;
        this.repeatType=repeatType;
        this.repeatInterval=repeatInterval;
        this.dateCreated=dateCreated;
        this.dateModified=dateModified;
        this.fromDate=fromDate;
        this.dateDue=dateDue;
        this.notes=notes;
        this.Pid=Pid;
//        updateDateCreatedCal();
//        updateDateModifiedCal();
//        updateDateDueCal();
    }

    public int getHour() {
        return hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

//    private void updateDateDueCal() {
//        if (dateDueCal == null)
//            dateDueCal = new GregorianCalendar();
//
//        dateDueCal.setTimeInMillis(dateDue);
//    }

//    public Calendar getDateCreatedCal() {
//        return dateCreatedCal;
//    }
//
//    public Calendar getDateDueCal() {
//        return dateDueCal;
//    }
//
//    public Calendar getDateModifiedCal() {
//        return dateModifiedCal;
//    }
//
//    public void setDateModifiedCal(Calendar dateModifiedCal) {
//        this.dateModifiedCal = dateModifiedCal;
//        updateDateModifiedCal();
//    }
//
//    public void setDateCreatedCal(Calendar dateCreatedCal) {
//        this.dateCreatedCal = dateCreatedCal;
//        updateDateCreatedCal();
//    }
//
//    public void setDateDueCal(Calendar dateDueCal) {
//        this.dateDueCal = dateDueCal;
//    }
//
//    private void updateDateModifiedCal() {
//        if (dateModifiedCal == null)
//            dateModifiedCal = new GregorianCalendar();
//
//        dateModifiedCal.setTimeInMillis(dateModified);
//    }
//
//    private void updateDateCreatedCal() {
//        if (dateCreatedCal == null)
//            dateCreatedCal = new GregorianCalendar();
//
//        dateCreatedCal.setTimeInMillis(dateCreated);
//    }

    public int getPid() {
        return Pid;
    }

    public void setPid(int pid) {
        Pid = pid;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isRepeating() {
        return isRepeating;
    }

    public int getId() {
        return id;
    }

    public int getRepeatInterval() {
        return repeatInterval;
    }

    public int getRepeatType() {
        return repeatType;
    }

    public String getName() {
        return name;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
//        updateDateDueCal();
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
//        updateDateModifiedCal();
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRepeating(boolean repeating) {
        isRepeating = repeating;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setRepeatInterval(int repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public void setRepeatType(int repeatType) {
        this.repeatType = repeatType;
    }

}
