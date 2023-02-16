package me.leechu.widgetsample

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.edit
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.snackbar.Snackbar
import me.leechu.widgetsample.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    
    private val pictureLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            binding.imgPreviewIcon.setImageURI(it)
            pickedUri = it
        }
    }
    
    private lateinit var pickedUri: Uri
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val pref = getSharedPreferences(WidgetPreferences.KEY, Context.MODE_PRIVATE)
        
        binding.editTitle.setText(pref.getString(WidgetPreferences.TEXT_TITLE, ""))
        binding.editDesc.setText(pref.getString(WidgetPreferences.TEXT_DESCRIPTION, ""))
        
        binding.btnIcon.setOnClickListener {
            pictureLauncher.launch("image/*")
        }
        
        binding.btnSave.setOnClickListener {
            pref.edit {
                putString(WidgetPreferences.TEXT_TITLE, binding.editTitle.text.toString())
                putString(WidgetPreferences.TEXT_DESCRIPTION, binding.editDesc.text.toString())
                if (this@MainActivity::pickedUri.isInitialized) {
                    val bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, pickedUri))
                    val array = ByteArrayOutputStream().run {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, this)
                        toByteArray()
                    }
                    
                    putString(WidgetPreferences.IMAGE_THUMB, Base64.encodeToString(array, Base64.NO_WRAP))
                } else {
                    putString(WidgetPreferences.IMAGE_THUMB, null)
                }
                
                commit()
            }
            
            val idArray = AppWidgetManager.getInstance(applicationContext).getAppWidgetIds(ComponentName(applicationContext, AppWidget::class.java))
            val intent = Intent(this@MainActivity, AppWidget::class.java).apply {
                action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray)
            }
            
            sendBroadcast(intent)
            Snackbar.make(it, "Updated...", Snackbar.LENGTH_SHORT).show()
        }
    }
}