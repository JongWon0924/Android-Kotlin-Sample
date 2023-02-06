package com.example.locationsample

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.locationsample.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val locationManager by lazy { getSystemService(LocationManager::class.java) }
    private lateinit var locationListener: LocationListener
    
    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) { // Accepted COARSE or FINE
            locationAction()
        } else {
            Snackbar.make(binding.root, "Declined", Snackbar.LENGTH_SHORT).show()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.btnCheck.setOnClickListener {
            permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
    
    private fun locationAction() {
        val result = ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION)
        
        if (result == PackageManager.PERMISSION_DENIED)
            return
        
        // minTimeMs = 0 -> 1 second
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, LocationListener { location ->
            Log.d("Location", "Current Location: ${location.latitude} ${location.longitude} Acc: ${location.accuracy}")
            locationManager.removeUpdates(locationListener) // Get current location only once.
        }.apply { locationListener = this })
    }
}