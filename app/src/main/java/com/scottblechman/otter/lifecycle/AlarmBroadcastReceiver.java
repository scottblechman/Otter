package com.scottblechman.otter.lifecycle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.scottblechman.otter.services.NotificationService;
import org.joda.time.DateTime;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("uid", -1);
        String label = intent.getStringExtra("label");
        long time = intent.getLongExtra("time", new DateTime().getMillis());
        boolean enabled = intent.getBooleanExtra("enabled", false);

        Intent serviceIntent = new Intent(context, NotificationService.class);
        serviceIntent.putExtra("uid", id);
        serviceIntent.putExtra("label", label);
        serviceIntent.putExtra("time", time);
        serviceIntent.putExtra("enabled", enabled);

        context.startService(serviceIntent);
    }
}
