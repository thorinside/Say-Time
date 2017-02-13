package org.nsdev.saytimeapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetProvider extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];

            Intent sayTimeIntent = new Intent(context, SayTimeService.class);
            sayTimeIntent.setAction(SayTimeService.SAYTIME_ACTION);
            sayTimeIntent.putExtra("skip_interval", false);
            sayTimeIntent.putExtra("hourly_chime", false);

            // Create an Intent to launch ExampleActivity
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, sayTimeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            // Get the layout for the App Widget and attach an on-click listener to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_provider_layout);
            views.setOnClickPendingIntent(R.id.button, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current App Widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
