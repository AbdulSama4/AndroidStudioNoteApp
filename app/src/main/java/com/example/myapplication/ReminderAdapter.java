package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReminderAdapter extends RecyclerView.Adapter {
    private final ArrayList<Reminder> reminderData;
    private View.OnClickListener mOnItemClickListener;
    private boolean isDeleting;
    private Context parentContext;

    public ReminderAdapter(ArrayList<Reminder> arrayList, Context context) {
        reminderData = arrayList;
        parentContext = context;
    }

    public ReminderAdapter(ArrayList<Reminder> arrayList) {
        reminderData = arrayList;
    }

    public void setmOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    //@Override
    public RecyclerView.ViewHolder onCreateViewViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_reminders, parent, false);
        return new ReminderViewHolder(v);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ReminderViewHolder rvh = (ReminderViewHolder) holder;
        //rvh.getSubjectText().setText(reminderData.get(position).getSubject());
        //rvh.getDateText().setText(reminderData.get(position).getDate());
        //rvh.getCriticalityText().setText(reminderData.get(position).get());


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

    @Override
    public int getItemCount() {
        return reminderData.size();
    }

    public class ReminderViewHolder extends RecyclerView.ViewHolder {

        public TextView textSubject;
        public TextView textCriticality;
        public TextView textDate;

        public ReminderViewHolder(@NonNull View itemView) {
            super(itemView);
            textSubject = itemView.findViewById(R.id.textSubject);
            textDate = itemView.findViewById(R.id.textDate);
            textCriticality = itemView.findViewById(R.id.textCriticality);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        public TextView getSubjectTextView() {
            return textSubject;
        }

        public TextView getDateTextView() {
            return textDate;
        }

        public TextView getCriticalityTextView() {
            return textCriticality;
        }
    }
}
