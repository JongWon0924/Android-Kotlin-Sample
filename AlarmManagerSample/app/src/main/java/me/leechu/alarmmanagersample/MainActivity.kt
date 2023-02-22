package me.leechu.alarmmanagersample

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import me.leechu.alarmmanagersample.databinding.ActivityMainBinding
import java.util.*

fun setAlarmManager(
    context: Context,
    alarmManager: AlarmManager,
    data: Pair<String, String>
) {
    val pendingIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
        intent.putExtra(data.first, data.second)
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }
    
    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, 20)
        set(Calendar.MINUTE, 13)
    }
    
    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
    )
}

const val KEY_DATA = "KEY_DATA"

class MainActivity : AppCompatActivity() {
    
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        it.iterator().forEach { x ->
            if (!x.value) {
                Toast.makeText(this@MainActivity, "Please grant all permission..", Toast.LENGTH_SHORT).show()
                return@forEach
            }
        }
    }
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    
    private val alarmManager by lazy { getSystemService(AlarmManager::class.java) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        requestPermissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.POST_NOTIFICATIONS,
                android.Manifest.permission.SCHEDULE_EXACT_ALARM
            )
        )
        
        setAlarmManager(this, alarmManager, KEY_DATA to "Hello World!")
    }
}
