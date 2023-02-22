package me.leechu.alarmmanagersample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val data = intent.getStringExtra(KEY_DATA)
        Toast.makeText(context, data, Toast.LENGTH_SHORT).show()
    }
}