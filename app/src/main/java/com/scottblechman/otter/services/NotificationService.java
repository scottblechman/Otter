package com.scottblechman.otter.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.scottblechman.otter.R;
import com.scottblechman.otter.ui.activity.AlarmActivity;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class NotificationService extends Service {

    private NotificationManager notificationManager;

    private final IBinder mBinder = new LocalBinder();

    private Ringtone ringtone;

    private Intent intent;

    public NotificationService() {
    }

    public class LocalBinder extends Binder {
        @SuppressWarnings("unused")
        NotificationService getService() {
            return NotificationService.this;
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.intent = intent;

        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        createNotification();

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(this, uri);
        ringtone.play();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        notificationManager.cancel(R.string.notificationId);
        ringtone.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private void createNotification() {

        // Open alarm activity when the notification is touched
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                intent.getIntExtra("uid", 0),
                createActivityIntent(this, intent), PendingIntent.FLAG_UPDATE_CURRENT);


        // Add actions for snooze and dismiss
        Intent snoozeIntent = new Intent(this, ActionReceiver.class);
        snoozeIntent.putExtra("action","snooze");

        Intent dismissIntent = new Intent(this, ActionReceiver.class);
        dismissIntent.putExtra("action","dismiss");

        final String label = intent.getStringExtra("label");
        DateTimeFormatter fmt = DateTimeFormat.forPattern(getString(R.string.dateTimeFormat));
        DateTime now = DateTime.now();
        final String time = now.toString(fmt);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this,
                NotificationCompat.CATEGORY_ALARM)
                .setChannelId(NotificationCompat.CATEGORY_ALARM)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE | NotificationCompat.DEFAULT_LIGHTS)

                .setSmallIcon(R.drawable.ic_launcher_foreground)  // TODO: replace with icon
                .setTicker(label)
                .setContentTitle(label)
                .setContentText(time)

                .setShowWhen(false)
                .setAutoCancel(false)
                .setOngoing(true)

                .setContentIntent(contentIntent)
                .addAction(R.drawable.ic_stat_snooze, "Snooze", PendingIntent.getBroadcast(
                        this, 0, snoozeIntent, PendingIntent.FLAG_CANCEL_CURRENT))
                .addAction(R.drawable.ic_stat_dismiss, "Dismiss", PendingIntent.getBroadcast(
                        this, 1, dismissIntent, PendingIntent.FLAG_CANCEL_CURRENT));

        notificationManager.notify(R.string.notificationId, notification.build());
    }

    private Intent createActivityIntent(Context context, Intent intent) {
        String label = intent.getStringExtra("label");
        long time = intent.getLongExtra("time", new DateTime().getMillis());

        Intent activityIntent = new Intent(context, AlarmActivity.class);
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activityIntent.putExtra("label", label);
        activityIntent.putExtra("time", time);

        return activityIntent;
    }
}
