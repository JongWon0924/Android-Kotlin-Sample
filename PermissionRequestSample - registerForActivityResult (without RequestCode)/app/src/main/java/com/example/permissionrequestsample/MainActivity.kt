package com.example.permissionrequestsample

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.permissionrequestsample.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    
    // 여러개의 권한이 필요한 경우에는 ActivityResultContract.RequestMultiplePermission() 으로 처리함.
    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            // 권한이 모두 부여되면 실행되는 부분
            doSomething()
        } else {
            // 권한이 거부되면 나타나는 메시지
            Snackbar.make(binding.root, "권한을 거부했습니다.", Snackbar.LENGTH_SHORT).show()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.btnRequest.setOnClickListener {
            // 권한 확인하고 있으면 PERMISSION_GRANTED, 없으면 PERMISSION_DENIED
            val result = ActivityCompat.checkSelfPermission(baseContext, android.Manifest.permission.CAMERA)
            
            if (result == PackageManager.PERMISSION_GRANTED) {
                // 권한 체크를 하고 있으면 실행되는 부분
                doSomething()
            } else {
                val permissionRequestResult = ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity, android.Manifest.permission.CAMERA
                )
                
                if (permissionRequestResult) {
                    // 권한을 거부 한 적 있으면 필요한 이유를 설명하거나, 동작을 처리하는 부분
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        .setData(Uri.parse("package:$packageName"))
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .run { startActivity(this) }
                    Snackbar.make(it, "권한이 필요합니다.", Snackbar.LENGTH_SHORT).show()
                } else {
                    // 권한 거부한적 없으면 실행되는 되는 부분
                    permissionLauncher.launch(android.Manifest.permission.CAMERA)
                }
            }
        }
    }
    
    private fun doSomething() {
        // 권한이 있으면 실행될 부분..
        Snackbar.make(binding.root, "권한이 부여됨", Snackbar.LENGTH_SHORT).show()
    }
}