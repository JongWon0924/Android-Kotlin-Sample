package me.leechu.chartsample

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import com.google.android.material.snackbar.Snackbar
import me.leechu.chartsample.charts.PieChartView
import me.leechu.chartsample.databinding.ActivityMainBinding
import java.util.function.Consumer
import kotlin.math.min

fun showMessage(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}

fun attachDefaultAction(btnAdd: Button, editName: EditText, editValue: EditText, pairAdapter: PairAdapter, consumer: Consumer<Pair<String, Int>>) {
    btnAdd.setOnClickListener {
        val name = editName.text.toString()
        val value = editValue.text.toString().toIntOrNull()
        
        if (name.isEmpty() || value == null) {
            showMessage(it, "All fields must be valid.")
            return@setOnClickListener
        }
        
        val pair = Pair(name, value)
        pairAdapter.insert(pair)
        consumer.accept(pair)
        
        editName.setText("")
        editValue.setText("")
    }
}

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.btnBarChart.setOnClickListener {
            startActivity(Intent(this@MainActivity, ActivityBarChart::class.java))
        }
        
        binding.btnLineChart.setOnClickListener {
            startActivity(Intent(this@MainActivity, ActivityLineChart::class.java))
        }
        
        binding.btnPieChart.setOnClickListener {
            startActivity(Intent(this@MainActivity, ActivityPieChart::class.java))
        }
    }
}