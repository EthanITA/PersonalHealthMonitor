package com.dong.personalhealthmonitor.java_classes.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dong.personalhealthmonitor.activities.MainActivity;
import com.dong.personalhealthmonitor.R;
import com.dong.personalhealthmonitor.activities.SnoozeActivity;

public class Notification {
    public String channel_name = "Default";
    public String channel_description = "default channel";
    public String channel_id = "Reminder";
    public int channel_importance = NotificationManager.IMPORTANCE_HIGH;
    NotificationCompat.Builder mBuilder;
    NotificationManagerCompat notificationManagerCompat;
    private Context context;

    public Notification(Context context) {
        this.context = context;
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(this.channel_id, this.channel_name, this.channel_importance);
            channel.setDescription(this.channel_description);

            NotificationManager notificationManager = this.context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void create(String title, String msg) {
        Intent notificationIntent;

        notificationIntent = new Intent(this.context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.context, 0, notificationIntent, 0);
        PendingIntent snoozeIntent = PendingIntent.getActivity(this.context, 0, new Intent(this.context, SnoozeActivity.class), 0);

        //create notification
        notificationManagerCompat = NotificationManagerCompat.from(this.context);
        mBuilder = new NotificationCompat.Builder(this.context, this.channel_id);
        mBuilder.setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.DEFAULT_SOUND)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.other_day, "Snooze", snoozeIntent)
                .setAutoCancel(true);


        //send notification

    }

    public void send(int id) {
        notificationManagerCompat.notify(id, mBuilder.build());
    }

    public void sendNotification(String title, String msg, int id) {
        create(title, msg);
        send(id);
    }
}
