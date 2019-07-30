package com.scottblechman.otter.db;

import androidx.room.TypeConverter;

import org.joda.time.DateTime;

@SuppressWarnings("WeakerAccess")
class DateConverters {
    @TypeConverter
    public static DateTime fromTimestamp(Long value) {
        return value == null ? null : new DateTime(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(DateTime date) {
        return date == null ? null : date.getMillis();
    }
}
