package me.leechu.chartsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.leechu.chartsample.databinding.ActivityBarChartBinding

class ActivityBarChart : AppCompatActivity() {
    
    private val binding by lazy { ActivityBarChartBinding.inflate(layoutInflater) }
    private val pairAdapter by lazy { PairAdapter() }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.apply {
            recyclerView.adapter = pairAdapter
            
            attachDefaultAction(btnAdd, editName, editValue, pairAdapter) {
                barChart.insert(it.first, it.second)
            }
        }
    }
}