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
        Toast.makeText(this, R.string.notification_service_stopped, Toast.LENGTH_SHORT).show();
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


        final String label = intent.getStringExtra("label");
        DateTimeFormatter fmt = DateTimeFormat.forPattern("EEEE, MMMM d y, h:mm a");
        DateTime now = DateTime.now();
        final String time = now.toString(fmt);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_foreground)  // TODO: replace with icon
                .setTicker(label)
                .setShowWhen(false)
                .setChannelId(NotificationCompat.CATEGORY_ALARM)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(false)
                .setOngoing(true)
                //.setContentTitle(getText(R.string.notificationId))
                .setContentTitle(label)
                .setContentText(time)
                .setContentIntent(contentIntent);
                //.build();

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
