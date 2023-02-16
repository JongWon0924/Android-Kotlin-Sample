package me.leechu.chartsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.leechu.chartsample.databinding.ActivityLineChartBinding

class ActivityLineChart : AppCompatActivity() {
    
    private val binding by lazy { ActivityLineChartBinding.inflate(layoutInflater) }
    private val pairAdapter by lazy { PairAdapter() }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.apply {
            recyclerView.adapter = pairAdapter
            
            attachDefaultAction(btnAdd, editName, editValue, pairAdapter) {
                lineChartView.insert(it.first, it.second)
            }
        }
    }
}