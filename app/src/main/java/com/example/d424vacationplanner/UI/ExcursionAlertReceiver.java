package com.example.d424vacationplanner.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.d424vacationplanner.R;

public class ExcursionAlertReceiver extends BroadcastReceiver {
    private static final String channel_id = "test";
    private static int notificationID;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        String message = intent.getStringExtra("key");
        if (message == null) {
            message = "Excursion Alert";
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channel_id);
        Notification notification = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(message)
                .setContentTitle("Excursion Alert").build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, notification);
    }
    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        CharSequence name = "Excursion Alerts";
        String description = "Alert for excursion date";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel  = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}