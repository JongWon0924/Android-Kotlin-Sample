package me.leechu.chartsample.charts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class PieChartView(context: Context, attr: AttributeSet) : View(context, attr) {
    
    private val names = mutableListOf<String>()
    private val values = mutableListOf<Int>()
    
    private val paint by lazy { Paint() }
    private val textPaint by lazy {
        Paint().apply {
            color = Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = 28f
        }
    }
    
    private val colors = mutableListOf<Int>()
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val size = width.coerceAtMost(height).toFloat() - PADDING * 2
        val rect = RectF(PADDING, PADDING, size, size)
        val sum = values.sum().toFloat()
        
        var previousAngle = -90f
        
        for (i in 0 until values.count()) {
            val angle = (values[i] / sum) * 360f
            
            paint.color = getColorOrCreate(i)
            canvas.drawArc(rect, previousAngle, angle, true, paint)
            canvas.drawText(names[i], 0f, 0f, textPaint)
            previousAngle += angle
        }
    }
    
    private fun getColorOrCreate(index: Int): Int {
        if (index >= colors.count()) {
            val r = Random.nextInt(0, 255).toFloat()
            val g = Random.nextInt(0, 255).toFloat()
            val b = Random.nextInt(0, 255).toFloat()
            
            colors.add(Color.valueOf(r, g, b).toArgb())
        }
        
        return colors[index]
    }
    
    fun insert(name: String, value: Int) {
        names.add(name)
        values.add(value)
        invalidate()
    }
    
    companion object {
        const val PADDING = 20f
    }
}