package com.dong.personalhealthmonitor.fragments;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.dong.personalhealthmonitor.R;
import com.dong.personalhealthmonitor.java_classes.SQLite.ReportDB;
import com.dong.personalhealthmonitor.java_classes.SQLite.ReportsSQLHelper;
import com.dong.personalhealthmonitor.java_classes.generic.CalendarManager;
import com.dong.personalhealthmonitor.java_classes.generic.Report;
import com.dong.personalhealthmonitor.java_classes.generic.entries.AverageOfReports;
import com.dong.personalhealthmonitor.java_classes.shared_preferences.SettingsOperations;

import java.util.ArrayList;
import java.util.List;

public class StatsCardFragment extends Fragment {


    SettingsOperations prefs;
    ReportDB reportDB;
    CalendarManager calendar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setEnterTransition(inflater.inflateTransition(R.transition.slide_right));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_stats, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        reportDB.db.close();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefs = new SettingsOperations(view.getContext());
        calendar = new CalendarManager();
        reportDB = new ReportDB(view.getContext());
        reportDB.db.open();
        AnyChartView anyChartView = view.findViewById(R.id.chart);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        switch (prefs.getCardSelection()) {
            case ReportsSQLHelper.HEIGHT:
                cartesian = HeightDataset(cartesian);
                break;
            case ReportsSQLHelper.WEIGHT:
                cartesian = WeightDataset(cartesian);
                break;
            case ReportsSQLHelper.TEMP:
                cartesian = TempDataset(cartesian);
                break;
            case ReportsSQLHelper.BLOOD1:
                cartesian = BloodDataset(cartesian);
                break;
        }

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
    }

    Cartesian HeightDataset(Cartesian cartesian) {
        cartesian.title("History of your height");

        cartesian.yAxis(0).title("cm");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
        List<DataEntry> seriesData = new ArrayList<>();
        for (Report r :
                merge_same_days(reportDB.getAllHeight())) {
            Log.i("TAG", String.valueOf(r));
            seriesData.add(new CustomDataEntry(calendar.getMillisToDate(r.day), r.height, prefs.getHeightThreshold()));
        }
        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Height");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Threshold");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);
        return cartesian;
    }

    Cartesian WeightDataset(Cartesian cartesian) {
        cartesian.title("History of your weight");

        cartesian.yAxis(0).title("Kg");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
        List<DataEntry> seriesData = new ArrayList<>();
        for (Report r :
                merge_same_days(reportDB.getAllWeight())) {
            seriesData.add(new CustomDataEntry(calendar.getMillisToDate(r.day), r.weight, prefs.getWeightThreshold()));
        }
        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Weight");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Threshold");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);
        return cartesian;
    }

    Cartesian TempDataset(Cartesian cartesian) {
        cartesian.title("History of your body temperature");

        cartesian.yAxis(0).title("°C");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
        List<DataEntry> seriesData = new ArrayList<>();
        for (Report r :
                merge_same_days(reportDB.getAllTemp())) {
            seriesData.add(new CustomDataEntry(calendar.getMillisToDate(r.day), r.temperature, prefs.getTempThreshold()));
        }
        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Temperature");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Threshold");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);
        return cartesian;
    }

    Cartesian BloodDataset(Cartesian cartesian) {
        cartesian.title("History of your blood pressure");

        cartesian.yAxis(0).title("mm of mercury");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
        List<DataEntry> seriesData = new ArrayList<>();
        for (Report r :
                merge_same_days(reportDB.getAllBlood())) {
            seriesData.add(new BloodDataEntry(calendar.getMillisToDate(r.day), r.blood1, r.blood2, prefs.getBlood1Threshold(), prefs.getBlood2Threshold()));
        }
        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");
        Mapping series4Mapping = set.mapAs("{ x: 'x', value: 'value4' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Systolic");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Diastolic");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);
        Line series3 = cartesian.line(series3Mapping);
        series3.name("Treshold Systolic");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);
        Line series4 = cartesian.line(series4Mapping);
        series4.name("Threshold Diastolic");
        series4.hovered().markers().enabled(true);
        series4.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series4.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);
        return cartesian;
    }

    List<Report> merge_same_days(List<Report> reports) {
        // la lista di Report è ordinata per giorno
        List<Report> result = new ArrayList<Report>();
        if (reports.size() == 0) {
            return reports;
        } else {
            long last_day = reports.get(0).day;
            result.add(new AverageOfReports(reportDB.getReportsDay(last_day)).getReport());
            for (Report r :
                    reports) {
                if (r.day != last_day) {
                    last_day = r.day;
                    result.add(new AverageOfReports(reportDB.getReportsDay(last_day)).getReport());
                }
            }
        }
        return result;
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number threshold) {
            super(x, value);
            setValue("value2", threshold);
        }

    }

    private class BloodDataEntry extends ValueDataEntry {

        public BloodDataEntry(String x, Number blood1, Number blood2, Number threshold1, Number threshold2) {
            super(x, blood1);
            setValue("value2", blood2);
            setValue("value3", threshold1);
            setValue("value4", threshold2);
        }
    }
}