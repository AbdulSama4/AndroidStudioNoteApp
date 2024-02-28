package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

public class NewEditReminder extends AppCompatActivity implements CalendarDatePickerDialog.SaveDateListener {

    private Reminder currentReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        initAllButton();
        initSettingsButton();
        initSaveButton();
        initChangeDateButton();
        initToggleOnOffButton();
        initTextChangedEvents();
    }

    private void initAllButton() {
        ImageButton ibAll = findViewById(R.id.allButton);
        ibAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(NewEditReminder.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton() {
        ImageButton ibSetting = findViewById(R.id.settingButton);
        ibSetting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(NewEditReminder.this, ReminderSettings.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initToggleOnOffButton() {
        final ToggleButton editToggle = findViewById(R.id.offToggleButton);
        editToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setForEditing(editToggle.isChecked());
            }


        });
    }

    private void setForEditing(boolean enabled) {
        EditText editSubject = findViewById(R.id.editSubject);
        EditText editDescription = findViewById(R.id.editDescription);
        Button editDate = findViewById(R.id.calendarButton);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        editSubject.setEnabled(enabled);
        editDescription.setEnabled(enabled);
        editDate.setEnabled(enabled);
        radioGroup.setEnabled(enabled);
    }


    @Override
    public void didFinishCalendarDatePickerDialog(Calendar selectedTime) {
        if (currentReminder != null) { // Check if currentReminder is not null
            TextView date = findViewById(R.id.textViewCalendar);
            date.setText(DateFormat.format("MM/dd/yyyy", selectedTime));
            currentReminder.setDate(selectedTime);
        }

    }

    private void initChangeDateButton() {
        Button changeDate = findViewById(R.id.calendarButton);
        changeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentReminder == null) {
                    currentReminder = new Reminder();
                }
                FragmentManager fm = getSupportFragmentManager();
                CalendarDatePickerDialog calendarDatePickerDialog = new CalendarDatePickerDialog(currentReminder);
                calendarDatePickerDialog.show(fm, "DatePick");
            }
        });
    }

    private void initTextChangedEvents() {
        final EditText etSubject = findViewById(R.id.editSubject);
        final EditText etDescription = findViewById(R.id.editDescription);
        final ToggleButton editToggle = findViewById(R.id.offToggleButton);

        etSubject.setEnabled(editToggle.isChecked());
        etDescription.setEnabled(editToggle.isChecked());

        editToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean enabled = editToggle.isChecked();
                etSubject.setEnabled(enabled);
                etDescription.setEnabled(enabled);
            }
        });

        etSubject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (currentReminder != null) {
                    currentReminder.setSubject(etSubject.getText().toString());
                }
            }
        });

        etDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (currentReminder != null) {
                    currentReminder.setDescription(etDescription.getText().toString());
                }
            }
        });
    }

    private void initSaveButton() {
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                boolean wasSuccessful;
                ReminderDataSource ds = new ReminderDataSource(NewEditReminder.this);
                try {
                    ds.open();

                    if (currentReminder.getReminderID() == -1) {
                        wasSuccessful = ds.insertReminder(currentReminder);
                        if (wasSuccessful) {

                            int newId = ds.getLastReminderID();

                            currentReminder.setReminderID(newId);

                        }
                    } else {
                        wasSuccessful = ds.updateReminder(currentReminder);
                    }
                    ds.close();
                } catch (Exception e) {
                    wasSuccessful = false;
                }
                if (wasSuccessful) {
                    ToggleButton editToggle = findViewById(R.id.offToggleButton);
                    editToggle.toggle();
                    setForEditing(false);
                }

            }

        });
    }

    public void initPriorityButtons() {
        RadioGroup rgPriority = findViewById(R.id.radioGroup);
        rgPriority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbLow = findViewById(R.id.radioLowButton);
                RadioButton rbMedium = findViewById(R.id.radioMediumButton);
                RadioButton rbHigh = findViewById(R.id.radioHighButton);

                if (rbHigh.isChecked()) {
                    currentReminder.setPriority(3);
                } else if (rbMedium.isChecked()) {
                    currentReminder.setPriority(2);
                } else {
                    currentReminder.setPriority(1);
                }
            }
        });
    }
}
