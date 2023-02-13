package me.leechu.splashscreensample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    
    private val btnDialog by lazy { findViewById<Button>(R.id.btnDialog) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        btnDialog.setOnClickListener {
            createAlert("Message", "Hello world").show()
        }
    }
    
    private fun createAlert(title: String, message: String) = AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setNegativeButton("Close", null)
        .create()
}