package com.example.internalstoragerwsample

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.internalstoragerwsample.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.io.BufferedWriter
import java.io.File
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.btnWrite.setOnClickListener {
            val fileName = binding.editName.text.toString()
            val content = binding.editContent.text.toString()
            
            if (fileName.isEmpty() || content.isEmpty()) {
                Snackbar.make(it, "All fields are required.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            // 일반적으로 내부저장소 파일은 Context 객체의  context.FilesDir 속성을 사용하여
            // 액세스 가능한 디렉터리에 있음.
            // File API를 사용하여 처리하기, 성능 유지를 위해 동일한 파일을 여러번 열거나 닫지 마세요.
            /**
             * val file = File(baseContext.filesDir, "fileName.txt")
             * file.deleteOnExit()
             * file.writeText(content)
             */
            
            // 파일을 스트림을 이용하여 처리하기
            // API24 이상부터는 Context.MODE_PRIVATE 로 설정하지 않으면 SecurityException 발생됨.
            
            baseContext.openFileOutput("$fileName.txt", Context.MODE_PRIVATE).use { stream ->
                stream.write(content.toByteArray())
            }
            Snackbar.make(it, "Successfully writed", Snackbar.LENGTH_SHORT).show()
        }
        
        binding.btnRead.setOnClickListener {
            val fileName = binding.editName.text.toString()
            val file = File(baseContext.filesDir, "$fileName.txt")
            
            if (file.exists()) {
                binding.editContent.setText(file.readText())
            } else {
                Snackbar.make(it, "File not found.", Snackbar.LENGTH_SHORT).show()
            }
        }
        
        binding.btnList.setOnClickListener {
            val list = baseContext.fileList()
            val result = list.joinToString("\r\n")
            alert(result)
        }
    }
    
    private fun alert(content: String) {
        AlertDialog.Builder(this)
            .setTitle("Content")
            .setMessage(content)
            .setNegativeButton("Close") { _: DialogInterface, _: Int ->
                // Nothing to do..
            }
            .create()
            .show()
    }
}