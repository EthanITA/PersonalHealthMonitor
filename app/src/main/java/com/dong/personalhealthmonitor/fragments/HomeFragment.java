package com.dong.personalhealthmonitor.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.dong.personalhealthmonitor.R;
import com.dong.personalhealthmonitor.java_classes.SQLite.ReportDB;
import com.dong.personalhealthmonitor.java_classes.SQLite.ReportsSQLHelper;
import com.dong.personalhealthmonitor.java_classes.generic.CalendarManager;
import com.dong.personalhealthmonitor.java_classes.generic.HealthCalculator;
import com.dong.personalhealthmonitor.java_classes.generic.Report;
import com.dong.personalhealthmonitor.java_classes.generic.Stats;
import com.dong.personalhealthmonitor.java_classes.generic.entries.AverageOfReports;
import com.dong.personalhealthmonitor.java_classes.shared_preferences.SettingsOperations;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class HomeFragment extends Fragment {
    // banner
    MaterialTextView banner_selected_day;
    ;

    // card of the day
    MaterialCardView card_of_the_day;
    MaterialTextView day_entries, day_height, day_weight, day_temp, day_blood;
    MaterialTextView day_height_size, day_weight_size, day_temp_size, day_blood_size;
    // stats of the day
    MaterialCardView card_bmi, card_bmr, card_fat;
    MaterialTextView bmi_feedback, bmr_feedback, fat_feedback;

    // cards of stats
    MaterialCardView card_height_stats, card_weight_stats, card_temp_stats, card_blood_stats;
    MaterialTextView height_values, height_dates,
            weight_values, weight_dates,
            temp_values, temp_dates,
            bloods_values, bloods_dates;

    SettingsOperations prefs;
    CalendarManager my_calendar;
    ReportDB reportDB;
    HealthCalculator calc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade));

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        reportDB = new ReportDB(getContext());
        reportDB.db.open();

        prefs = new SettingsOperations(getContext());
        my_calendar = new CalendarManager();

        // banner
        banner_selected_day = view.getRootView().findViewById(R.id.selected_day);
        // cards
        card_of_the_day = view.findViewById(R.id.day_card);
        card_height_stats = view.findViewById(R.id.height_home_card);
        card_weight_stats = view.findViewById(R.id.weight_home_card);
        card_temp_stats = view.findViewById(R.id.temp_home_card);
        card_blood_stats = view.findViewById(R.id.blood_home_card);
        card_bmi = view.findViewById(R.id.bmi_card);
        card_bmr = view.findViewById(R.id.bmr_card);
        card_fat = view.findViewById(R.id.fat_card);

        // textviews
        day_entries = view.findViewById(R.id.day_entries);
        day_height = view.findViewById(R.id.day_height);
        day_weight = view.findViewById(R.id.day_weight);
        day_temp = view.findViewById(R.id.day_temp);
        day_blood = view.findViewById(R.id.day_blood);
        day_height_size = view.findViewById(R.id.day_height_entries);
        day_weight_size = view.findViewById(R.id.day_weight_entries);
        day_temp_size = view.findViewById(R.id.day_temp_entries);
        day_blood_size = view.findViewById(R.id.day_blood_entries);

        height_values = view.findViewById(R.id.height_stats_values);
        weight_values = view.findViewById(R.id.weight_stats_values);
        temp_values = view.findViewById(R.id.temp_stats_values);
        bloods_values = view.findViewById(R.id.blood_stats_values);

        height_dates = view.findViewById(R.id.height_stats_date);
        weight_dates = view.findViewById(R.id.weight_stats_date);
        temp_dates = view.findViewById(R.id.temp_stats_date);
        bloods_dates = view.findViewById(R.id.blood_stats_date);

        bmi_feedback = view.findViewById(R.id.BMI_feedback);
        bmr_feedback = view.findViewById(R.id.BMR_feedback);
        fat_feedback = view.findViewById(R.id.FAT_feedback);

        banner_selected_day.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateHomeCards();
            }
        });

        updateHomeCards();
        initCardsClickListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().findViewById(R.id.fab).setVisibility(View.GONE);
        getActivity().findViewById(R.id.banner).setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.banner).setVisibility(View.VISIBLE);

    }


    public void initCardsClickListener() {
        card_of_the_day.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.HomeToDay);
            }
        });
        card_height_stats.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                prefs.insertCardSelection(ReportsSQLHelper.HEIGHT);
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.HomeToStats);
            }
        });
        card_weight_stats.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                prefs.insertCardSelection(ReportsSQLHelper.WEIGHT);
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.HomeToStats);
            }
        });
        card_temp_stats.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                prefs.insertCardSelection(ReportsSQLHelper.TEMP);
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.HomeToStats);
            }
        });
        card_blood_stats.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                prefs.insertCardSelection(ReportsSQLHelper.BLOOD1);
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.HomeToStats);
            }
        });
        card_bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("BMI: " + calc.getBMI())
                        .setMessage(calc.getBMIFeedback())

                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
        card_bmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("BMR: " + calc.getBMR())
                        .setMessage(calc.getBMRDescription())
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
        card_fat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle("Body Fat: " + calc.getFat() )
                        .setMessage(calc.getFatFeedback())
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
    }

    public void updateHomeCards() {
        updateDayCard();
        updateDayStatsCards();
        updateStatsCards();
    }

    public void updateDayStatsCards() {
        calc = new HealthCalculator(prefs.getAverageHeight(), prefs.getAverageWeight(), prefs.getAge(), prefs.getGender());
        bmi_feedback.setText(calc.getBMIFeedback());
        bmr_feedback.setText(calc.getBMR());
        fat_feedback.setText(calc.getFatFeedback());
    }

    public void updateDayCard() {
        List<Report> reports = reportDB.getReportDay(prefs.getSelectedDayMillis());
        String totalEntries = reports.size() + " entries";

        AverageOfReports averageOfReports = new AverageOfReports(reports);

        day_entries.setText(totalEntries);
        day_height_size.setText("" + averageOfReports.getHeightSize());
        day_weight_size.setText("" + averageOfReports.getWeightSize());
        day_temp_size.setText("" + averageOfReports.getTemperatureSize());
        day_blood_size.setText("" + averageOfReports.getBloodSize());

        day_height.setText(averageOfReports.getAverageHeight() + " cm");
        day_weight.setText(averageOfReports.getAverageWeight() + " Kg");
        day_temp.setText(averageOfReports.getAverageTemperature() + " °C");
        day_blood.setText(averageOfReports.getAverageBlood1() + "/" + averageOfReports.getAverageBlood2() + " mmHg");
        prefs.insertAverageHeight(averageOfReports.getAverageHeight());
        prefs.insertAverageWeight(averageOfReports.getAverageWeight());
    }

    public void updateStatsCards() {
        Stats height_stats = new Stats(reportDB.getAllHeight());
        Stats weight_stats = new Stats(reportDB.getAllWeight());
        Stats temp_stats = new Stats(reportDB.getAllTemp());
        Stats blood_stats = new Stats(reportDB.getAllBlood());
        if (!height_stats.getDates().equals("")) {
            card_height_stats.setVisibility(View.VISIBLE);
            height_dates.setText(height_stats.getDates());
            height_values.setText(height_stats.getHeight());
        } else {
            card_height_stats.setVisibility(View.GONE);
        }
        if (!weight_stats.getDates().equals("")) {
            card_weight_stats.setVisibility(View.VISIBLE);
            weight_dates.setText(weight_stats.getDates());
            weight_values.setText(weight_stats.getWeight());
        } else {
            card_weight_stats.setVisibility(View.GONE);
        }
        if (!temp_stats.getDates().equals("")) {
            card_temp_stats.setVisibility(View.VISIBLE);
            temp_dates.setText(temp_stats.getDates());
            temp_values.setText(temp_stats.getTemp());
        } else {
            card_temp_stats.setVisibility(View.GONE);
        }
        if (!blood_stats.getDates().equals("")) {
            card_blood_stats.setVisibility(View.VISIBLE);
            bloods_dates.setText(blood_stats.getDates());
            bloods_values.setText(blood_stats.getBlood());
        } else {
            card_blood_stats.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        reportDB.db.close();
    }
}