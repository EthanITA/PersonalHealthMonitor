package com.dong.personalhealthmonitor.activities;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.dong.personalhealthmonitor.R;
import com.dong.personalhealthmonitor.java_classes.notifications.AlarmNotification;
import com.dong.personalhealthmonitor.java_classes.shared_preferences.GenericOperations;
import com.dong.personalhealthmonitor.java_classes.shared_preferences.SettingsOperations;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        final MaterialTimePicker.Builder builderTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H);
        GenericOperations prefs;
        Preference notPreference;
        EditTextPreference age, height_thresh, weight_thresh, temp_thresh, blood1_thresh, blood2_thresh;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }

        public void update_notification_time_preference() {
            SettingsOperations settings_prefs = new SettingsOperations(getActivity());
            Preference notPreference = findPreference("notification_time");
            builderTimePicker.setHour(settings_prefs.getHour()).setMinute(settings_prefs.getMinutes());
            notPreference.setSummary("Everyday at " + settings_prefs.getTime());
        }

        @Override
        public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            prefs = new GenericOperations(getContext());
            notPreference = findPreference("notification_time");
            age = findPreference("age");
            height_thresh =findPreference("height_thresh");
            weight_thresh =findPreference("weight_thresh");
            temp_thresh =findPreference("temp_thresh");
            blood1_thresh =findPreference("blood1_thresh");
            blood2_thresh =findPreference("blood2_thresh");
            initETOnAttach();

            update_notification_time_preference();


            notPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    update_notification_time_preference();
                    final MaterialTimePicker TimePicker = builderTimePicker.build();
                    TimePicker.show(requireFragmentManager(), "fragment_tag");

                    TimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int hour = TimePicker.getHour();
                            int minute = TimePicker.getMinute();
                            prefs.saveInt("notification_hour", hour);
                            prefs.saveInt("notification_minute", minute);
                            update_notification_time_preference();
                            SettingsOperations prefs = new SettingsOperations(view.getContext());
                            AlarmNotification alarm = new AlarmNotification(view.getContext());
                            alarm.setDailyAlarm(prefs.getHour(), prefs.getMinutes());
                        }
                    });

                    return true;
                }
            });
        }

        void initETOnAttach() {

            age.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {

                    editText.setFilters(new InputFilter[]{new MainActivity.DecimalDigitsInputFilter(2, 1)});

                }
            });
            height_thresh.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {

                    editText.setFilters(new InputFilter[]{new MainActivity.DecimalDigitsInputFilter(4, 2)});

                }
            });
            weight_thresh.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {

                    editText.setFilters(new InputFilter[]{new MainActivity.DecimalDigitsInputFilter(4, 2)});

                }
            });
            temp_thresh.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {

                    editText.setFilters(new InputFilter[]{new MainActivity.DecimalDigitsInputFilter(3, 1)});

                }
            });
            blood1_thresh.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {

                    editText.setFilters(new InputFilter[]{new MainActivity.DecimalDigitsInputFilter(4, 1)});

                }
            });
            blood2_thresh.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
                @Override
                public void onBindEditText(@NonNull EditText editText) {

                    editText.setFilters(new InputFilter[]{new MainActivity.DecimalDigitsInputFilter(4, 1)});

                }
            });
        }
    }

}