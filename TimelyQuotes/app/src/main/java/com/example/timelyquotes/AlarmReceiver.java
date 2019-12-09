package com.example.timelyquotes;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){

        final String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        // quote[0] is the title, quote[1] is the actual quote.
        String[] quote = QuoteGenerator.generate();
        if (intent.getAction().equalsIgnoreCase(
                "com.example.timelyquotes.SEND.QUOTE")){

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
                    NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(quote[1])
                    .setContentTitle(quote[0])
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(quote[1])
                            .setBigContentTitle(quote[0]));

            NotificationManager notificationManager = (NotificationManager)context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(MainActivity.incrementNotify(), builder.build());
            QuoteSender.send(context);
        } else if (intent.getAction().equalsIgnoreCase(
                "com.example.timelyquotes.REVIVE.QUOTE")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(new Intent(context, QuoteSender.class));
            } else {
                context.startService(new Intent(context, QuoteSender.class));
            }
        }
    }
}
