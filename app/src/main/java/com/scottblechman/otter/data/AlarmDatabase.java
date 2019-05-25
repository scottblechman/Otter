package com.scottblechman.otter.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {Alarm.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverters.class})
public abstract class AlarmDatabase extends RoomDatabase {

    public abstract AlarmDao alarmDao();

    private static volatile AlarmDatabase INSTANCE;

    public static AlarmDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AlarmDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AlarmDatabase.class, "alarm_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
