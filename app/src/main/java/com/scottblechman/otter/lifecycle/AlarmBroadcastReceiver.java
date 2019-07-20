package com.scottblechman.otter.lifecycle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.scottblechman.otter.ui.activity.AlarmActivity;
import com.scottblechman.otter.ui.notification.AlarmNotification;

import org.joda.time.DateTime;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = "Alarm activated.";
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

        AlarmNotification.notify(context, "Title", 0);

        String label = intent.getStringExtra("label");
        long time = intent.getLongExtra("time", new DateTime().getMillis());

        Intent intent2 = new Intent(context, AlarmActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.putExtra("label", label);
        intent2.putExtra("time", time);
        context.startActivity(intent2);
    }
}
