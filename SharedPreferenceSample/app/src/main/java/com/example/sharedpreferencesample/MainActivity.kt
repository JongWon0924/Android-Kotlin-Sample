package com.example.sharedpreferencesample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sharedpreferencesample.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

val APP_PREF = "preferences/main"

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.btnSave.setOnClickListener {
//            SharedPreferences는 설정이 여러개 이상인 경우에 사용함. 따라서 이름으로 구분
//            val sharedPref = getSharedPreferences(APP_PREF, Context.MODE_PRIVATE)

//            구분이 필요 없는 공용 설정의 경우 사용
            val pref = getPreferences(Context.MODE_PRIVATE)
            
            with(pref.edit()) {
                val key = binding.editKey.text.toString()
                val value = binding.editValue.text.toString()
                putString(key, value)
                apply()
            }
            Snackbar.make(binding.root, "Saved", Snackbar.LENGTH_SHORT).show()
        }
        
        binding.btnLoad.setOnClickListener {
            val key = binding.editKey.text.toString()
            val value = binding.editValue.text.toString()
            
            val pref = getPreferences(Context.MODE_PRIVATE)
            val result = pref.getString(key, value)
            
            binding.editValue.setText(result)
        }
    }
}