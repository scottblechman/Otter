package com.scottblechman.otter.controller;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.scottblechman.otter.data.Alarm;
import com.scottblechman.otter.data.AlarmDao;
import com.scottblechman.otter.data.AlarmDatabase;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public class AlarmRepository {

    private AlarmDao mAlarmDao;
    private LiveData<List<Alarm>> mAllAlarms;

    AlarmRepository(Application application) {
        AlarmDatabase db = AlarmDatabase.getDatabase(application);
        mAlarmDao = db.alarmDao();
        mAllAlarms = mAlarmDao.getAllAlarms();
    }

    LiveData<List<Alarm>> getAllAlarms() {
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
