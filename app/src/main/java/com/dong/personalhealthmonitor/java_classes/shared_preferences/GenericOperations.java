package com.dong.personalhealthmonitor.java_classes.shared_preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.dong.personalhealthmonitor.R;

import static android.content.Context.MODE_PRIVATE;

public class GenericOperations {
    Context context;
    String file_name;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public GenericOperations(Context context, String file_name) {
        this.context = context;
        this.file_name = file_name;
        initPrefs();
    }

    public GenericOperations(Context context) {
        this.context = context;
        file_name = context.getResources().getString(R.string.preferences_file);
        initPrefs();

    }

    @SuppressLint("CommitPrefEdits")
    private void initPrefs() {
        this.prefs = context.getSharedPreferences(this.file_name, MODE_PRIVATE);
        this.editor = this.prefs.edit();
    }

    public void saveInt(String key, int value) {
        this.editor.putInt(key, value);
        save();
    }

    public void saveLong(String key, long value) {
        this.editor.putLong(key, value);
        save();
    }

    public void saveString(String key, String value) {
        this.editor.putString(key, value);
        save();
    }

    public void saveDouble(String key, double value) {
        saveString(key, String.valueOf(value));
    }

    public double readDouble(String key, double defaultValue) {
        return Double.parseDouble(readString(key, String.valueOf(defaultValue)));
    }

    public int readInt(String key, int defaultValue) {
        return this.prefs.getInt(key, defaultValue);
    }

    public Long readLong(String key, Long defaultValue) {
        return this.prefs.getLong(key, defaultValue);

    }

    public void saveBool(String key, boolean value) {
        this.editor.putBoolean(key, value);
        save();
    }

    public boolean readBool(String key, boolean defaultValue) {
        return this.prefs.getBoolean(key, defaultValue);
    }

    public String readString(String key, String defaultValue) {
        return this.prefs.getString(key, defaultValue);
    }

    private void save() {
        this.editor.commit();
    }
}
