package me.leechu.chartsample.charts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.view.updateLayoutParams

class LineChartView(context: Context, attrSet: AttributeSet) : View(context, attrSet) {
    
    private val values = mutableListOf<Int>()
    private val names = mutableListOf<String>()
    
    private val paint by lazy {
        Paint().apply {
            color = Color.parseColor("#3700b3")
            strokeWidth = STROKE_SIZE
        }
    }
    
    private val textPaint by lazy {
        Paint().apply {
            color = Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = 24f
        }
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        val max = (values.maxOrNull() ?: 0).toFloat()
        val virtualHeight = height - PADDING * 2
        var x = PADDING
        
        var previousHeight = -1f
        
        for (i in 0 until values.count()) {
            val size = values[i] / max * virtualHeight
            canvas.drawOval(x, virtualHeight - size, x + MARKER_SIZE, virtualHeight - size + MARKER_SIZE, paint)
            
            if (previousHeight != -1f) {
                canvas.drawLine(
                    x - LINE_SPACING + (MARKER_SIZE / 2), virtualHeight - previousHeight + (MARKER_SIZE / 2),
                    x + (MARKER_SIZE / 2), virtualHeight - size + (MARKER_SIZE / 2),
                    paint
                )
            }
            
            previousHeight = size
            x += LINE_SPACING
        }
    }
    
    fun insert(name: String, x: Int) {
        names.add(name)
        values.add(x)
        
        updateLayoutParams {
            width = ((values.count() * LINE_SPACING) + PADDING * 2).toInt()
        }
        invalidate()
    }
    
    companion object {
        const val MARKER_SIZE = 10f
        const val STROKE_SIZE = 3f
        const val LINE_SPACING = 75f
        const val PADDING = 25f
    }
}