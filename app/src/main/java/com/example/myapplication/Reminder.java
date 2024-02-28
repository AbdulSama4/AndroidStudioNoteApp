package com.example.myapplication;

import java.util.Calendar;

public class Reminder {
    private int reminderID;

    private String subject;

    private String description;

    private Calendar date;

    private int priority;

    public Reminder() {
        reminderID = -1;
        date = Calendar.getInstance();
        priority = -1;
    }

    public int getReminderID() {
        return reminderID;
    }

    public void setReminderID(int reminderID) {
        this.reminderID = reminderID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
