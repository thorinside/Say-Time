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
import android.widget.Toast;

public class AlarmIntentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Intent sayTimeIntent = new Intent(context, SayTimeService.class);
            sayTimeIntent.setAction(SayTimeService.SAYTIME_ACTION);
            sayTimeIntent.putExtra("skip_interval", true);
            sayTimeIntent.putExtra("hourly_chime", true);
            context.startService(sayTimeIntent);
        } catch (Exception e) {
            Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
