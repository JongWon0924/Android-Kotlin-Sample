package me.leechu.chartsample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.leechu.chartsample.databinding.ItemPairBinding

class PairAdapter : RecyclerView.Adapter<PairAdapter.ViewHolder>() {
    
    private val items = mutableListOf<Pair<String, Int>>()
    
    fun insert(pair: Pair<String, Int>) {
        items.add(pair)
        notifyItemInserted(items.count() - 1)
    }
    
    class ViewHolder(private val binding: ItemPairBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(pair: Pair<String, Int>) {
            binding.apply {
                textName.text = pair.first
                textValue.text = pair.second.toString()
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPairBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        
        return ViewHolder(binding)
    }
    
    override fun getItemCount(): Int = items.count()
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }
}