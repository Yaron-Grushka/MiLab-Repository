package com.example.timelyquotes;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;

public class QuoteSender extends IntentService {

    // Actions:
    private static final String SEND_QUOTE = "com.example.timelyquotes.SEND.QUOTE";
    private static final String CANCEL_QUOTE = "com.example.timelyquotes.CANCEL.QUOTE";

    public QuoteSender() {
        super("QuoteSender");
    }

    /**
     * Method for activating the messages.
     * @param context The context of the method (switch turned on in MainActivity)
     */
    public static void send(Context context) {
        Intent intent = new Intent(context, QuoteSender.class);
        intent.setAction(SEND_QUOTE);
        context.startService(intent);
    }

    /**
     * Method for cancelling the messages.
     * @param context The context of the method (switch turned off in MainActivity)
     */
    public static void cancel(Context context){
        Intent intent = new Intent(context, QuoteSender.class);
        intent.setAction(CANCEL_QUOTE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (SEND_QUOTE.equals(action)) {
                handleActionSend();
            } else if (CANCEL_QUOTE.equals(action)){
                handleActionCancel();
            } else {
                throw new RuntimeException("Unknown action provided");
            }
        }
    }

    private void handleActionSend() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.setAction("com.example.timelyquotes.SEND.QUOTE");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime(),300000,
                    pendingIntent);
        }
    }

    private void handleActionCancel(){
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.setAction("com.example.timelyquotes.SEND.QUOTE");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

}
