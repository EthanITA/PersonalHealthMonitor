package com.dong.personalhealthmonitor.java_classes.receveirs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dong.personalhealthmonitor.java_classes.notifications.Notification;
import com.dong.personalhealthmonitor.java_classes.shared_preferences.SettingsOperations;

public class ReceiverPostponedNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("TAG", "onReceive: ");
        Notification notification = new Notification(context);
        SettingsOperations prefs = new SettingsOperations(context);
        notification.create("Your postponed reminder", "Hi again " + prefs.getName() + ", have you already filled today's report?");
        notification.send(2);
    }
}
