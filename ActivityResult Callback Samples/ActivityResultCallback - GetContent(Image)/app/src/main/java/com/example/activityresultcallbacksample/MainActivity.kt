package com.example.activityresultcallbacksample

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.example.activityresultcallbacksample.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri == null) {
            Snackbar.make(binding.root, "No image selected.", Snackbar.LENGTH_SHORT).show()
            binding.imagePreview.setImageBitmap(null)
            return@registerForActivityResult
        }
        
        // URI를 기준으로 이미지 가져오는 방법
        val source = ImageDecoder.createSource(contentResolver, uri)
        val bitmap = ImageDecoder.decodeBitmap(source)
        
        binding.imagePreview.setImageBitmap(bitmap)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.btnAction.setOnClickListener {
            // MIME TYPE 사용하여 데이터 가져오기 (image/*은 모든 이미지를 선택함)
            getContent.launch("image/*")
        }
    }
}
