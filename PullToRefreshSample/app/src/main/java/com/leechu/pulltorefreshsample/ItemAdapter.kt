package com.leechu.pulltorefreshsample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leechu.pulltorefreshsample.databinding.ItemBinding

class ItemAdapter : RecyclerView.Adapter<ItemViewHolder>() {
    
    private val items = mutableListOf<Item>()
    
    fun insert(item: Item) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val holder = ItemViewHolder(binding)
        
        return holder
    }
    
    override fun getItemCount(): Int = items.count()
    
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.setDisplayItem(item)
    }
}

class ItemViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setDisplayItem(item: Item) {
        binding.textView.text = item.title
    }
}