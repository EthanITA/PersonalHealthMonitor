package com.dong.personalhealthmonitor.java_classes.receveirs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dong.personalhealthmonitor.java_classes.SQLite.ReportDB;
import com.dong.personalhealthmonitor.java_classes.generic.Report;
import com.dong.personalhealthmonitor.java_classes.notifications.Notification;
import com.dong.personalhealthmonitor.java_classes.shared_preferences.SettingsOperations;

import java.util.List;

public class ReceiverPostponedNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ReportDB reportDB = new ReportDB(context);
        SettingsOperations prefs = new SettingsOperations(context);
        reportDB.db.open();
        List<Report> reports = reportDB.getReportDay(prefs.getSelectedDayMillis());

        if (reports.size() == 0) {
            Notification notification = new Notification(context);
            notification.create("Your postponed reminder", "Hi again " + prefs.getName() + ", have you already filled today's report?");
            notification.send(2);
        }
        reportDB.db.close();
    }
}
