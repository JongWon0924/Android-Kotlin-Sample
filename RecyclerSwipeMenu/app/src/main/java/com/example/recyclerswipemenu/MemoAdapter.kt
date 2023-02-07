package com.example.recyclerswipemenu

import android.opengl.Visibility
import android.system.Os.remove
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerswipemenu.databinding.ItemMemoBinding
import kotlin.math.abs

class MemoAdapter : RecyclerView.Adapter<MemoViewHolder>() {
    
    private val items by lazy {
        mutableListOf<Memo>(
            Memo(
                1,
                "Hello World",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            ),
            Memo(
                2,
                "Hello World111",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            ),
            Memo(
                3,
                "Hello World222",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            ),
            Memo(
                4,
                "Hello World333",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            ),
            Memo(
                5,
                "Hello W444orld",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            ),
            Memo(
                6,
                "Hello Wo555rld",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            ),
            Memo(
                7,
                "Hello Wo666rld",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            ),
            Memo(
                8,
                "Hello W777orld",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            ),
            Memo(
                9,
                "Hello Wo888rld",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            ),
            Memo(
                10,
                "Hello Wo999rld",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            ),
            Memo(
                11,
                "Hello Wo101010",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            ),
            Memo(
                12,
                "Hello Wo111111",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            ),
            Memo(
                13,
                "Hello Wo121212",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            ),
            Memo(
                14,
                "Hello Wo131313",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            ),
            Memo(
                15,
                "Hello Wo141414",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
            ),
        )
    }
    
    val nextId
        get() = (items.maxOfOrNull { x -> x.id } ?: 0) + 1
    
    fun insert(memo: Memo): Boolean {
        items.add(memo)
        notifyItemInserted(items.size - 1)
        return true
    }
    
    fun remove(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }
    
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val binding = ItemMemoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val holder = MemoViewHolder(binding)
        
        binding.btnDelete.setOnClickListener {
//            Toast.makeText(parent.context, "Clicked..", Toast.LENGTH_SHORT).show()
            Log.w("Button", "Button ${holder.adapterPosition} clicked!!")
            remove(holder.adapterPosition)
        }
    
        binding.root.setOnClickListener {
            binding.btnDelete.visibility = View.GONE
        }
        
        return holder
    }
    
    override fun getItemCount(): Int {
        return items.count()
    }
    
    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        val item = items[position]
        
        holder.itemView.findViewById<Button>(R.id.btnDelete).visibility = View.GONE
        holder.setDisplayItem(item)
    }
}

class MemoViewHolder(private val binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setDisplayItem(item: Memo) {
        binding.textTitle.text = item.title
        binding.textContent.text = item.content
    }
}