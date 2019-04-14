package com.scottblechman.otter.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "alarms")
public class Alarm {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int mUid;

    @NonNull
    @ColumnInfo(name = "date")
    private Date mDate;

    @ColumnInfo(name = "label")
    private String mLabel;

    public Alarm(@NonNull Date date, String label) {
        this.mDate = date;
        this.mLabel = label;
    }

    public Date getDate() {
        return mDate;
    }

    public String getLabel() {
        return mLabel;
    }

}
