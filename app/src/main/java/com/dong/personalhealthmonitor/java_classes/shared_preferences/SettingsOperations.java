package com.dong.personalhealthmonitor.java_classes.shared_preferences;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.dong.personalhealthmonitor.java_classes.generic.CalendarManager;

public class SettingsOperations {

    Context context;
    SharedPreferences prefs;
    GenericOperations generic_pref;

    public SettingsOperations(Context context) {
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        generic_pref = new GenericOperations(this.context);
    }

    private String readString(String key, String defaultValue) {
        return this.prefs.getString(key, defaultValue);
    }

    private boolean readBoolean(String key, Boolean defaultValue) {
        return this.prefs.getBoolean(key, defaultValue);
    }

    public void insertCardSelection(String selection) {
        this.generic_pref.saveString("card_selection", selection);
    }

    public String getCardSelection() {
        return this.generic_pref.readString("card_selection", "");
    }

    public int getAge() {
        return Integer.parseInt(readString("age", "18"));
    }

    public String getName() {
        return readString("name", "");
    }

    public boolean getGender() {
        return readBoolean("gender", false);
    }

    public int getHour() {
        return generic_pref.readInt("notification_hour", 17);
    }

    public int getMinutes() {
        return generic_pref.readInt("notification_minute", 0);
    }

    public void insertSelectedDayMillis(Long day) {
        generic_pref.saveLong("selected_day", day);

    }

    public long getSelectedDayMillis() {
        return generic_pref.readLong("selected_day", new CalendarManager().getTodayMillis());
    }

    public String getTime() {
        String hour = getHour() < 10 ? "0" + getHour() : String.valueOf(getHour());
        String minutes = getMinutes() <= 10 ? "0" + getMinutes() : String.valueOf(getMinutes());
        return hour + ":" + minutes;
    }

    public void insertBlood1Threshold(double threshold) {
        generic_pref.saveDouble("blood1_thresh", threshold);
    }

    public void insertBlood2Threshold(double threshold) {
        generic_pref.saveDouble("blood2_thresh", threshold);
    }

    public void insertHeightThreshold(double threshold) {
        generic_pref.saveDouble("height_thresh", threshold);
    }

    public void insertWeightThreshold(double threshold) {
        generic_pref.saveDouble("weight_thresh", threshold);
    }

    public void insertTempThreshold(double threshold) {
        generic_pref.saveDouble("temp_thresh", threshold);
    }

    public double getBlood1Threshold() {

        return getBlood1ThresholdSW() ? generic_pref.readDouble("blood1_thresh", 130.0) : 0.0;
    }

    public double getBlood2Threshold() {
        return getBlood2ThresholdSW() ? generic_pref.readDouble("blood2_thresh", 85.0) : 0.0;
    }

    public double getHeightThreshold() {
        return getHeightThresholdSW() ? generic_pref.readDouble("height_thresh", 0.0) : 0.0;
    }

    public double getWeightThreshold() {
        return getWeightThresholdSW() ? generic_pref.readDouble("weight_thresh", 0.0) : 0.0;
    }

    public double getTempThreshold() {
        return getTempThresholdSW() ? generic_pref.readDouble("temp_thresh", 37.5) : 0.0;
    }

    public void insertBlood1ThresholdSW(boolean sw) {
        generic_pref.saveBool("blood1_thresh_sw", sw);
    }

    public void insertBlood2ThresholdSW(boolean sw) {
        generic_pref.saveBool("blood2_thresh_sw", sw);
    }

    public void insertHeightThresholdSW(boolean sw) {
        generic_pref.saveBool("height_thresh_sw", sw);
    }

    public void insertWeightThresholdSW(boolean sw) {
        generic_pref.saveBool("weight_thresh_sw", sw);
    }

    public void insertTempThresholdSW(boolean sw) {
        generic_pref.saveBool("temp_thresh_sw", sw);
    }

    public boolean getBlood1ThresholdSW() {
        return generic_pref.readBool("blood1_thresh_sw", false);
    }

    public boolean getBlood2ThresholdSW() {
        return generic_pref.readBool("blood2_thresh_sw", false);
    }

    public boolean getHeightThresholdSW() {
        return generic_pref.readBool("height_thresh_sw", false);
    }

    public boolean getWeightThresholdSW() {
        return generic_pref.readBool("weight_thresh_sw", false);
    }

    public boolean getTempThresholdSW() {
        return generic_pref.readBool("temp_thresh_sw", false);
    }

    public void insertAverageHeight(double height) {
        generic_pref.saveDouble("avg_height", height);
    }

    public void insertAverageWeight(double weight) {
        generic_pref.saveDouble("avg_weight", weight);

    }

    public double getAverageWeight() {
        return generic_pref.readDouble("avg_weight", 0.0);
    }

    public double getAverageHeight() {
        return generic_pref.readDouble("avg_height", 0.0);
    }
}
