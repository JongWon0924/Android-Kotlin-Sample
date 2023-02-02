package com.example.mediaplayersample

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.mediaplayersample.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    
    private lateinit var mediaPlayer: MediaPlayer
    
    private val getMediaLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it == null) {
            Snackbar.make(binding.root, "No media selected.", Toast.LENGTH_SHORT).show()
            return@registerForActivityResult
        }
        
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.stop()
        }
        
        mediaPlayer = MediaPlayer().apply {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            setAudioAttributes(audioAttributes)
            setDataSource(applicationContext, it)
            prepare()
        }
        mediaPlayer.start()
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.btnStart.setOnClickListener {
            getMediaLauncher.launch("audio/*")
        }
        
        binding.btnStop.setOnClickListener {
            mediaPlayer.stop()
        }
    }
    
}