package com.scottblechman.otter.lifecycle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.ui.activity.AlarmActivity;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = "Alarm activated.";
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

        String label = intent.getStringExtra("label");
        Intent intent2 = new Intent(context, AlarmActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.putExtra("label", label);
        context.startActivity(intent2);
    }
}
