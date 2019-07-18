package com.scottblechman.otter.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.joda.time.DateTime;

import java.util.Objects;

@SuppressWarnings("WeakerAccess")
@Entity(tableName = "alarms")
public class Alarm implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mUid;

    @NonNull
    @ColumnInfo(name = "date")
    private DateTime mDate;

    @ColumnInfo(name = "label")
    private String mLabel;

    @ColumnInfo(name = "enabled")
    private boolean mEnabled;

    // Default constructor, used when new Alarm object created
    @Ignore
    public Alarm() {
        mDate = new DateTime();
        mLabel = "";
        mEnabled = true;
    }

    public Alarm(@NonNull DateTime date, String label) {
        this.mDate = date;
        this.mLabel = label;
        this.mEnabled = true;
    }

    public DateTime getDate() {
        return mDate;
    }

    public String getLabel() {
        return mLabel;
    }

    public int getUid() { return mUid; }

    public boolean getEnabled() { return mEnabled; }

    public void setUid(int id) { mUid = id; }

    public void setLabel(String toString) {
        mLabel = toString;
    }

    public void setEnabled(boolean enabled) { mEnabled = enabled; }

    public void setDate(DateTime dateTime) { mDate = dateTime; }

    // All methods below allow for conversion to a Parcelable object
    protected Alarm(Parcel in) {
        mUid = in.readInt();
        long tmpMDate = in.readLong();
        mDate = Objects.requireNonNull(tmpMDate != -1 ? new DateTime(tmpMDate) : null);
        mLabel = in.readString();
        mEnabled = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mUid);
        dest.writeLong(mDate.getMillis());
        dest.writeString(mLabel);
        dest.writeByte((byte) (mEnabled ? 1 : 0));
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
