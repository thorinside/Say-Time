package org.nsdev.saytimeapp;

import android.app.Application;

/**
 * Created by neal on 2013-08-13.
 */
public class SayTimeApp extends Application {

    private static final String TAG = "SayTimeApp";
    public static TTSEngineManager mTTSEngineManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mTTSEngineManager = new TTSEngineManager(this);
    }

    @Override
    public void onTerminate() {
        mTTSEngineManager.destroy();
        super.onTerminate();
    }
}
