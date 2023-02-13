package me.leechu.notificationsample

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import me.leechu.notificationsample.databinding.ActivityAlertBinding

class AlertActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityAlertBinding.inflate(layoutInflater) }
    private val notifyMgr by lazy { NotificationManagerCompat.from(this@AlertActivity) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        val notifyId = intent.getIntExtra(NOTIFY_ID, -1)
        
        if (notifyId != -1) {
            getReceiveString()?.let {
                binding.textInfo.text = it
                
                val replyNotification = NotificationCompat.Builder(this@AlertActivity, MESSAGE_NOTIFY_ID)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentText("Your message has been sent.")
                    .setAutoCancel(true)
                    .build()
                
                if (ContextCompat.checkSelfPermission(
                        this@AlertActivity,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return@let
                }
                
                notifyMgr.notify(notifyId, replyNotification)
            }
            
            notifyMgr.cancel(notifyId) // NOTIFY ID (PendingIntent RequestCode와 NotifyId 모두 일치해야함..)
        }
    }
    
    private fun getReceiveString(): String? {
        val remoteInput: String? = RemoteInput.getResultsFromIntent(intent)?.run {
            getString(KEY_REPLY).toString()
        }
        return remoteInput
    }
}