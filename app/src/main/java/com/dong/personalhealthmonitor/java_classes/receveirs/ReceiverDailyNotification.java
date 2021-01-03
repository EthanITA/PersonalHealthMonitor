package com.dong.personalhealthmonitor.java_classes.receveirs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dong.personalhealthmonitor.java_classes.notifications.Notification;
import com.dong.personalhealthmonitor.java_classes.shared_preferences.SettingsOperations;

public class ReceiverDailyNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Notification notification = new Notification(context);
        SettingsOperations prefs = new SettingsOperations(context);
        notification.create("Your daily reminder", "Hi " + prefs.getName() + ", remember to fill today's report!");
        notification.send(1);
    }
}
