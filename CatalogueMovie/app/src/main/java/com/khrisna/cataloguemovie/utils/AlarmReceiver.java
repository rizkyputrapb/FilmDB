package com.khrisna.cataloguemovie.utils;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.khrisna.cataloguemovie.R;
import com.khrisna.cataloguemovie.activity.HomeActivity;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String TYPE_ONE_TIME = "OneTimeAlarm";
    public static final String TYPE_REPEATING = "RepeatingAlarm";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";

    private final int NOTIF_ID_ONETIME = 100;
    private final int NOTIF_ID_REPEATING = 101;

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        String title = context.getResources().getString(R.string.app_name);
        int notifId = type.equalsIgnoreCase(TYPE_ONE_TIME) ? NOTIF_ID_ONETIME : NOTIF_ID_REPEATING;

        showAlarmNotification(context, title, message, notifId);
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, message)
                .setSmallIcon(R.drawable.round_live_tv_black_48)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }

    public void setRepeatingAlarm(Context context, String type, String time, String message) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) calendar.add(Calendar.DATE, 1);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID_REPEATING, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        int requestCode = type.equalsIgnoreCase(TYPE_ONE_TIME) ? NOTIF_ID_ONETIME : NOTIF_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
