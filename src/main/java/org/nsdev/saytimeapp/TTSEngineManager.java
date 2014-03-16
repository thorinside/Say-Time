package org.nsdev.saytimeapp;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

public class TTSEngineManager implements TextToSpeech.OnInitListener {
    private static TextToSpeech tts;
    protected Context context;

    public TTSEngineManager(Context context) {
        this.context = context;
        Toast.makeText(context, "Trying to initialize TTS", Toast.LENGTH_SHORT).show();
        tts = new TextToSpeech(this.context, this);
    }

    public static TextToSpeech getTTS() {
        return tts;
    }

    public void destroy() {
        if (tts != null) {
            Toast.makeText(context, "Destroying TTS", Toast.LENGTH_SHORT).show();
            tts.stop();
            tts.shutdown();
        }
    }

    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Toast.makeText(context, "TextToSpeech initialization succeeded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "TextToSpeech initialization failed", Toast.LENGTH_SHORT).show();
        }
    }
}
