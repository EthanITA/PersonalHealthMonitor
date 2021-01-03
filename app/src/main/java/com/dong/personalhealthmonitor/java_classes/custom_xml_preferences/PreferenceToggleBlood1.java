package com.dong.personalhealthmonitor.java_classes.custom_xml_preferences;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceViewHolder;

import com.dong.personalhealthmonitor.R;
import com.dong.personalhealthmonitor.java_classes.shared_preferences.SettingsOperations;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class PreferenceToggleBlood1 extends EditTextPreference {
    public PreferenceToggleBlood1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        final SettingsOperations prefs = new SettingsOperations(getContext());

        SwitchMaterial s = holder.itemView.findViewById(R.id.blood1_threshold_sw);

        s.setChecked(prefs.getBlood1ThresholdSW());

        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefs.insertBlood1ThresholdSW(isChecked);
            }
        });
    }
}
