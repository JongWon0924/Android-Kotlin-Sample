package com.example.networkstatussample

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import com.example.networkstatussample.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

fun isInternetAvailable(context: Context): Boolean {
    val connMgr = context.getSystemService(ConnectivityManager::class.java)
    val isConnected = connMgr.activeNetwork != null
    
    if (isConnected) {
        val network = connMgr.activeNetwork!!
        val caps = connMgr.getNetworkCapabilities(network)
        val isCellular = caps?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false
        val isWiFi = caps?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false
        
        Log.d("Network Info ", "IsCellular $isCellular IsWiFi $isWiFi")
    }
    
    Log.d("Network Info", "IsConnected $isConnected")
    return isConnected
}

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var connectivityManager: ConnectivityManager
    
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            // When network is connected...
            Snackbar.make(binding.root, "Network connected $network", Toast.LENGTH_SHORT).show()
        }
        
        override fun onLost(network: Network) {
            super.onLost(network)
            // When network is lost...
            Snackbar.make(binding.root, "Network lost $network", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        NetworkRequest.Builder()
//            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI) // Restrict the network type to Wi-Fi.
//            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR) // Restrict the network type to Cellular.
            .let {
                connectivityManager.registerNetworkCallback(it.build(), networkCallback)
            }
        
        binding.btnCheck.setOnClickListener {
            isInternetAvailable(this@MainActivity)
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}