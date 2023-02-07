package com.example.itemtouchhelpersample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itemtouchhelpersample.databinding.ItemFoodBinding
import com.google.android.material.snackbar.Snackbar

class FoodAdapter(
    private val items: MutableList<Food>,
) : RecyclerView.Adapter<FoodViewHolder>() {
    
    fun swap(from: Int, to: Int) {
        val temp = items[from]
        items[from] = items[to]
        items[to] = temp
        
        notifyItemMoved(from, to)
    }
    
    fun remove(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val bind = ItemFoodBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FoodViewHolder(bind)
    }
    
    override fun getItemCount(): Int {
        return items.count()
    }
    
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val item = items[position]
        holder.setDisplayItem(item)
    }
}

class FoodViewHolder(private val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
    
    fun setDisplayItem(food: Food) {
        binding.textName.text = food.name
        binding.textCalories.text = String.format(binding.root.resources.getString(R.string.calories), food.kcal)
    }
}

