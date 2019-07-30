package com.scottblechman.otter.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlarmDao {

    @Insert
    void insert(Alarm alarm);

    @Update
    void update(Alarm alarm);

    @Delete
    void delete(Alarm alarm);

    @Query("SELECT * FROM alarms")
    LiveData<List<Alarm>> getAllAlarms();

    @Query("SELECT * FROM alarms")
    List<Alarm> getAllAlarmsSync();
}
