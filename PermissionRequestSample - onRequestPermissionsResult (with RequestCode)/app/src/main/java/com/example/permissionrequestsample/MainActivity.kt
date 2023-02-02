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


const val CAMERA_PERMISSION_REQUEST_CODE = 0x000_000_1 // REQUEST_CODE -> 권한이 여러개 있을 시 구분하는 용도


class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    
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
                    // ** 권한이 거부 한 적 없으면 실행되는 부분
                    
                    // 권한을 요청하면 onRequestPermissionResult에서 Callback을 처리해야 함.
                    // 권한 요청이 여러 곳에서 진행되는것을 구분하기 위해 RequestCode를 지정함.
                    val permissionArray = arrayOf(android.Manifest.permission.CAMERA)
                    ActivityCompat.requestPermissions(this, permissionArray, CAMERA_PERMISSION_REQUEST_CODE)
                }
            }
        }
    }
    
    private fun doSomething() {
        // 권한이 있으면 실행될 부분..
        Snackbar.make(binding.root, "권한이 부여됨", Snackbar.LENGTH_SHORT).show()
    }
    
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            val granted = grantResults.all { result -> result == PackageManager.PERMISSION_GRANTED }
            
            if (granted) {
                // REQUEST_CODE에 해당되는 모든 권한이 거부된 경우
                doSomething()
            } else {
                // REQUEST_CODE에 해당하는 권한이 하나라도 거부된 경우
                Snackbar.make(binding.root, "권한 거부됨", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}