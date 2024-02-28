package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ReminderAdapter extends ArrayAdapter<Reminder> {
    private final ArrayList<Reminder> reminderData;
    private View.OnClickListener mOnItemClickListener;
    private boolean isDeleting;
    private final Context parentContext;

    public ReminderAdapter(ArrayList<Reminder> arrayList, Context context) {
        super(context, R.layout.list_reminders, arrayList);
        reminderData = arrayList;
        parentContext = context;
    }

    public void setmOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_reminders, parent, false);
        }

        Reminder reminder = reminderData.get(position);

        TextView textSubject = convertView.findViewById(R.id.textSubject);
        TextView textDate = convertView.findViewById(R.id.textDate);
        TextView textCriticality = convertView.findViewById(R.id.textCriticality);

        textSubject.setText(reminder.getSubject());
        textDate.setText(String.valueOf(reminder.getDate().getTime()));
        textCriticality.setText(reminder.getPriority());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(v);
                }
            }
        });

        return convertView;
    }

    public void setDelete(boolean b) {
        isDeleting = b;
    }

    private void deleteItem(int position) {
        Reminder reminder = reminderData.get(position);
        ReminderDataSource ds = new ReminderDataSource(parentContext);
        try {
            ds.open();
            boolean didDelete = ds.deleteReminder(reminder.getReminderID());
            ds.close();
            if (didDelete) {
                reminderData.remove(position);
                notifyDataSetChanged();
            } else {
                Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
        }
    }
}
