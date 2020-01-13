package com.example.timelyquotes;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    final String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

    @Override
    public void onReceive(Context context, Intent intent){

        // quote[0] is the title, quote[1] is the actual quote.
        String[] quote = QuoteGenerator.generate();
        if (intent.getAction().equalsIgnoreCase(
                "com.example.timelyquotes.SEND.QUOTE")){
            makeChannel(context);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
                    NOTIFICATION_CHANNEL_ID)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(quote[1])
                    .setContentTitle(quote[0])
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(quote[1])
                            .setBigContentTitle(quote[0]));

            NotificationManager notificationManager = (NotificationManager)context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.notify(MainActivity.incrementNotify(), builder.build());
            }
        }
    }

    protected void makeChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager)context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel
                        (new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                        NOTIFICATION_CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT));
            }
        }
    }
}
