package com.scottblechman.otter.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "alarms")
public class Alarm implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mUid;

    @NonNull
    @ColumnInfo(name = "date")
    private Date mDate;

    @ColumnInfo(name = "label")
    private String mLabel;

    // Default constructor, used when new Alarm object created
    @Ignore
    public Alarm() {
        mDate = new Date();
    }

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

    public int getUid() { return mUid; }

    public void setUid(int id) { mUid = id; }

    // All methods below allow for conversion to a Parcelable object
    protected Alarm(Parcel in) {
        mUid = in.readInt();
        long tmpMDate = in.readLong();
        mDate = Objects.requireNonNull(tmpMDate != -1 ? new Date(tmpMDate) : null);
        mLabel = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mUid);
        dest.writeLong(mDate.getTime());
        dest.writeString(mLabel);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Alarm> CREATOR = new Parcelable.Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

}
