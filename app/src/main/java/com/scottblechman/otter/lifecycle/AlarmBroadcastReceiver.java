package com.scottblechman.otter.lifecycle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.scottblechman.otter.services.NotificationService;
import org.joda.time.DateTime;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = "Alarm activated.";
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

        String label = intent.getStringExtra("label");
        long time = intent.getLongExtra("time", new DateTime().getMillis());

        // AlarmNotification.notify(context, intent);
        Intent serviceIntent = new Intent(context, NotificationService.class);
        serviceIntent.putExtra("label", label);
        serviceIntent.putExtra("time", time);
        context.startService(serviceIntent);
    }
}
