package com.example.myapplication;

import java.util.Calendar;

public class Reminder {
    private int reminderID;

    private String subject;

    private String description;

    private Calendar date;

    public Reminder() {
        reminderID = -1;
        date = Calendar.getInstance();
    }

    public int getReminderID() {
        return reminderID;
    }

    public void setReminderID(int i) {
        reminderID = i;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String s) {
        subject = s;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String s) {
        description = s;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar c) {
        date = c;
    }
}
