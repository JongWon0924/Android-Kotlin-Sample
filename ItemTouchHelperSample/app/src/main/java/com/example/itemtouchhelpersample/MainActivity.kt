package com.example.itemtouchhelpersample

import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.itemtouchhelpersample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    
    private var foodList = mutableListOf<Food>()
    private val adapter by lazy { FoodAdapter(foodList) }
    
    private val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP + ItemTouchHelper.DOWN, ItemTouchHelper.LEFT
    ) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            Log.d("Recycler - OnMove", "${viewHolder.adapterPosition} -> ${target.adapterPosition}")
            adapter.swap(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }
        
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            adapter.notifyItemChanged(viewHolder.adapterPosition)
            
            if (direction == ItemTouchHelper.LEFT) {
                Log.d("Recycler - OnSwiped", "Left")
                adapter.remove(viewHolder.adapterPosition)
            }
        }
    }
    
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArray("Key", foodList.toTypedArray())
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        val key = savedInstanceState?.getParcelableArray("Key")
        if (key == null) {
            foodList.addAll(getFoodMock())
        } else {
            key.forEach { x -> foodList.add(x as Food) }
        }
        
        binding.root.adapter = adapter
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.root)
    }
}