package com.wjx.android.weather.common.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.wjx.android.weather.R
import com.wjx.android.weather.common.util.getSky
import com.wjx.android.weather.model.Hourly

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/11 22:50
 */
class MyWeatherView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) :
    HorizontalScrollView(context, attrs) {
    private var list: Hourly? = null
    private var dayPaint: Paint? = null
    private var nightPaint: Paint? = null
    protected var pathDay: Path? = null
    protected var pathNight: Path? = null
    private var lineType = LINE_TYPE_CURVE
    private var lineWidth = 6f
    private var dayLineColor = -0x8752dd
    private var nightLineColor = -0xdc534d
    private var columnNumber = 6
    private var weatherItemClickListener: OnWeatherItemClickListener? = null

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
        dayPaint = Paint()
        dayPaint!!.color = dayLineColor
        dayPaint!!.isAntiAlias = true
        dayPaint!!.strokeWidth = lineWidth
        dayPaint!!.style = Paint.Style.STROKE
        nightPaint = Paint()
        nightPaint!!.color = nightLineColor
        nightPaint!!.isAntiAlias = true
        nightPaint!!.strokeWidth = lineWidth
        nightPaint!!.style = Paint.Style.STROKE
        pathDay = Path()
        pathNight = Path()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (childCount > 0) {
            val root = getChildAt(0) as ViewGroup
            if (root.childCount > 0) {
                val prevDx = 0f
                val prevDy = 0f
                val curDx = 0f
                val curDy = 0f
                val prevDx1 = 0f
                val prevDy1 = 0f
                val curDx1 = 0f
                val curDy1 = 0f
                val intensity = 0.16f
                val c = root.getChildAt(0) as MyWeatherItemView
                val dX = c.getTempX()
                val dY = c.getTempY()
                val nX = c.getTempX()
                val nY = c.getTempY()
                val tv = c.findViewById(R.id.ttv_day) as MyTemperatureView
                tv.setRadius(10)
                pathDay!!.reset()
                pathNight!!.reset()
                pathDay!!.moveTo(
                    (dX + tv.getxPointDay()).toFloat(),
                    (dY + tv.getyPointDay()).toFloat()
                )
                pathNight!!.moveTo(
                    (nX + tv.getxPointNight()).toFloat(),
                    (nY + tv.getyPointNight()).toFloat()
                )
                if (lineType == LINE_TYPE_CURVE) {
                    val lineSize = root.childCount

                    //曲线
                    var prePreviousPointX = Float.NaN
                    var prePreviousPointY = Float.NaN
                    var previousPointX = Float.NaN
                    var previousPointY = Float.NaN
                    var currentPointX = Float.NaN
                    var currentPointY = Float.NaN
                    var nextPointX = Float.NaN
                    var nextPointY = Float.NaN
                    var prePreviousPointX1 = Float.NaN
                    var prePreviousPointY1 = Float.NaN
                    var previousPointX1 = Float.NaN
                    var previousPointY1 = Float.NaN
                    var currentPointX1 = Float.NaN
                    var currentPointY1 = Float.NaN
                    var nextPointX1 = Float.NaN
                    var nextPointY1 = Float.NaN
                    for (i in 0 until lineSize) {

                        //Day
                        if (java.lang.Float.isNaN(currentPointX)) {
                            val curWI = root.getChildAt(i) as MyWeatherItemView
                            val dayX = curWI.getTempX() + curWI.width * i
                            val dayY = curWI.getTempY()
                            val nightX = curWI.getTempX() + curWI.width * i
                            val nightY = curWI.getTempY()
                            val tempV =
                                curWI.findViewById(R.id.ttv_day) as MyTemperatureView
                            tempV.setRadius(10)

                            //day2
                            currentPointX = (dayX + tempV.getxPointDay()).toFloat()
                            currentPointY = (dayY + tempV.getyPointDay()).toFloat()
                            //night2
                            val x2 = (nightX + tempV.getxPointNight())
                            val y2 = (nightY + tempV.getyPointNight())
                        }
                        if (java.lang.Float.isNaN(previousPointX)) {
                            if (i > 0) {
                                val preWI =
                                    root.getChildAt(Math.max(i - 1, 0)) as MyWeatherItemView
                                val dayX0 = preWI.getTempX() + preWI.width * (i - 1)
                                val dayY0 = preWI.getTempY()
                                val nightX0 = preWI.getTempX() + preWI.width * (i - 1)
                                val nightY0 = preWI.getTempY()
                                val tempV0 =
                                    preWI.findViewById(R.id.ttv_day) as MyTemperatureView
                                tempV0.setRadius(10)


                                //day1
                                previousPointX = (dayX0 + tempV0.getxPointDay()).toFloat()
                                previousPointY = (dayY0 + tempV0.getyPointDay()).toFloat()

                                //night1
                                val x02 = (nightX0 + tempV0.getxPointNight())
                                val y02 = (nightY0 + tempV0.getyPointNight())
                            } else {
                                previousPointX = currentPointX
                                previousPointY = currentPointY
                            }
                        }
                        if (java.lang.Float.isNaN(prePreviousPointX)) {
                            if (i > 1) {
                                val preWI =
                                    root.getChildAt(Math.max(i - 2, 0)) as MyWeatherItemView
                                val dayX0 = preWI.getTempX() + preWI.width * (i - 2)
                                val dayY0 = preWI.getTempY()
                                val nightX0 = preWI.getTempX() + preWI.width * (i - 2)
                                val nightY0 = preWI.getTempY()
                                val tempV0 =
                                    preWI.findViewById(R.id.ttv_day) as MyTemperatureView
                                tempV0.setRadius(10)

                                //pre pre day
                                prePreviousPointX = (dayX0 + tempV0.getxPointDay()).toFloat()
                                prePreviousPointY = (dayY0 + tempV0.getyPointDay()).toFloat()
                            } else {
                                prePreviousPointX = previousPointX
                                prePreviousPointY = previousPointY
                            }
                        }

                        // nextPoint is always new one or it is equal currentPoint.
                        if (i < lineSize - 1) {
                            val nextWI = root.getChildAt(
                                Math.min(
                                    root.childCount - 1,
                                    i + 1
                                )
                            ) as MyWeatherItemView
                            val dayX1 = nextWI.getTempX() + nextWI.width * (i + 1)
                            val dayY1 = nextWI.getTempY()
                            val nightX1 = nextWI.getTempX() + nextWI.width * (i + 1)
                            val nightY1 = nextWI.getTempY()
                            val tempV1 =
                                nextWI.findViewById(R.id.ttv_day) as MyTemperatureView
                            tempV1.setRadius(10)
                            //day3
                            nextPointX = (dayX1 + tempV1.getxPointDay()).toFloat()
                            nextPointY = (dayY1 + tempV1.getyPointDay()).toFloat()
                            //night3
                            val x22 = (nightX1 + tempV1.getxPointNight())
                            val y22 = (nightY1 + tempV1.getyPointNight())
                        } else {
                            nextPointX = currentPointX
                            nextPointY = currentPointY
                        }
                        /*****************************Night */
                        if (java.lang.Float.isNaN(currentPointX1)) {
                            val curWI = root.getChildAt(i) as MyWeatherItemView
                            val nightX = curWI.getTempX() + curWI.width * i
                            val nightY = curWI.getTempY()
                            val tempV =
                                curWI.findViewById(R.id.ttv_day) as MyTemperatureView
                            tempV.setRadius(10)

                            //night2
                            currentPointX1 = (nightX + tempV.getxPointNight()).toFloat()
                            currentPointY1 = (nightY + tempV.getyPointNight()).toFloat()
                        }
                        if (java.lang.Float.isNaN(previousPointX1)) {
                            if (i > 0) {
                                val preWI =
                                    root.getChildAt(Math.max(i - 1, 0)) as MyWeatherItemView
                                val nightX0 = preWI.getTempX() + preWI.width * (i - 1)
                                val nightY0 = preWI.getTempY()
                                val tempV0 =
                                    preWI.findViewById(R.id.ttv_day) as MyTemperatureView
                                tempV0.setRadius(10)

                                //night
                                previousPointX1 =
                                    (nightX0 + tempV0.getxPointNight()).toFloat()
                                previousPointY1 =
                                    (nightY0 + tempV0.getyPointNight()).toFloat()
                            } else {
                                previousPointX1 = currentPointX1
                                previousPointY1 = currentPointY1
                            }
                        }
                        if (java.lang.Float.isNaN(prePreviousPointX1)) {
                            if (i > 1) {
                                val preWI =
                                    root.getChildAt(Math.max(i - 2, 0)) as MyWeatherItemView
                                val nightX0 = preWI.getTempX() + preWI.width * (i - 2)
                                val nightY0 = preWI.getTempY()
                                val tempV0 =
                                    preWI.findViewById(R.id.ttv_day) as MyTemperatureView
                                tempV0.setRadius(10)

                                //pre pre day
                                prePreviousPointX1 =
                                    (nightX0 + tempV0.getxPointNight()).toFloat()
                                prePreviousPointY1 =
                                    (nightY0 + tempV0.getyPointNight()).toFloat()
                            } else {
                                prePreviousPointX1 = previousPointX1
                                prePreviousPointY1 = previousPointY1
                            }
                        }

                        // nextPoint is always new one or it is equal currentPoint.
                        if (i < lineSize - 1) {
                            val nextWI = root.getChildAt(
                                Math.min(
                                    root.childCount - 1,
                                    i + 1
                                )
                            ) as MyWeatherItemView
                            val nightX1 = nextWI.getTempX() + nextWI.width * (i + 1)
                            val nightY1 = nextWI.getTempY()
                            val tempV1 =
                                nextWI.findViewById(R.id.ttv_day) as MyTemperatureView
                            tempV1.setRadius(10)
                            //night3
                            nextPointX1 = (nightX1 + tempV1.getxPointNight()).toFloat()
                            nextPointY1 = (nightY1 + tempV1.getyPointNight()).toFloat()
                        } else {
                            nextPointX1 = currentPointX1
                            nextPointY1 = currentPointY1
                        }

                        //Day and Night
                        if (i == 0) {
                            // Move to start point.
                            pathDay!!.moveTo(currentPointX, currentPointY)
                            pathNight!!.moveTo(currentPointX1, currentPointY1)
                        } else {
                            // Calculate control points.
                            val firstDiffX = currentPointX - prePreviousPointX
                            val firstDiffY = currentPointY - prePreviousPointY
                            val secondDiffX = nextPointX - previousPointX
                            val secondDiffY = nextPointY - previousPointY
                            val firstControlPointX =
                                previousPointX + intensity * firstDiffX
                            val firstControlPointY =
                                previousPointY + intensity * firstDiffY
                            val secondControlPointX =
                                currentPointX - intensity * secondDiffX
                            val secondControlPointY =
                                currentPointY - intensity * secondDiffY
                            pathDay!!.cubicTo(
                                firstControlPointX,
                                firstControlPointY,
                                secondControlPointX,
                                secondControlPointY,
                                currentPointX,
                                currentPointY
                            )
                            val firstDiffX1 = currentPointX1 - prePreviousPointX1
                            val firstDiffY1 = currentPointY1 - prePreviousPointY1
                            val secondDiffX1 = nextPointX1 - previousPointX1
                            val secondDiffY1 = nextPointY1 - previousPointY1
                            val firstControlPointX1 =
                                previousPointX1 + intensity * firstDiffX1
                            val firstControlPointY1 =
                                previousPointY1 + intensity * firstDiffY1
                            val secondControlPointX1 =
                                currentPointX1 - intensity * secondDiffX1
                            val secondControlPointY1 =
                                currentPointY1 - intensity * secondDiffY1
                            pathNight!!.cubicTo(
                                firstControlPointX1,
                                firstControlPointY1,
                                secondControlPointX1,
                                secondControlPointY1,
                                currentPointX1,
                                currentPointY1
                            )
                        }

                        // Shift values by one back to prevent recalculation of values that have
                        // been already calculated.
                        prePreviousPointX = previousPointX
                        prePreviousPointY = previousPointY
                        previousPointX = currentPointX
                        previousPointY = currentPointY
                        currentPointX = nextPointX
                        currentPointY = nextPointY
                        prePreviousPointX1 = previousPointX1
                        prePreviousPointY1 = previousPointY1
                        previousPointX1 = currentPointX1
                        previousPointY1 = currentPointY1
                        currentPointX1 = nextPointX1
                        currentPointY1 = nextPointY1
                    }
                    canvas.drawPath(pathDay!!, dayPaint!!)
                    canvas.drawPath(pathNight!!, nightPaint!!)
                } else {
                    //折线
                    for (i in 0 until root.childCount - 1) {
                        val child = root.getChildAt(i) as MyWeatherItemView
                        val child1 = root.getChildAt(i + 1) as MyWeatherItemView
                        val dayX = child.getTempX() + child.width * i
                        val dayY = child.getTempY()
                        val nightX = child.getTempX() + child.width * i
                        val nightY = child.getTempY()
                        val dayX1 = child1.getTempX() + child1.width * (i + 1)
                        val dayY1 = child1.getTempY()
                        val nightX1 = child1.getTempX() + child1.width * (i + 1)
                        val nightY1 = child1.getTempY()
                        val tempV =
                            child.findViewById(R.id.ttv_day) as MyTemperatureView
                        val tempV1 =
                            child1.findViewById(R.id.ttv_day) as MyTemperatureView
                        tempV.setRadius(10)
                        tempV1.setRadius(10)
                        canvas.drawLine(
                            (dayX + tempV.getxPointDay()).toFloat(),
                            (dayY + tempV.getyPointDay()).toFloat(),
                            (dayX1 + tempV1.getxPointDay()).toFloat(),
                            (dayY1 + tempV1.getyPointDay()).toFloat(),
                            dayPaint!!
                        )
                        canvas.drawLine(
                            (nightX + tempV.getxPointNight()).toFloat(),
                            (nightY + tempV.getyPointNight()).toFloat(),
                            (nightX1 + tempV1.getxPointNight()).toFloat(),
                            (nightY1 + tempV1.getyPointNight()).toFloat(),
                            nightPaint!!
                        )
                    }
                }
            }
        }
    }

    fun getLineWidth(): Float {
        return lineWidth
    }

    fun setLineWidth(lineWidth: Float) {
        this.lineWidth = lineWidth
        dayPaint!!.strokeWidth = lineWidth
        nightPaint!!.strokeWidth = lineWidth
        invalidate()
    }

    fun setDayLineColor(color: Int) {
        dayLineColor = color
        dayPaint!!.color = dayLineColor
        invalidate()
    }

    fun setNightLineColor(color: Int) {
        nightLineColor = color
        nightPaint!!.color = nightLineColor
        invalidate()
    }

    fun setDayAndNightLineColor(dayColor: Int, nightColor: Int) {
        dayLineColor = dayColor
        nightLineColor = nightColor
        dayPaint!!.color = dayLineColor
        nightPaint!!.color = nightLineColor
        invalidate()
    }

    fun getList(): Hourly? {
        return list
    }

    fun setOnWeatherItemClickListener(weatherItemClickListener: OnWeatherItemClickListener?) {
        this.weatherItemClickListener = weatherItemClickListener
    }

    fun setList(list: Hourly?) {
        this.list = list
        removeAllViews()
        val llRoot = LinearLayout(context)
        llRoot.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        llRoot.orientation = LinearLayout.HORIZONTAL
        for (i in 0 until list?.skycon?.size!!) {
            val itemView = MyWeatherItemView(context)
            itemView.setDate(list.skycon[i].datetime)
            itemView.setDayTemp(list.temperature[i].value.toInt())
            itemView.setDayWeather(list.skycon[i].value)
            itemView.setDayImg(getSky(list.skycon[i].value).icon)
            itemView.isClickable = true
            itemView.setOnClickListener {

            }
            llRoot.addView(itemView)
        }
        addView(llRoot)
        invalidate()
    }

    @Throws(Exception::class)
    fun setColumnNumber(num: Int) {
        if (num > 2) {
            columnNumber = num
            setList(list)
        } else {
            throw Exception("ColumnNumber should lager than 2")
        }
    }

    interface OnWeatherItemClickListener {
        fun onItemClick(
            itemView: MyWeatherItemView?,
            position: Int,
            Hourly: Hourly?
        )
    }

    companion object {
        const val LINE_TYPE_CURVE = 1 //曲线
        const val LINE_TYPE_DISCOUNT = 2 //折线
    }

    init {
        init(context, attrs)
    }
}

