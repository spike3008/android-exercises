package com.android.madd.exercise1;

import android.app.Application;

import static timber.log.Timber.DebugTree;
import static timber.log.Timber.plant;

public class LoggableApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            plant(new DebugTree());
        }
    }
}
