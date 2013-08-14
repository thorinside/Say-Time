package org.nsdev.saytimeapp;

import android.app.Application;
import android.content.ComponentName;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

/**
 * Created by neal on 2013-08-13.
 */
public class SayTimeApp extends Application {

    private static final String TAG = "SayTimeApp";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
