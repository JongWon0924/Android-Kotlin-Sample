package com.example.recyclerswipemenu

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerswipemenu.databinding.ActivityMainBinding
import kotlin.math.max

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter by lazy { MemoAdapter() }
    
    private val memoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val title = it.data?.getStringExtra(MemoActivity.KEY_TITLE) ?: ""
            val content = it.data?.getStringExtra(MemoActivity.KEY_CONTENT) ?: ""
            
            val nextId = adapter.nextId
            val memo = Memo(nextId, title, content)
            adapter.insert(memo)
        }
    }
    
    private val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.LEFT
    ) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false
        
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        }

        override fun onChildDrawOver(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean,
        ) {
            Log.i("Render // drawOver", "Position($dX, $dY) ActionState $actionState Active $isCurrentlyActive")
            val x = max(dX, -250f)
            if (x == -250f) {
                viewHolder.itemView.findViewById<Button>(R.id.btnDelete).visibility = View.VISIBLE
                super.onChildDrawOver(c, recyclerView, viewHolder, 0f, dY, actionState, isCurrentlyActive)
            } else {
                super.onChildDrawOver(c, recyclerView, viewHolder, x, dY, actionState, isCurrentlyActive)
            }
        }
        
        override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
            return defaultValue * 10f
        }
        
        override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
            return 2f
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setContentView(root)
            
            recyclerView.adapter = adapter
            ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView)
            floatingAdd.setOnClickListener {
                val intent = Intent(this@MainActivity, MemoActivity::class.java)
                memoLauncher.launch(intent)
            }
        }
    }
}