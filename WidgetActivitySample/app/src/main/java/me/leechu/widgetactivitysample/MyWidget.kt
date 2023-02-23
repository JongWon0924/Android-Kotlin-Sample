package me.leechu.widgetactivitysample

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class MyWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
            Log.wtf("CALLED", "CALLED APPLICATION ON UPDATED")
        }
    }
    
    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }
    
    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    val pref = context.getSharedPreferences(KEY_SHARED, Context.MODE_PRIVATE)
    val widgetText = pref.getString(KEY_TEXT + appWidgetId, "")
    
    // Construct the RemoteViews object
    Log.wtf("TEXT", "$widgetText $appWidgetId")
    val views = RemoteViews(context.packageName, R.layout.my_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)
    appWidgetManager.updateAppWidget(appWidgetId, views)
}