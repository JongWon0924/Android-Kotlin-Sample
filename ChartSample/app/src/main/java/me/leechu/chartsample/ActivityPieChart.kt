package me.leechu.chartsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.leechu.chartsample.databinding.ActivityPieChartBinding

class ActivityPieChart : AppCompatActivity() {
    
    private val binding by lazy { ActivityPieChartBinding.inflate(layoutInflater) }
    private val pairAdapter by lazy { PairAdapter() }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.apply {
            recyclerView.adapter = pairAdapter
            
            attachDefaultAction(btnAdd, editName, editValue, pairAdapter) {
                pieChart.insert(it.first, it.second)
            }
        }
    }
}