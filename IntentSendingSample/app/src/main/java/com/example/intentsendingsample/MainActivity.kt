package com.example.intentsendingsample

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import com.example.intentsendingsample.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        
        binding.btnCall.setOnClickListener {
            val intent = Uri.parse(
                "tel:+14429005457"
            ).let { number -> Intent(Intent.ACTION_DIAL, number) }
            
            startActivity(intent)
        }
        
        binding.btnMap.setOnClickListener {
            val intent = Uri.parse(
                "geo:0,0?q=Samsung+Research+America,665+Clyde+Ave,+Mountain+View,+California"
            ).let { location -> Intent(Intent.ACTION_VIEW, location) }
            
            // 지도의 위치를 위도/경도로 나타낼 수 있음.
            Uri.parse("geo:37.42219,-122.08364?z=14") // Z 값은 Zoom Level
            
            startActivity(intent)
        }
        
        binding.btnWebsite.setOnClickListener {
            val intent = Uri.parse(
                "https://research.samsung.com"
            ).let { webpage -> Intent(Intent.ACTION_VIEW, webpage) }
            
            startActivity(intent)
        }
        
        binding.btnMail.setOnClickListener {
            val intent = Uri.parse("mailto:john@doe.com").let {
                Intent(Intent.ACTION_SENDTO, it).apply {
                    putExtra(Intent.EXTRA_SUBJECT, "Mail Subject")
                    putExtra(Intent.EXTRA_TEXT, "Email message text")
                }
            }
            startActivity(intent)
        }
        
        binding.btnCalendar.setOnClickListener {
            val intent = Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI).apply {
                val beginTime = Calendar.getInstance().apply {
                    set(2023, 3 - 1, 24, 17, 0)
                }
                val endTime = Calendar.getInstance().apply {
                    set(2023, 3 - 1, 24, 20, 0)
                }
                
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
                putExtra(CalendarContract.Events.TITLE, "Birthday")
                putExtra(CalendarContract.Events.DESCRIPTION, "Ichinose Asuna")
                putExtra(CalendarContract.Events.EVENT_LOCATION, "Schale")
            }
            
            startActivity(intent)
        }
    }
}