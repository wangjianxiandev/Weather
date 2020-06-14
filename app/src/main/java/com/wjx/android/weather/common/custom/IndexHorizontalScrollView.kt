package com.wjx.android.weather.common.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.HorizontalScrollView
import com.wjx.android.weather.common.util.DisplayUtil

/**
 * Created by user on 2016/10/19.
 */
class IndexHorizontalScrollView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : HorizontalScrollView(context, attrs, defStyleAttr) {
    private var textPaint: Paint? = null
    private var hourlyView: HourlyView? = null
    private fun init() {
        textPaint = Paint()
        textPaint!!.textSize = DisplayUtil.sp2px(context, 12)
        textPaint!!.isAntiAlias = true
        textPaint!!.color = Color.WHITE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val offset = computeHorizontalScrollOffset()
        val maxOffset: Int =
            (computeHorizontalScrollRange() - DisplayUtil.getScreenWidth(context)).toInt()
        if (hourlyView != null) {
            hourlyView!!.setScrollOffset(offset, maxOffset)
        }
    }

    fun setHourlyView(hourlyView: HourlyView?) {
        this.hourlyView = hourlyView
    }

    companion object {
        private const val TAG = "IndexHorizontal"
    }

    init {
        init()
    }
}