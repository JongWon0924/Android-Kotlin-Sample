package com.example.sqlitesample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitesample.databinding.ActivityListBinding
import com.example.sqlitesample.databinding.ItemFeedBinding

class ListActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityListBinding.inflate(layoutInflater) }
    private val adapter by lazy { ListAdapter() }
    
    private val dbHelper by lazy { FeedReaderContract.FeedReaderDbHelper(this) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        binding.root.adapter = adapter
        
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            BaseColumns._ID,
            FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
            FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
        )
        
        val sortOrder = "${FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE} DESC"
        
        val cursor = db.query(
            FeedReaderContract.FeedEntry.TABLE_NAME,
            projection,
            null, null, null, null,
            sortOrder
        )
        
        val list = mutableListOf<Feed>()
        with(cursor) {
            while (moveToNext()) {
                list.add(
                    Feed(
                        getInt(0),
                        getString(1),
                        getString(2)
                    )
                )
            }
        }
        adapter.setList(list)
    }
    
    private class ListAdapter : RecyclerView.Adapter<ListViewHolder>() {
        
        private lateinit var list: List<Feed>
        
        fun setList(list: List<Feed>) {
            this.list = list
            notifyDataSetChanged()
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
            val binding = ItemFeedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ListViewHolder(binding)
        }
        
        override fun getItemCount(): Int {
            return list.size
        }
        
        override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
            val item = list[position]
            holder.setItem(item)
        }
    }
    
    private class ListViewHolder(private val binding: ItemFeedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(feed: Feed) {
            binding.textTitle.text = feed.title
            binding.textSubTitle.text = feed.subTitle
        }
    }
}