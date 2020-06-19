package com.wjx.android.weather.common.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/15 8:35
 */
class TemperatureView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private var mMaxTemp = 0
    private var mMinTemp = 0
    private var mTemp = 0
    private lateinit var mPointPaint: Paint
    private lateinit var mTextPaint: Paint
    private var mPointColor = 0
    private var mTextColor = 0
    private var mRadius = 6F
    private var mTextSize = 26F
    private var mXPoint = 0F
    private var mYPoint = 0F
    private var mWidth = 0

    init {
        init()
    }

    private fun init() {
        mTextColor = 0xffffffff.toInt()
        mPointColor = 0xffffffff.toInt()
        mPointPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPointPaint.color = mPointColor
        mTextPaint.color = mTextColor
        mTextPaint.textSize = mTextSize
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawPoint(canvas)
        drawText(canvas)
    }

    private fun drawPoint(canvas: Canvas?) {
        var height = height
        var x = width / 2
        var y =
            (height - height * (mTemp - mMinTemp) * 1.0F / (mMaxTemp - mMinTemp)) + mTextSize * 2
        mXPoint = x.toFloat()
        mYPoint = y
        mWidth = width
        canvas?.let { it ->
            it.drawCircle(mXPoint, mYPoint, mRadius, mPointPaint)
        }
    }

    private fun drawText(canvas: Canvas?) {
        val height = height - mTextSize * 4
        val y =
            (height - height * (mTemp - mMinTemp) * 1.0f / (mMaxTemp - mMinTemp)) + mTextSize * 2
        val dayTemp: String = mTemp.toString() + "°C"
        val widDay: Float = mTextPaint.measureText(dayTemp)
        val hei: Float = mTextPaint.descent() - mTextPaint.ascent()
        canvas?.let { it ->
            it.drawText(dayTemp, width / 2 - widDay / 2, y - mRadius - hei / 2, mTextPaint)
        }
    }

    fun setRadius(radius: Float) {
        this.mRadius = radius
        invalidate()
    }

    fun setMaxTemp(maxTemp: Int) {
        this.mMaxTemp = maxTemp
    }

    fun setMinTemp(minTemp: Int) {
        this.mMinTemp = minTemp
    }

    fun getXPoint() = mXPoint

    fun getYPoint() = mYPoint

    fun setTemp(temp : Int) {
        this.mTemp = temp
    }
    fun getTemp() = mTemp
}