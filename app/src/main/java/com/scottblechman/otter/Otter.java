package com.scottblechman.otter;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

public class Otter extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
