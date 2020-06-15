package com.wjx.android.weather.common.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/15 8:35
 */
class TemperatureView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private var mMaxTemp = 0
    private var mMinTemp = 0
    private var mTemp = 0.0
    private lateinit var mPointPaint: Paint
    private lateinit var mLinePaint: Paint
    private lateinit var mTextPaint: Paint
    private var mLineColor = 0
    private var mPointColor = 0
    private var mTextColor = 0
    private var mRadius = 6F
    private var mTextSize = 26
    private var mXPoint = 0
    private var mYPoint = 0
    private var mWidth = 0

    init {
        init()
    }

    private fun init() {
        mLineColor = -0x6c5ede
        mTextColor = -0x1
        mPointColor = -0x1
        mPointPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mLinePaint = Paint()
        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        mLinePaint.color = mLineColor
        mPointPaint.color = mPointColor
        mTextPaint.color = mTextColor
        mTextPaint.textSize = mTextSize.toFloat()
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
            ((height - height * (mTemp - mMinTemp) * 1.0F / (mMaxTemp - mMinTemp)) + mTextSize * 2).toInt()
        mXPoint = x
        mYPoint = y
        mWidth = width
        canvas?.let { it ->
            it.drawCircle(x.toFloat(), y.toFloat(), mRadius, mPointPaint)
        }
    }

    private fun drawText(canvas: Canvas?) {
        val height: Int = height
        val y =
            (height - height * (mTemp - mMinTemp) * 1.0f / (mMaxTemp - mMinTemp)) + mTextSize * 2
        val dayTemp: String = mTemp.toString() + "°"
        val widDay: Float = mTextPaint.measureText(dayTemp)
        val hei: Float = mTextPaint.descent() - mTextPaint.ascent()
        canvas?.let { it ->
            it.drawText(dayTemp, width / 2 - widDay / 2, (y - mRadius - hei / 2).toFloat(), mTextPaint)
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

    fun setTemp(temp : Double) {
        this.mTemp = temp
    }
    fun getTemp() = mTemp
}