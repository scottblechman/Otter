package com.scottblechman.otter.lifecycle;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.AsyncTask;

import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.db.AlarmDao;
import com.scottblechman.otter.db.AlarmDatabase;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public class AlarmRepository {

    private AlarmDao mAlarmDao;

    // These are kept around to have observer cleared
    private LiveData<List<Alarm>> mAlarmLiveData;
    private Observer mObserver;

    private MutableLiveData<List<Alarm>> mAllAlarms;

    private static volatile AlarmRepository instance;


    private AlarmRepository() {
        if (instance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static AlarmRepository getInstance() {
        if (instance == null) {
            synchronized (AlarmRepository.class) {
                if (instance == null) instance = new AlarmRepository();
            }
        }

        return instance;
    }

    @SuppressWarnings("unused")
    protected AlarmRepository readResolve() {
        return getInstance();
    }

    public void initializeDataAccess(Application application) {
        if(mAlarmDao == null) {
            AlarmDatabase db = AlarmDatabase.getDatabase(application);
            mAlarmDao = db.alarmDao();
            mAllAlarms = new MutableLiveData<>();
            mAlarmLiveData = mAlarmDao.getAllAlarms();
            mObserver = new Observer<List<Alarm>>() {
                @Override
                public void onChanged(List<Alarm> alarms) {
                    mAllAlarms.setValue(alarms);
                }
            };
            mAlarmLiveData.observeForever(mObserver);
        }
    }

    MutableLiveData<List<Alarm>> getAllAlarms() {
        return mAllAlarms;
    }

    public void insert(Alarm alarm) {
        new insertAsyncTask(mAlarmDao).execute(alarm);
    }

    public void delete(Alarm alarm) {
        new deleteAsyncTask(mAlarmDao).execute(alarm);
    }

    public void update(Alarm alarm) {
        new updateAsyncTask(mAlarmDao).execute(alarm);
    }

    public void refresh() {
        mAllAlarms.postValue(mAllAlarms.getValue());
    }

    public void clear() {
        mAlarmLiveData.removeObserver(mObserver);
    }

    private static class insertAsyncTask extends AsyncTask<Alarm, Void, Void> {

        private AlarmDao mAsyncTaskDao;

        insertAsyncTask(AlarmDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Alarm... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Alarm, Void, Void> {

        private AlarmDao mAsyncTaskDao;

        deleteAsyncTask(AlarmDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Alarm... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Alarm, Void, Void> {

        private AlarmDao mAsyncTaskDao;

        updateAsyncTask(AlarmDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Alarm... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }


}
