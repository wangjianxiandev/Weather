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
 * @CreateDate: 2020/6/11 22:38
 */
class MyTemperatureView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) :
    View(context, attrs) {
    private var maxTemp = 0
    private var minTemp = 0
    private var temperatureDay = 0
    private var temperatureNight = 0
    private var pointPaint: Paint? = null
    private var linePaint: Paint? = null
    private var textPaint: Paint? = null
    private var lineColor = 0
    private var pointColor = 0
    private var textColor = 0
    private var radius = 6
    private val textSize = 26
    private var xPointDay = 0
    private var yPointDay = 0
    private var xPointNight = 0
    private var yPointNight = 0
    private var mWidth = 0

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : this(context, attrs) {
    }

    private fun init(
        context: Context?,
        attrs: AttributeSet?
    ) {
        initAttrs(context, attrs)
        initPaint(context, attrs)
    }

    private fun initAttrs(
        context: Context?,
        attrs: AttributeSet?
    ) {
        lineColor = -0x6c5ede
        textColor = -0x1
        pointColor = -0x1
    }

    private fun initPaint(
        context: Context?,
        attrs: AttributeSet?
    ) {
        pointPaint = Paint()
        linePaint = Paint()
        textPaint = Paint()
        linePaint!!.color = lineColor
        pointPaint!!.color = pointColor
        pointPaint!!.isAntiAlias = true
        textPaint!!.color = textColor
        textPaint!!.textSize = textSize.toFloat()
        textPaint!!.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawPoint(canvas)
        drawText(canvas)
    }

    private fun drawPoint(canvas: Canvas) {
        val height = height - textSize * 4
        val x = width / 2
        val y =
            (height - height * (temperatureDay - minTemp) * 1.0f / (maxTemp - minTemp)).toInt() + textSize * 2
        val x2 = width / 2
        val y2 =
            (height - height * (temperatureNight - minTemp) * 1.0f / (maxTemp - minTemp)).toInt() + textSize * 2
        xPointDay = x
        yPointDay = y
        xPointNight = x2
        yPointNight = y2
        mWidth = width
        canvas.drawCircle(x.toFloat(), y.toFloat(), radius.toFloat(), pointPaint!!)
        canvas.drawCircle(x2.toFloat(), y2.toFloat(), radius.toFloat(), pointPaint!!)
    }

    private fun drawText(canvas: Canvas) {
        val height = height - textSize * 4
        val yDay =
            (height - height * (temperatureDay - minTemp) * 1.0f / (maxTemp - minTemp)).toInt() + textSize * 2
        val yNight =
            (height - height * (temperatureNight - minTemp) * 1.0f / (maxTemp - minTemp)).toInt() + textSize * 2
        val dayTemp = "$temperatureDay°"
        val nightTemp = "$temperatureNight°"
        val widDay = textPaint!!.measureText(dayTemp)
        val widNight = textPaint!!.measureText(nightTemp)
        val hei = textPaint!!.descent() - textPaint!!.ascent()
        canvas.drawText(
            dayTemp, width / 2 - widDay / 2, yDay - radius - hei / 2,
            textPaint!!
        )
        canvas.drawText(
            nightTemp, width / 2 - widNight / 2, yNight + radius + hei,
            textPaint!!
        )
    }

    fun getRadius(): Int {
        return radius
    }

    fun setRadius(radius: Int) {
        this.radius = radius
        invalidate()
    }

    fun getMaxTemp(): Int {
        return maxTemp
    }

    fun setMaxTemp(maxTemp: Int) {
        this.maxTemp = maxTemp
    }

    fun getMinTemp(): Int {
        return minTemp
    }

    fun setMinTemp(minTemp: Int) {
        this.minTemp = minTemp
    }

    fun getxPointDay(): Int {
        return xPointDay
    }

    fun getyPointDay(): Int {
        return yPointDay
    }

    fun setxPointDay(xPointDay: Int) {
        this.xPointDay = xPointDay
    }

    fun setyPointDay(yPointDay: Int) {
        this.yPointDay = yPointDay
    }

    fun getxPointNight(): Int {
        return xPointNight
    }

    fun setxPointNight(xPointNight: Int) {
        this.xPointNight = xPointNight
    }

    fun getyPointNight(): Int {
        return yPointNight
    }

    fun setyPointNight(yPointNight: Int) {
        this.yPointNight = yPointNight
    }

    fun getmWidth(): Int {
        return mWidth
    }

    fun getTemperatureDay(): Int {
        return temperatureDay
    }

    fun setTemperatureDay(temperatureDay: Int) {
        this.temperatureDay = temperatureDay
    }

    fun getLineColor(): Int {
        return lineColor
    }

    fun setLineColor(lineColor: Int) {
        this.lineColor = lineColor
    }

    fun getPointColor(): Int {
        return pointColor
    }

    fun setPointColor(pointColor: Int) {
        this.pointColor = pointColor
    }

    fun getTextColor(): Int {
        return textColor
    }

    fun setTextColor(textColor: Int) {
        this.textColor = textColor
    }

    fun getTemperatureNight(): Int {
        return temperatureNight
    }

    fun setTemperatureNight(temperatureNight: Int) {
        this.temperatureNight = temperatureNight
    }

    init {
        init(context, attrs)
    }
}
