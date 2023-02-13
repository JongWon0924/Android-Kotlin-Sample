package me.leechu.notificationsample

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.graphics.drawable.toBitmap
import me.leechu.notificationsample.databinding.ActivityMainBinding

fun createNotificationChannel(
    notifyMgr: NotificationManagerCompat,
    id: String,
    name: String,
    importance: Int,
) {
    val channel = NotificationChannel(
        id,
        name,
        importance
    )
    
    notifyMgr.createNotificationChannel(channel)
}

const val MAIN_NOTIFY_ID = "me.leechu.notify.main"
const val MESSAGE_NOTIFY_ID = "me.leechu.notify.message"
const val INFO_NOTIFY_ID = "me.leechu.notify.info"

const val MAIN_NOTIFY_NAME = "Main Notification"
const val MESSAGE_NOTIFY_NAME = "Message Notification"
const val INFO_NOTIFY_NAME = "Info Notification"

const val NOTIFY_ID = "NOTIFY_ID"
const val KEY_MESSAGE_ARCHIVE = "KEY_MESSAGE_ARCHIVE"
const val KEY_REPLY = "me.leechu.remoteinput.reply"

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val notifyMgr by lazy { NotificationManagerCompat.from(this@MainActivity) }
    
    private var index = 0
    
    private val notifyPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (!it) {
            Toast.makeText(this@MainActivity, "Notification permission denied.", Toast.LENGTH_SHORT).show()
        }
    }
    
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            notifyPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
        
        createNotificationChannel(
            notifyMgr,
            MAIN_NOTIFY_ID,
            MAIN_NOTIFY_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        
        createNotificationChannel(
            notifyMgr,
            INFO_NOTIFY_ID,
            INFO_NOTIFY_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        
        createNotificationChannel(
            notifyMgr,
            MESSAGE_NOTIFY_ID,
            MESSAGE_NOTIFY_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        
        binding.btnNormal.setOnClickListener {
            val targetIntent = Intent(this@MainActivity, AlertActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            
            // Notification 에서 실행되므로 현재 Intent가 아니라 PendingIntent로 지연되게 보내야 함.
            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                targetIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
            
            val notification = NotificationCompat.Builder(this@MainActivity, MAIN_NOTIFY_ID)
                .setContentTitle("Hello World!") // 제목
                .setContentText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.") // 기본 내용
                .setSmallIcon(android.R.drawable.ic_dialog_info) // NotificationBar에서 표시되는 아이콘
                .setContentIntent(pendingIntent) // 알림을 터치하면 실행될 Intent 작업
                .setAutoCancel(true) // 알림을 터치하면 알림이 사라짐.
                .build()
            
            // 같은 NotificationChannel에 있는 Notify들은 그룹되어 표시됨.
            // id가 같으면 동일한 알림은 Push 되지 않음.
            notifyMgr.notify(index++, notification)
        }
        
        binding.btnPriorityNotify.setOnClickListener {
            val targetIntent = Intent(this@MainActivity, AlertActivity::class.java).apply {
                putExtra(NOTIFY_ID, index)
            }
            
            // Notification 에서 실행되므로 현재 Intent가 아니라 PendingIntent로 지연되게 보내야 함.
            val pendingIntent = PendingIntent.getActivity(
                this,
                index,
                targetIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
            
            val notification = NotificationCompat.Builder(this@MainActivity, INFO_NOTIFY_ID)
                .setContentTitle("Head-up Notification!") // 제목
                .setContentText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.") // 기본 내용
                .setSmallIcon(android.R.drawable.ic_dialog_info) // NotificationBar에서 표시되는 아이콘
                .setContentIntent(pendingIntent) // 알림을 터치하면 실행될 Intent 작업
                .setAutoCancel(false) // 알림을 터치하면 알림이 사라짐.
                .build()
            
            // 같은 NotificationChannel에 있는 Notify들은 그룹되어 표시됨.
            // id가 같으면 동일한 알림은 Push 되지 않음.
            notifyMgr.notify(index++, notification)
        }
        
        binding.btnBigStyle.setOnClickListener {
            val pendingIntent = PendingIntent.getActivity(
                this@MainActivity,
                0,
                Intent(this@MainActivity, AlertActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )
            
            val style = NotificationCompat.BigTextStyle().run {
                bigText(
                    "Android 9 introduces several enhancements to notifications, all of which are available to developers targeting " +
                            "API level 28 and above."
                )
            }
            
            val builder = NotificationCompat.Builder(this@MainActivity, MAIN_NOTIFY_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("New Message")
                .setContentText("Notifications in Android P")
                .setStyle(style)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()
            
            notifyMgr.notify(index++, builder)
        }
        
        binding.btnInboxStyle.setOnClickListener {
            val pendingIntent = PendingIntent.getActivity(
                this@MainActivity,
                0,
                Intent(this@MainActivity, AlertActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )
            
            val style = NotificationCompat.InboxStyle().run {
                addLine("Hello")
                addLine("World")
                addLine("Lorem ipsum dolor sit amet.")
            }
            
            val builder = NotificationCompat.Builder(this, MAIN_NOTIFY_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("New Mail")
                .setContentText("+3 Mails")
                .setStyle(style)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()
            
            notifyMgr.notify(index++, builder)
        }
        
        binding.btnMessageStyle.setOnClickListener {
            val pendingIntent =
                PendingIntent.getActivity(this@MainActivity, 0, Intent(this@MainActivity, AlertActivity::class.java), PendingIntent.FLAG_IMMUTABLE)
            
            val john = Person.Builder()
                .setIcon(IconCompat.createWithResource(this@MainActivity, R.drawable.baseline_person_24))
                .setName("John")
                .build()
            
            val jane = Person.Builder()
                .setIcon(IconCompat.createWithResource(this@MainActivity, R.drawable.baseline_person_24))
                .setName("Jane")
                .build()
            
            val style = NotificationCompat.MessagingStyle(john)
            style.addMessage("You can get great deals there", System.currentTimeMillis(), jane)
            style.addMessage("I know what to get", System.currentTimeMillis(), john)
            
            val builder = NotificationCompat.Builder(this@MainActivity, MESSAGE_NOTIFY_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("New Message")
                .setContentText("Content here...")
                .setContentIntent(pendingIntent)
                .setStyle(style)
                .setAutoCancel(true)
                .build()
            
            notifyMgr.notify(index++, builder)
        }
        
        
        binding.btnActionNotify.setOnClickListener {
            val targetIntent = Intent(this@MainActivity, AlertActivity::class.java)
                .apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    putExtra(NOTIFY_ID, index)
                }
            
            val pendingIntent = PendingIntent.getActivity(
                this@MainActivity,
                index,
                targetIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
            )
            
            val notification = NotificationCompat.Builder(this, INFO_NOTIFY_ID)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setSmallIcon(R.drawable.notification_icon)
                .setLargeIcon(ContextCompat.getDrawable(this@MainActivity, R.drawable.reply_icon)!!.toBitmap())
                .setContentTitle("Action Notification")
                .setContentText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                .addAction(0, "DETAIL", pendingIntent)
                .build()
            
            notifyMgr.notify(index++, notification)
        }
        
        binding.btnReplyNotify.setOnClickListener {
            val replyIntent = Intent(this@MainActivity, AlertActivity::class.java).apply {
                putExtra(NOTIFY_ID, index)
            }
            val replyPendingIntent = PendingIntent.getActivity(
                this,
                index,
                replyIntent,
                PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_ONE_SHOT
            )
            
            val remoteInput = RemoteInput.Builder(KEY_REPLY).run {
                setLabel("Write a message.")
                build()
            }
            
            val replyAction = NotificationCompat.Action.Builder(
                0,
                "Reply",
                replyPendingIntent
            ).addRemoteInput(remoteInput)
                .build()
            
            val archiveIntent = Intent(this@MainActivity, AlertActivity::class.java).apply {
                putExtra(NOTIFY_ID, index)
                putExtra(KEY_MESSAGE_ARCHIVE, true)
            }
            val archiveAction = PendingIntent.getActivity(
                this,
                index,
                archiveIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
            )
            
            val notification = NotificationCompat.Builder(this@MainActivity, MESSAGE_NOTIFY_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("New Message")
                .setContentText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                .addAction(0, "Archive", archiveAction)
                .addAction(replyAction)
                .build()
            
            notifyMgr.notify(index++, notification)
        }
    }
}