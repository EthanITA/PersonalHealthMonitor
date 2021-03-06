package com.dong.personalhealthmonitor.java_classes.custom_xml_preferences;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceViewHolder;

import com.dong.personalhealthmonitor.R;
import com.dong.personalhealthmonitor.java_classes.shared_preferences.SettingsOperations;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class PreferenceToggleHeight extends EditTextPreference {
    public PreferenceToggleHeight(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        final SettingsOperations prefs = new SettingsOperations(getContext());

        SwitchMaterial s = holder.itemView.findViewById(R.id.height_threshold_sw);
        s.setChecked(prefs.getHeightThresholdSW());

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefs.insertHeightThresholdSW(isChecked);
            }
        });
    }
}
