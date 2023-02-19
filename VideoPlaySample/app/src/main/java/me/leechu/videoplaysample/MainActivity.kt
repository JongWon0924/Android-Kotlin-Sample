package me.leechu.videoplaysample

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import me.leechu.videoplaysample.databinding.ActivityMainBinding
import java.time.LocalTime
import java.time.format.DateTimeFormatter

const val SAMPLE_VIDEO_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
val TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("mm:ss")

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val handler by lazy { Handler(Looper.getMainLooper()) }
    
    private val videoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            binding.videoView.setVideoURI(it)
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.apply {
            editUrl.setText(SAMPLE_VIDEO_URL)
            
            btnLocal.setOnClickListener {
                videoLauncher.launch("video/mp4")
            }
            
            btnPlay.setOnClickListener {
                videoView.start()
            }
            
            btnStop.setOnClickListener {
                videoView.pause()
            }
            
            btnLoad.setOnClickListener {
                val url = editUrl.text.toString()
                
                videoView.setVideoURI(Uri.parse(url))
                videoView.requestFocus()
            }
            
            videoView.setOnPreparedListener {
                videoView.start()
                val finalTime = videoView.duration
                seekBar.progress = 0
                seekBar.max = finalTime
                
                handler.postDelayed({ updateVideoTime() }, 100L)
            }
            
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser && seekBar != null) {
                        videoView.seekTo(progress)
                    }
                }
                
                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }
                
                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })
        }
    }
    
    private fun updateVideoTime() {
        val curPosition: Int = binding.videoView.currentPosition
        
        with(binding) {
            textVideoTime.text = LocalTime.ofSecondOfDay(curPosition / 1000L).format(TIME_FORMATTER)
            seekBar.progress = curPosition
        }
        handler.postDelayed({ updateVideoTime() }, 100L)
    }
}