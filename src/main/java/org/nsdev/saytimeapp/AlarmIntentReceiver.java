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
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class AlarmIntentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            // It's important to do the hourly chime only if the
            // phone is not in vibrate mode
            AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
            switch (am.getRingerMode()) {
                case AudioManager.RINGER_MODE_SILENT:
                case AudioManager.RINGER_MODE_VIBRATE:
                    return;
            }

            // Make sure there isn't a call coming in, or in progress
            TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm.getCallState() != TelephonyManager.CALL_STATE_IDLE)
                return;

            Intent sayTimeIntent = new Intent(context, SayTimeService.class);
            sayTimeIntent.setAction(SayTimeService.SAYTIME_ACTION);
            sayTimeIntent.putExtra("skip_interval", true);
            sayTimeIntent.putExtra("hourly_chime", true);
            context.startService(sayTimeIntent);

        } catch (Throwable ex) {
            Log.e("SayTimeAlarmIntentReceiver", "Error during alarm.", ex);
        }
    }
}
