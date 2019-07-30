package com.scottblechman.otter.lifecycle.boot;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.db.AlarmDatabase;
import com.scottblechman.otter.lifecycle.AlarmRepository;
import com.scottblechman.otter.lifecycle.AlarmViewModel;
import com.scottblechman.otter.lifecycle.BroadcastRepository;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Called whenever the device has been restarted. Allows for the application to re-initialize alarms
 * that have been cleared in {@link android.app.AlarmManager}.
 */
public class OnBootReceiver extends BroadcastReceiver {

    private static String TAG = OnBootReceiver.class.toString();

    @Override
    public void onReceive(Context context, Intent intent) {

        android.os.Debug.waitForDebugger();

        Log.d(TAG, "onReceive:  got action " + intent.getAction());

        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(AlarmReinitWorker.class)
                    .build();
            WorkManager.getInstance(context).enqueue(workRequest);
            // GetAlarmsTask mGetAlarmsTask = new GetAlarmsTask(context);
            // mGetAlarmsTask.execute();
            /*AlarmDatabase db = AlarmDatabase.getDatabase(context);

            final Observer<List<Alarm>> alarmsObserver = new Observer<List<Alarm>>() {
                @Override
                public void onChanged(@Nullable final List<Alarm> alarms) {
                    Log.d(TAG, "onReceive: All available alarms: ");
                    if(alarms != null) {
                        for (Alarm alarm : alarms) {
                            Log.d(TAG, "onReceive: alarm " + alarm.getDate().toString());
                        }
                    }
                }
            };*/

            //db.alarmDao().getAllAlarms().observe(this, alarmsObserver);

        }
    }

    /**
     * Checks if an alarm requires being re-initialized, then sets a new pending intent
     * @param alarm database entry with missing pending intent
     */
    private static void reinitialize(Alarm alarm, BroadcastRepository repository, Application application) {
        DateTime now = DateTime.now();
        if(alarm.getEnabled() && alarm.getDate().isAfter(now.toInstant().getMillis())) {
            repository.insert(application, alarm);
        }
    }

    private static class GetAlarmsTask extends AsyncTask<Void, Void, Void> {

        private AlarmRepository alarmRepository;
        private BroadcastRepository broadcastRepository;
        private Application application;

        private List<Alarm> mAlarms;

        AlarmViewModel viewModel;

        AlarmDatabase db;

        GetAlarmsTask(Context context) {
            db = AlarmDatabase.getDatabase(context);

            /*application = (Otter) context.getApplicationContext();
            Log.d(TAG, "onReceive: got application");

            alarmRepository = AlarmRepository.getInstance();
            Log.d(TAG, "onReceive: got repository");

            broadcastRepository = BroadcastRepository.getInstance();
            Log.d(TAG, "onReceive: got broadcast repository");
            this.mAlarms = null;*/
        }

        @Override
        public Void doInBackground(Void... records) {
            //alarmRepository.initializeDataAccess(application);
            Log.d(TAG, "onReceive: initialized data access");

            //mAlarms = alarmRepository.getAllAlarmsSync();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            /*Log.d(TAG, "onReceive: All available alarms: ");
            if(mAlarms != null) {
                for (Alarm alarm : mAlarms) {
                    Log.d(TAG, "onReceive: alarm " + alarm.getDate().toString());
                }
            }

            if (mAlarms != null && mAlarms.size() > 0) {
                Log.d(TAG, "onReceive: got " + mAlarms.size() + " alarms.");
                for (Alarm alarm : mAlarms) {
                    Log.d(TAG, "onReceive: reinitializing alarm " + alarm.getLabel());
                    reinitialize(alarm, broadcastRepository, application);
                    Log.d(TAG, "onReceive: reinitialized alarm " + alarm.getLabel() + " to "
                            + alarm.getDate().toString());
                }
            }*/
        }
    }
}
