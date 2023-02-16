package me.leechu.chartsample.charts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.view.updateLayoutParams
import kotlin.math.min

class BarChartView(context: Context, attr: AttributeSet) : View(context, attr) {
    
    private val values = mutableListOf<Int>()
    private val names = mutableListOf<String>()
    private val paint by lazy { Paint().apply { color = Color.LTGRAY } }
    private val textPaint by lazy {
        Paint().apply {
            color = Color.BLACK
            textSize = 24f
            textAlign = Paint.Align.CENTER
        }
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.i("LineChartView", "$width x $height")
        
        val bottom = (height - 50 - PADDING).toFloat()
        val max = (values.maxOrNull() ?: 0).toFloat()
        var x = PADDING.toFloat()
        
        for (i in 0 until values.count()) {
            val size = ((values[i] / max) * bottom)
            
            Log.i("DRAW", "$width X $height $size")
            canvas.drawRect(RectF(x, min(bottom - size + PADDING, bottom - 1), x + CHART_WIDTH, bottom), paint)
            canvas.drawText(names[i], x + (CHART_WIDTH / 2), height - 30.toFloat(), textPaint)
            x += CHART_WIDTH + CHART_SPACING
        }
    }
    
    fun insert(name: String, x: Int) {
        names.add(name)
        values.add(x)
        
        updateLayoutParams { width = values.count() * (CHART_WIDTH + CHART_SPACING) + 20 }
        invalidate()
    }
    
    companion object {
        const val CHART_WIDTH = 75
        const val CHART_SPACING = 30
        const val PADDING = 20
    }
}