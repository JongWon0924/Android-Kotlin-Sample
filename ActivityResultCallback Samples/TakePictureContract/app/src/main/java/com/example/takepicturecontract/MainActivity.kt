package com.example.takepicturecontract

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.example.takepicturecontract.databinding.ActivityMainBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

val DATE_FORMAT: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    
    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
        binding.imgPreview.setImageURI(
            if (result) {
                pictureUri
            } else {
                contentResolver.delete(pictureUri, null, null)
                null
            }
        )
    }
    
    private lateinit var pictureUri: Uri
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.btnTakePic.setOnClickListener {
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, DATE_FORMAT.format(Date()))
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
            }
            
            if (::pictureUri.isInitialized) {
                pictureUri.path?.let { path -> File(path).deleteOnExit() }
            }
            pictureUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues) ?: throw IOException("Failed to create file")
            
            takePictureLauncher.launch(pictureUri)
        }
    }
}