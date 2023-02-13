package me.leechu.spinnersample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import me.leechu.spinnersample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        val itemList = listOf("Orange", "Grape", "Kiwi")
        val imageMap = mapOf(
            "Orange" to R.drawable.orange,
            "Grape" to R.drawable.grape,
            "Kiwi" to R.drawable.kiwi
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, itemList)
        
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val item = parent.getItemAtPosition(pos) as String
                imageMap[item]?.let {
                    binding.imageView.setImageResource(it)
                }
            }
            
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}