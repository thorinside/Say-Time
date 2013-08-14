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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AnalogClock;

import com.actionbarsherlock.app.SherlockActivity;

public class SayTimeActivity extends SherlockActivity {

    private static final String TAG = "SayTimeActivity";
    public static final String PREFS_NAME = "SayTimePreferences";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        ProgressDialog dlg = ProgressDialog.show(this, "Please Wait...", "Firing up the service.", true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final AnalogClock clock = (AnalogClock)findViewById(R.id.AnalogClock);

        Log.d(TAG, "Starting SayTimeService...");
        //Intent svc = new Intent(this, SayTimeService.class);
        //startService(svc);

        Log.d(TAG, "Started SayTimeService.");

        clock.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Context context = getApplicationContext();
                Intent intent = new Intent(context, SayTimeService.class);
                intent.setAction(SayTimeService.SAYTIME_ACTION);
                context.startService(intent);
            }
        });

        dlg.dismiss();

    }

    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        this.getSupportMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        if (item.getItemId() == R.id.settings_menu) {
            // Fire off the settings panel activity
            Log.i(TAG, "Settings selected.");

            Intent settingsActivity = new Intent(getBaseContext(), SettingsActivity.class);
            startActivity(settingsActivity);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        try {
            Context context = getApplicationContext();
            Intent intent = new Intent(context, SayTimeService.class);
            intent.setAction(SayTimeService.CONFIGURATION_ACTION);
            context.startService(intent);
        } catch (Exception ex) {
        }

        super.onResume();
    }
}
