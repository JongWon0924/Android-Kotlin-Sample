package com.leechu.pulltorefreshsample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.leechu.pulltorefreshsample.databinding.ActivityMainBinding
import java.util.function.Consumer
import kotlin.concurrent.thread

fun loadData(consumer: Consumer<Item>) {
    thread {
        val item = Item(1, "Hello World!")
        Thread.sleep(300L) // Loading...
        Handler(Looper.getMainLooper()).post { consumer.accept(item) }
    }
}

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter by lazy { ItemAdapter() }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.recyclerView.apply {
            adapter = this@MainActivity.adapter
            
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                var isRunning = false
                
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (isRunning)
                        return
                    
                    val canScrollUp = recyclerView.canScrollVertically(-1)
                    if (!canScrollUp && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        isRunning = true
                        binding.progressTop.visibility = View.VISIBLE
                        
                        loadData {
                            this@MainActivity.adapter.insert(it)
                            binding.progressTop.visibility = View.GONE
                            isRunning = false
                        }
                    }
                }
            })
        }
    }
}