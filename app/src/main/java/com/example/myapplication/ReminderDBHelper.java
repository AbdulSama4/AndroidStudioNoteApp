package com.example.mobileappdevproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ReminderDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myreminders.db";
    private static final int DATABASE_VERSION = 4;

    //DB CREATION SQL STATEMENT

    private static final String CREATE_TABLE_REMINDER =
            "create table reminder (_id integer primary key autoincrement, " + "subject text not null, description text," + "priority integer, date text);";

    public ReminderDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_REMINDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ReminderDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS reminder");
        onCreate(db);

    }

}
