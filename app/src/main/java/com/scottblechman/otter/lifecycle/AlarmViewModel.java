package com.scottblechman.otter.lifecycle;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.scottblechman.otter.db.Alarm;

import org.joda.time.DateTime;

import java.util.List;

@SuppressWarnings("unused")
public class AlarmViewModel extends AndroidViewModel {

    private AlarmRepository mRepository;
    private BroadcastRepository mBroadcastRepository;

    private LiveData<List<Alarm>>  mAllAlarms;

    public AlarmViewModel(@NonNull Application application) {
        super(application);
        mRepository = new AlarmRepository(application);
        mBroadcastRepository = BroadcastRepository.getInstance();
        mAllAlarms = mRepository.getAllAlarms();
    }

    public LiveData<List<Alarm>> getAllWords() { return mAllAlarms; }

    public void insert(Alarm alarm) {
        mRepository.insert(alarm);
        mBroadcastRepository.insert(getApplication(), alarm);
    }

    /**
     * Modifies the database entry and broadcast receiver (if any) for an alarm.
     * @param oldAlarm current version of alarm
     * @param newAlarm copy of old alarm with values modified
     * @param updateDb false if we want to create a new broadcast receiver (e.g. for snoozing)
     */
    public void update(Alarm oldAlarm, Alarm newAlarm, boolean updateDb) {
        if(updateDb) {
            mRepository.update(newAlarm);
        }
        mBroadcastRepository.update(getApplication(), oldAlarm, newAlarm);
    }

    public void delete(Alarm alarm) {
        mRepository.delete(alarm);
        mBroadcastRepository.delete(getApplication(), alarm);
    }

    /**
     * Checks to see if an alarm has a valid date-time; i.e., that the alarm has not been set for a
     * time in the past.
     * @param alarm has date and time set
     * @param now current time when alarm is being set
     * @return whether the alarm has been set in the past
     */
    public boolean isAlarmValid(Alarm alarm, DateTime now) {
        return alarm.getDate().isAfter(now.toInstant().getMillis());
    }
}
