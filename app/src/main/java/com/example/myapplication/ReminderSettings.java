package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class ReminderSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initAllButton();
        initSettingsButton();
    }

    private void initAllButton() {
        ImageButton ibAll = findViewById(R.id.allButton);
        ibAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ReminderSettings.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton() {
        ImageButton ibSetting = findViewById(R.id.settingButton);
        ibSetting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(ReminderSettings.this, ReminderSettings.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettings() {
        // Adjusted to match the SharedPreferences keys and default values in your friend's code
        String sortBy = getSharedPreferences("MyReminderPreferences", Context.MODE_PRIVATE)
                .getString("sortfield", "subject");
        String sortOrder = getSharedPreferences("MyReminderPreferences", Context.MODE_PRIVATE)
                .getString("sortorder", "ASC");
        RadioButton radioSortSubject = findViewById(R.id.sortSubject);
        RadioButton radioSortDate = findViewById(R.id.sortDate);
        RadioButton radioSortCriticality = findViewById(R.id.sortCriticality);

        if (sortBy.equalsIgnoreCase("subject")) {
            radioSortSubject.setChecked(true);
        } else if (sortBy.equalsIgnoreCase("date")) {
            radioSortDate.setChecked(true);
        } else {
            radioSortCriticality.setChecked(true);
        }

        // Initialize and set the sort order RadioButtons
        RadioButton radioASC = findViewById(R.id.sortAscending);
        RadioButton radioDSC = findViewById(R.id.sortDescending);

        if (sortOrder.equalsIgnoreCase("ASC")) {
            radioASC.setChecked(true);
        } else {
            radioDSC.setChecked(true);
        }
    }

    private void initSortByClick() {
        // No changes needed, remains the same as your original code
    }

    private void initSortOrderClick() {
        // No changes needed, remains the same as your original code
    }
}

