package com.example.activityresultcallbacksample

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.activityresultcallbacksample.databinding.ActivityMainBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    
    private val previewPictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        result.data?.extras?.get("data")?.let {
            binding.imagePreview.setImageBitmap(it as Bitmap)
        }
    }
    
    private val dispatchTakePicture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(file))
            binding.imagePreview.setImageBitmap(bitmap)
        }
    }
    
    private lateinit var file: File
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.btnTakePicturePreview.setOnClickListener {
            previewPictureLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
        
        binding.btnTakePicture.setOnClickListener {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
            val directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            
            file = File.createTempFile("JPEG_${timeStamp}_", ".jpg", directory).also {
                val imageURI = FileProvider.getUriForFile(
                    this,
                    "com.example.activityresultcallbacksample.fileprovider",
                    it
                )
                
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI)
                
                dispatchTakePicture.launch(intent)
            }
        }
    }
}

