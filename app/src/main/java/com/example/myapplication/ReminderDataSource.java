package com.example.mobileappdevproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Calendar;

public class ReminderDataSource {

    private SQLiteDatabase database;
    private final ReminderDBHelper dbHelper;

    public ReminderDataSource(Context context) {
        dbHelper = new ReminderDBHelper(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }
    public boolean insertReminder(Reminder r) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            String subject = (r.getSubject() != null) ? r.getSubject() : "DefaultSubject";
            initialValues.put("subject", r.getSubject());
            initialValues.put("description", r.getDescription());
            initialValues.put("priority", r.getPriority());
            initialValues.put("date", String.valueOf(r.getDate().getTimeInMillis()));


            didSucceed = database.insert("reminder", null, initialValues) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            //Do nothing will return fasle if there is an exception
        }
        return didSucceed;
    }



    public boolean updateReminder(Reminder r) {
        boolean didSucceed = false;
        try {
            Long rowId = (long) r.getReminderID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("subject", r.getSubject());
            updateValues.put("description", r.getDescription());
            updateValues.put("priority", r.getPriority());
            updateValues.put("date", String.valueOf(r.getDate().getTimeInMillis()));

            didSucceed = database.update("reminder", updateValues, "_id=" + rowId, null) > 0;
        } catch (Exception e) {
        }
        return didSucceed;
    }

    public int getLastReminderID() {
        int lastId = -1;
        try {
            String query = "Select MAX(_id) from reminder";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        } catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }

    //public ArrayList<String> getSubject() {
    // ArrayList<String> subject = new ArrayList<>();
    //try {
    // String query = "Select subject from reminder";
    // Cursor cursor = database.rawQuery(query, null);

    // cursor.moveToFirst();
    // while (!cursor.isAfterLast()) {
    //   subject.add(cursor.getString(0));
    //    cursor.moveToNext();
    //  }
    //  cursor.close();
    // } catch (Exception e) {
    //     subject = new ArrayList<String>();
    // }
    // return subject;
    //}

    public ArrayList<Reminder> getReminders(String sortField, String sortOrder) {
        ArrayList<Reminder> reminders = new ArrayList<>();
        try {
            String query = "SELECT * FROM reminder ORDER BY" + sortField + " " + sortOrder;
            Cursor cursor = database.rawQuery(query, null);
            Reminder newReminder;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newReminder = new Reminder();
                newReminder.setReminderID(cursor.getInt(0));
                newReminder.setSubject(cursor.getString(1));
                newReminder.setDescription(cursor.getString(2));
                newReminder.setPriority(cursor.getInt(3));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.valueOf(cursor.getString(4)));
                newReminder.setDate(calendar);

                reminders.add(newReminder);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            reminders = new ArrayList<Reminder>();
        }
        return reminders;
    }
    public Reminder getSpecificReminder(int reminderId) {
        Reminder reminder = new Reminder();
        String query = "SELECT * FROM reminder WHERE _id = " + reminderId;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            reminder.setReminderID(cursor.getInt(0));
            reminder.setSubject(cursor.getString(1));
            reminder.setDescription(cursor.getString(2));
            reminder.setPriority(cursor.getInt(3));

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.valueOf(cursor.getString(4)));
            reminder.setDate(calendar);
            cursor.close();
        }
        return reminder;
    }
    public boolean deleteReminder(int reminderId) {
        boolean didDelete = false;
        try {
            didDelete = database.delete("reminder", "_id=" + reminderId, null) > 0;
        } catch (Exception e) {
        }
        return didDelete;
    }
}

    }
}


