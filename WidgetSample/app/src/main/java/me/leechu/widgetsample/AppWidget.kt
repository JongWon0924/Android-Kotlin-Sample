package me.leechu.widgetsample

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.widget.RemoteViews
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap

class AppWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
}

internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    val views = RemoteViews(context.packageName, R.layout.widget_layout)
    val preference = context.getSharedPreferences(WidgetPreferences.KEY, Context.MODE_PRIVATE)
    val title = preference.getString(WidgetPreferences.TEXT_TITLE, "Title")
    val desc = preference.getString(WidgetPreferences.TEXT_DESCRIPTION, "Description")
    val baseBitmap = preference.getString(WidgetPreferences.IMAGE_THUMB, "null")
    
    Log.i("Update App Widget", "$title $desc $baseBitmap")
    
    views.apply {
        setTextViewText(R.id.textTitle, title)
        setTextViewText(R.id.textDesc, desc)
        if (baseBitmap == "null")
            setImageViewBitmap(R.id.imgIcon, ContextCompat.getDrawable(context, R.drawable.baseline_android_24)!!.toBitmap())
        else {
            val array = Base64.decode(baseBitmap, Base64.NO_WRAP)
            val bitmap = BitmapFactory.decodeByteArray(array, 0, array.size, BitmapFactory.Options().apply { inSampleSize = 3 })
            setImageViewBitmap(R.id.imgIcon, bitmap)
        }
        
        setOnClickPendingIntent(
            R.id.root, PendingIntent.getActivity(
                context,
                0,
                Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                },
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
    
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

class WidgetPreferences {
    companion object {
        const val KEY = "PREF.WIDGET"
        const val TEXT_TITLE = "KEY.TEXT.TITLE"
        const val TEXT_DESCRIPTION = "KEY.TEXT.DESCRIPTION"
        const val IMAGE_THUMB = "KEY.IMAGE.THUMB"
    }
}