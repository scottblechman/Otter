package com.scottblechman.otter.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.scottblechman.otter.R;
import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.lifecycle.AlarmViewModel;

import java.util.Date;

public class AlarmActivity extends AppCompatActivity {

    private AlarmViewModel mAlarmViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        mAlarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel.class);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        final Ringtone ringtone = RingtoneManager.getRingtone(this, uri);
        ringtone.play();

        TextView textView = findViewById(R.id.labelText);
        textView.setText(getIntent().getStringExtra("label"));

        TextView timeTv = findViewById(R.id.timeText);
        final Date date = new Date(getIntent().getLongExtra("time", new Date().getTime()));
        timeTv.setText(date.toString());

        Button mSnoozeButton = findViewById(R.id.buttonSnooze);
        mSnoozeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Alarm oldAlarm = new Alarm(date, getIntent().getStringExtra("label"));
                Alarm newAlarm = createSnoozedAlarm(oldAlarm);
                mAlarmViewModel.update(oldAlarm, newAlarm,false);
                ringtone.stop();
                finish();
            }
        });

        Button mCancelButton = findViewById(R.id.buttonCancel);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ringtone.stop();
                finish();
            }
        });
    }

    /**
     * Creates a copy of an ongoing alarm with a new activation time based on user preferences.
     * @param originalAlarm currently activated alarm
     * @return alarm with a new activation time
     */
    private Alarm createSnoozedAlarm(Alarm originalAlarm) {
        long originalTime = originalAlarm.getDate().getTime();
        long newTime = originalTime + (getResources().getInteger(R.integer.snooze_minutes) * 60000);
        return new Alarm(new Date(newTime), originalAlarm.getLabel());
    }
}
