package org.nsdev.saytimeapp;

import android.content.Context;
import android.speech.tts.TextToSpeech;

public class TTSEngineManager implements TextToSpeech.OnInitListener {
    private static TextToSpeech tts;
    protected Context context;

    public TTSEngineManager(Context context) {
        this.context = context;
        tts = new TextToSpeech(this.context, this);
    }

    public static TextToSpeech getTTS() {
        return tts;
    }

    public void destroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
        } else {
        }
    }
}
