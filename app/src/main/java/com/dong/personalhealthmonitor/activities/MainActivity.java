package com.dong.personalhealthmonitor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.dong.personalhealthmonitor.R;
import com.dong.personalhealthmonitor.java_classes.SQLite.ReportDB;
import com.dong.personalhealthmonitor.java_classes.generic.CalendarManager;
import com.dong.personalhealthmonitor.java_classes.notifications.Notification;
import com.dong.personalhealthmonitor.java_classes.shared_preferences.SettingsOperations;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    // banner
    MaterialCardView banner;
    MaterialTextView banner_selected_day;
    MaterialButton banner_get_today;

    MaterialTextView report_day;
    // to be filled by user
    TextInputEditText height_val, weight_val, temp_val, blood_val1, blood_val2;
    TextInputEditText optional_notes;
    // select which corresponding ET to save
    MaterialCheckBox height_check, weight_check, temp_check, blood_check;

    SettingsOperations prefs;
    CalendarManager my_calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Notification notification = new Notification(this);
        setContentView(R.layout.activity_main);

        prefs = new SettingsOperations(this);
        my_calendar = new CalendarManager();

        banner = findViewById(R.id.banner);
        banner_selected_day = findViewById(R.id.selected_day);
        banner_get_today = findViewById(R.id.get_today);

        height_val = findViewById(R.id.height_value);
        weight_val = findViewById(R.id.weight_value);
        temp_val = findViewById(R.id.temperature_value);
        blood_val1 = findViewById(R.id.blood_pressure1);
        blood_val2 = findViewById(R.id.blood_pressure2);

        height_check = findViewById(R.id.height_check);
        weight_check = findViewById(R.id.weight_check);
        temp_check = findViewById(R.id.temperature_check);
        blood_check = findViewById(R.id.blood_pressure_check);

        optional_notes = findViewById(R.id.optional_text);
        report_day = findViewById(R.id.report_selected_day);

        banner_selected_day.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                update_report_day();
            }
        });


        // define behaviour of EditTexts and Checkboxes
        addFiltersToVals();
        addTextWatchersToVals();
        addChangeListenerToCB();
        // banner
        updateBanner();
        initBannerClickListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initBannerClickListener() {
        banner_get_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.insertSelectedDayMillis(my_calendar.getTodayMillis());
                updateBanner();
            }
        });
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();

                builder.setCalendarConstraints(new CalendarManager().limitFutureDates().build());
                builder.setSelection(prefs.getSelectedDayMillis());
                MaterialDatePicker<Long> picker = builder.build();
                picker.show(getSupportFragmentManager(), picker.toString());
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        prefs.insertSelectedDayMillis(selection);
                        updateBanner();
                    }
                });
            }
        });
    }

    public void updateBanner() {
        banner_selected_day.setText(String.valueOf(my_calendar.getMillisToDate(prefs.getSelectedDayMillis())));

    }

    Double getter(TextInputEditText i, MaterialCheckBox c, Double defaultValue) {
        return c.isChecked() ? Double.parseDouble(i.getText().toString()) : defaultValue;
    }

    private Double getHeight() {
        return getter(height_val, height_check, 0.0);
    }

    private Double getWeight() {
        return getter(weight_val, weight_check, 0.0);
    }

    private Double getTemperature() {
        return getter(temp_val, temp_check, 0.0);
    }

    private Double getBlood1() {
        return getter(blood_val1, blood_check, 0.0);
    }

    private Double getBlood2() {
        return getter(blood_val2, blood_check, 0.0);
    }


    public void cardSave(final View view) {

        String notes = optional_notes.getText().toString().trim();

        ReportDB rp = new ReportDB(view.getContext());
        rp.db.open();
        rp.report.fill(prefs.getSelectedDayMillis(), notes, getHeight(), getWeight(), getTemperature(), getBlood1(), getBlood2());
        rp.save_report();
        rp.db.close();
        Snackbar.make(findViewById(android.R.id.content), "Saved!", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .show();
        resetCard();
        toggleFAB(view);
        updateBanner();
    }

    private void resetCard() {
        height_val.setText("");
        weight_val.setText("");
        temp_val.setText("");
        blood_val1.setText("");
        blood_val2.setText("");
        optional_notes.setText("");
    }

    private void addFiltersToVals() {
        height_val.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(4, 2)});
        weight_val.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(4, 2)});
        temp_val.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(3, 1)});
        blood_val1.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(4, 1)});
        blood_val2.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(4, 1)});


    }

    private void addTextWatchersToVals() {
        height_val.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                limit_range(3, height_val);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isEmpty(height_val)) {
                    height_check.setChecked(false);
                } else {
                    height_check.setChecked(true);
                }
            }
        });
        weight_val.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                limit_range(3, weight_val);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isEmpty(weight_val)) {
                    weight_check.setChecked(false);
                } else {
                    weight_check.setChecked(true);
                }
            }
        });
        temp_val.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                limit_range(2, temp_val);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isEmpty(temp_val)) {
                    temp_check.setChecked(false);
                } else {
                    temp_check.setChecked(true);
                }
            }
        });
        blood_val1.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                limit_range(3, blood_val1);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isEmpty(blood_val1)) {
                    blood_check.setChecked(false);
                } else if (!isEmpty(blood_val2)) {
                    blood_check.setChecked(true);
                }
            }
        });
        blood_val2.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                limit_range(3, blood_val2);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isEmpty(blood_val2)) {
                    blood_check.setChecked(false);
                } else if (!isEmpty(blood_val1)) {
                    blood_check.setChecked(true);
                }
            }
        });
    }

    private void addChangeListenerToCB() {

        height_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isEmpty(height_val)) {
                    height_check.setChecked(false);
                }
                update_save_button();
            }
        });
        weight_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isEmpty(weight_val)) {
                    weight_check.setChecked(false);
                }
                update_save_button();
            }
        });

        temp_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isEmpty(temp_val)) {
                    temp_check.setChecked(false);
                }
                update_save_button();
            }
        });

        blood_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isEmpty(blood_val1) || isEmpty(blood_val2)) {
                    blood_check.setChecked(false);
                }
                update_save_button();
            }
        });
    }


    private void limit_range(int len, TextInputEditText i) {
        String str_val = i.getText().toString();
        int max_val = (int) Math.pow(10, len) - 1;
        if (!str_val.equals("")) {
            double val = Double.parseDouble(str_val);
            if (val > max_val) {
                i.setText(String.valueOf(max_val));
            }

        }
    }

    private boolean isEmpty(TextInputEditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void update_save_button() {
        MaterialButton save = findViewById(R.id.materialSaveButton);
        final MaterialCheckBox height_check = findViewById(R.id.height_check),
                weight_check = findViewById(R.id.weight_check),
                temp_check = findViewById(R.id.temperature_check),
                blood_check = findViewById(R.id.blood_pressure_check);
        int count = 0;
        count = height_check.isChecked() ? count + 1 : count;
        count = weight_check.isChecked() ? count + 1 : count;
        count = temp_check.isChecked() ? count + 1 : count;
        count = blood_check.isChecked() ? count + 1 : count;
        if (count >= 2) {
            save.setEnabled(true);
        } else {
            save.setEnabled(false);
        }

    }

    public void toggleFAB(View view) {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setExpanded(!fab.isExpanded());
        view.getRootView().findViewById(R.id.home_layout).setVisibility(fab.isExpanded() ? View.INVISIBLE : View.VISIBLE);
        if (fab.isExpanded()) {
            update_report_day();
        }
    }

    void update_report_day() {
        report_day.setText(String.valueOf(new CalendarManager().getMillisToDate(prefs.getSelectedDayMillis())));
    }

    public void settings_click(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    public static class DecimalDigitsInputFilter implements InputFilter {
        private Pattern mPattern;

        public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
            mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }

    }

}
