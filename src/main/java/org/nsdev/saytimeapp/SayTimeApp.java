package org.nsdev.saytimeapp;

import android.app.Application;
import android.content.ComponentName;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

/**
 * Created by neal on 2013-08-13.
 */
public class SayTimeApp extends Application implements TextToSpeech.OnInitListener {

    private static final String TAG = "SayTimeApp";

    public static TextToSpeech textToSpeech;

    @Override
    public void onCreate() {
        textToSpeech = new TextToSpeech(this, this);
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        Log.e(TAG, "Text to Speech Terminated.");
        textToSpeech.stop();
        textToSpeech.shutdown();
        super.onTerminate();
    }

    @Override
    public void onInit(int i) {
        Log.e(TAG, "Text To Speech Initialized: " + i);
    }
}
