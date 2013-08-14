/*
    This file is part of Say Time.

    Say Time is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Say Time is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Say Time.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.nsdev.saytimeapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;

public class MediaButtonIntentReceiver extends BroadcastReceiver {

    public static final int KEYCODE_MEDIA_PLAY = 126;
    public static final int KEYCODE_MEDIA_PAUSE = 127;
    private static boolean mPressed = false;
    private static boolean mIsHook = false;

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

        boolean headSetEnabled = prefs.getBoolean("isHeadsetButtonEnabled", false);
        boolean cameraButtonEnabled = prefs.getBoolean("isCameraButtonEnabled", false);

        if (!headSetEnabled && !cameraButtonEnabled) {
            return;
        }

        // Make sure there isn't a call coming in, or in progress
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm.getCallState() != TelephonyManager.CALL_STATE_IDLE)
            return;

        KeyEvent event = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);

        if (event == null) {
            return;
        }

        int keycode = event.getKeyCode();
        int action = event.getAction();
        boolean handled = false;

        switch (keycode) {
            case KeyEvent.KEYCODE_CAMERA:
            case KeyEvent.KEYCODE_HEADSETHOOK:
            case KEYCODE_MEDIA_PLAY:
            case KEYCODE_MEDIA_PAUSE:
                if ((keycode == KeyEvent.KEYCODE_HEADSETHOOK && headSetEnabled) ||
                        (keycode == KeyEvent.KEYCODE_CAMERA && cameraButtonEnabled) ||
                        (keycode == KEYCODE_MEDIA_PLAY && headSetEnabled) ||
                        (keycode == KEYCODE_MEDIA_PAUSE && headSetEnabled) ) {
                    mIsHook = true;
                    handled = true;
                }
                break;
        }

        switch (action) {
            case KeyEvent.ACTION_DOWN:
                mPressed = true;
                if (keycode == KeyEvent.KEYCODE_CAMERA && cameraButtonEnabled) {
                    sayTime(context);
                    handled = true;
                }
                break;
            case KeyEvent.ACTION_UP:
                if (mPressed && mIsHook) {
                    if ((keycode == KeyEvent.KEYCODE_HEADSETHOOK && headSetEnabled) ||
                            (keycode == KEYCODE_MEDIA_PLAY && headSetEnabled) ||
                            (keycode == KEYCODE_MEDIA_PAUSE && headSetEnabled) ||
                            (keycode == KeyEvent.KEYCODE_CAMERA && cameraButtonEnabled)) {
                        sayTime(context);
                        handled = true;
                    }
                }
                break;
        }

        if (isOrderedBroadcast() && handled)
            abortBroadcast();
        else
            clearAbortBroadcast();
    }

    private void sayTime(final Context context) {
        mPressed = false;
        mIsHook = false;

        /*
        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        final WakeLock mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, this.getClass().getName());
        mWakeLock.setReferenceCounted(false);
        mWakeLock.acquire();
        */

        Intent intent = new Intent(context, SayTimeService.class);
        intent.setAction(SayTimeService.SAYTIME_ACTION);
        context.startService(intent);

        /*mWakeLock.release()*/;
    }
}
