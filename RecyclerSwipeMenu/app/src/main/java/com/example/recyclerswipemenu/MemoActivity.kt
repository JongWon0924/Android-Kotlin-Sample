package com.example.recyclerswipemenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerswipemenu.databinding.ActivityMemoBinding
import com.google.android.material.snackbar.Snackbar


class MemoActivity : AppCompatActivity() {
    companion object {
        const val KEY_TITLE = "title"
        const val KEY_CONTENT = "content"
    }
    
    private val binding by lazy { ActivityMemoBinding.inflate(layoutInflater) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setContentView(root)
            
            btnConfirm.setOnClickListener {
                val title = textTitle.text.toString()
                val content = textContent.text.toString()
                
                if (title.isEmpty() || content.isEmpty()) {
                    Snackbar.make(it, "Title and content can not be empty.", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                
                intent.apply {
                    putExtra(KEY_TITLE, title)
                    putExtra(KEY_CONTENT, content)
                }
                
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}