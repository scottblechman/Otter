package com.scottblechman.otter.data;

import android.support.annotation.NonNull;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AlarmManager {

    public static final List<Alarm> ITEMS = new ArrayList<>();

    // public static final Map<String, Alarm> ITEM_MAP = new HashMap<>();

    // TODO: 1/20/19 remove this when alarm add form available
    private static final int COUNT = 3;

    static {
        // Add some sample items.
        for (int i = 0; i < COUNT; i++) {
            addItem(createAlarm(i));
        }
    }

    private static void addItem(Alarm item) {
        ITEMS.add(item);
        // ITEM_MAP.put(item.id, item);
    }

    private static Alarm createAlarm(int position) {
        Date date = new Date();
        if(position > 0) {
            date.setHours(date.getHours() + position);
        }
        return new Alarm(date, "Item "+position);
    }

    public static class Alarm {
        public final Date date;
        public final String label;

        public Alarm(Date date, String label) {
            this.date = date;
            this.label = label;
        }

        @Override
        @NonNull
        public String toString() {
            return new StringBuilder(date.toString())
            .append(" - ")
            .append(label)
                    .toString();
        }

        public String getDateAsString() {
            return new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(date);
        }

        public String getTimeAsString() {
            return new SimpleDateFormat("h:mm", Locale.getDefault()).format(date);
        }
    }
}
