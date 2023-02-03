package com.example.mediastorefilewritesample

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.activity.result.contract.ActivityResultContracts
import com.example.mediastorefilewritesample.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.*


fun savePlainText(
    context: Context,
    content: String,
    mimeType: String,
    displayName: String,
): Uri {
    val values = ContentValues().apply {
        put(MediaStore.Files.FileColumns.DISPLAY_NAME, displayName)
        put(MediaStore.Files.FileColumns.MIME_TYPE, mimeType)
        put(MediaStore.Files.FileColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
    }
    
    val resolver = context.contentResolver
    var uri: Uri? = null
    
    try {
        uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values) ?: throw IOException("Failed to create new MediaStore record.")
        
        resolver.openOutputStream(uri)?.use {
            val writer = BufferedWriter(OutputStreamWriter(it))
            writer.write(content)
            writer.flush()
            writer.close()
        }
        
        return uri
    } catch (e: IOException) {
        uri?.let {
            resolver.delete(uri, null, null)
        }
        
        throw e
    }
}

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    
    private val fileSelectLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
        if (it == null) {
            binding.textContent.text = ""
            return@registerForActivityResult
        }
        
        contentResolver.openInputStream(it)?.let { inputStream ->
            val reader = BufferedReader(InputStreamReader(inputStream))
            val builder = StringBuilder()
            
            contentResolver.query(it, null, null, null, null)?.run {
                moveToFirst()
                builder.append(getString(getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)) + "\r\n")
            }
            
            reader.forEachLine { x: String ->
                builder.append(x)
            }
            
            binding.textContent.text = builder.toString()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.btnWrite.setOnClickListener {
            val content = binding.fieldContent.text.toString()
            
            if (content.isEmpty()) {
                Snackbar.make(it, "Field can not be empty.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            savePlainText(
                this,
                content,
                "text/plain",
                SimpleDateFormat("yyyy-MM-dd_HHmmss", Locale.US).format(Date())
            )
            
            binding.fieldContent.setText("")
            Snackbar.make(it, "File successfully created.", Snackbar.LENGTH_SHORT).show()
        }
        
        binding.btnRead.setOnClickListener {
            fileSelectLauncher.launch(arrayOf("text/plain"))
        }
    }
}