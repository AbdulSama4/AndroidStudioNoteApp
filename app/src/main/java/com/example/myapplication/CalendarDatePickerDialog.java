package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class CalendarDatePickerDialog extends DialogFragment {

    private final Reminder currentReminder;
    Calendar selectedDate;

    public CalendarDatePickerDialog(Reminder reminder) {
        //empty required
        this.currentReminder = reminder;
        this.selectedDate = currentReminder.getDate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_select_calendar_date, container);

        getDialog().setTitle("Select Date");
        //selectedDate = Calendar.getInstance();

        final CalendarView cv = view.findViewById(R.id.calendarView);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {

                selectedDate.set(year, month, day);
            }
        });

        Button selectButton = view.findViewById(R.id.selectButton);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem(selectedDate);
            }


        });

        Button cancelButton = view.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }


        });
        return view;

    }

    private void saveItem(Calendar selectedTime) {
        SaveDateListener activity = (SaveDateListener) getActivity();
        activity.didFinishCalendarDatePickerDialog(selectedTime);
        getDialog().dismiss();
    }

    public interface SaveDateListener {
        void didFinishCalendarDatePickerDialog(Calendar selectedTime);
    }
}
