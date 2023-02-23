package me.leechu.widgetactivitysample

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import me.leechu.widgetactivitysample.databinding.ActivitySettingBinding

const val KEY_TEXT = "KEY.TEXT"
const val KEY_SHARED = "SHAREDPREF"

class SettingActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySettingBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        val appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID
        
        val pref = this@SettingActivity.getSharedPreferences(KEY_SHARED, MODE_PRIVATE)
        
        binding.btnSave.setOnClickListener {
//            pref.edit().apply {
//                putString(KEY_TEXT + appWidgetId, binding.editText.text.toString())
//            }.apply()
//
//            val resultValue = Intent().apply {
//                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
//                putExtra(KEY_TEXT, binding.editText.text.toString())
//            }
//            setResult(Activity.RESULT_OK, resultValue)
            
            val appWidgetManager = AppWidgetManager.getInstance(this@SettingActivity)
            
            val views = RemoteViews(this@SettingActivity.packageName, R.layout.my_widget)
            views.setTextViewText(R.id.appwidget_text, binding.editText.text.toString())
            appWidgetManager.updateAppWidget(appWidgetId, views)
            finish()
        }
    }
    
    
}