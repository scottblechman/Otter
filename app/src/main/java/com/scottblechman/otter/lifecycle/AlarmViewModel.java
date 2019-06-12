package com.scottblechman.otter.lifecycle;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.scottblechman.otter.db.Alarm;

import java.util.List;

@SuppressWarnings("unused")
public class AlarmViewModel extends AndroidViewModel {

    private AlarmRepository mRepository;
    private BroadcastRepository mBroadcastRepository;

    private LiveData<List<Alarm>>  mAllAlarms;

    public AlarmViewModel(@NonNull Application application) {
        super(application);
        mRepository = new AlarmRepository(application);
        mBroadcastRepository = new BroadcastRepository(application);
        mAllAlarms = mRepository.getAllAlarms();
    }

    public LiveData<List<Alarm>> getAllWords() { return mAllAlarms; }

    public void insert(Alarm alarm) {
        mRepository.insert(alarm);
        mBroadcastRepository.insert(alarm);
    }

    public void update(Alarm alarm) { mRepository.update(alarm); }

    public void delete(Alarm alarm) {
        mRepository.delete(alarm);
        mBroadcastRepository.delete(alarm);
    }
}
