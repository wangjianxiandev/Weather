package com.wjx.android.weather.common.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.wjx.android.weather.R
import com.wjx.android.weather.common.util.getSky
import com.wjx.android.weather.model.HourlyWeather
import java.util.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/15 8:27
 */
class WeatherView :
    HorizontalScrollView {
    private lateinit var mPaint: Paint
    private lateinit var mPath: Path

    private lateinit var mHourlyWeatherList: ArrayList<HourlyWeather>

    private var mLineWidth = 6f

    private var mLineColor = R.color.always_white_text

    private var mColumnNumber = 6

    private lateinit var onWeatherItemClickListener: OnWeatherItemClickListener

    constructor(mContext: Context) : super(mContext, null)

    constructor(mContext: Context, attrs: AttributeSet) : super(mContext, attrs, 0) {
        init(mContext, attrs)
    }

    constructor(mContext: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        mContext,
        attrs,
        defStyleAttr
    ) {
        init(mContext, attrs)
    }

    private fun init(context: Context, attributeSet: AttributeSet) {
        mPaint = Paint()
        mPaint.setColor(context.getColor(mLineColor))
        mPaint.isAntiAlias = true
        mPaint.strokeWidth = mLineWidth
        mPaint.style = Paint.Style.STROKE
        mPath = Path()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (childCount > 0) {
            var root = getChildAt(0) as ViewGroup
            if (root.childCount > 0) {
                var hourlyWeatherItem = root.getChildAt(0) as HourlyWeatherItem
                val dX: Int = hourlyWeatherItem.getTempX().toInt()
                val dY: Int = hourlyWeatherItem.getTempY().toInt()
                val temperatureView =
                    hourlyWeatherItem.findViewById<View>(R.id.hourly_temp) as TemperatureView
                temperatureView.setRadius(10F)
                val x = dX + temperatureView.getXPoint()
                val y = dY + temperatureView.getYPoint()
                mPath.reset()
                mPath.moveTo(x.toFloat(), y.toFloat())

                //折线
                for (i in 0 until root.childCount - 1) {
                    val child: HourlyWeatherItem = root.getChildAt(i) as HourlyWeatherItem
                    val child1: HourlyWeatherItem = root.getChildAt(i + 1) as HourlyWeatherItem
                    val dayX = (child.getTempX() + child.getWidth() * i).toInt()
                    val dayY = child.getTempY().toInt()
                    val dayX1 = (child1.getTempX() + child1.getWidth() * (i + 1)).toInt()
                    val dayY1 = child1.getTempY().toInt()
                    val tempV = child.findViewById<View>(R.id.hourly_temp) as TemperatureView
                    val tempV1 = child1.findViewById<View>(R.id.hourly_temp) as TemperatureView
                    tempV.setRadius(10F)
                    tempV1.setRadius(10F)
                    val x1 = (dayX + tempV.getXPoint()).toInt()
                    val y1 = (dayY + tempV.getYPoint()).toInt()
                    val x11 = (dayX1 + tempV1.getXPoint()).toInt()
                    val y11 = (dayY1 + tempV1.getYPoint()).toInt()
                    canvas!!.drawLine(
                        x1.toFloat(),
                        y1.toFloat(),
                        x11.toFloat(),
                        y11.toFloat(),
                        mPaint
                    )
                    invalidate()
                }
            }
        }
    }

    fun setLineWidth(lineWidth: Float) {
        mLineWidth = lineWidth
        mPaint.strokeWidth = lineWidth
        invalidate()
    }

    fun setOnWeatherItemClickListener(weatherItemClickListener: OnWeatherItemClickListener) {
        onWeatherItemClickListener = weatherItemClickListener
    }

    private fun getScreenWidth(): Int {
        val wm = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }

    private class TempComparator : Comparator<HourlyWeather> {
        override fun compare(o1: HourlyWeather, o2: HourlyWeather): Int {
            return if (o1.temp === o2.temp) {
                0
            } else if (o1.temp > o2.temp) {
                1
            } else {
                -1
            }
        }
    }

    private fun getMaxTemp(list: ArrayList<HourlyWeather>?): Int {
        return (if (list != null) {
            Collections.max<HourlyWeather>(
                list, TempComparator()
            ).temp
        } else 0)
    }

    private fun getMinTemp(list: ArrayList<HourlyWeather>?): Int {
        return (if (list != null) {
            Collections.min<HourlyWeather>(
                list, TempComparator()
            ).temp
        } else 0)
    }

    @Throws(Exception::class)
    fun setColumnNumber(num: Int) {
        if (num > 2) {
            this.mColumnNumber = num
            setList(this.mHourlyWeatherList)
        } else {
            throw Exception("ColumnNumber should lager than 2")
        }
    }

    fun setList(list: ArrayList<HourlyWeather>) {
        this.mHourlyWeatherList = list
        var screenWidth = getScreenWidth()
        var max = getMaxTemp(list).toInt()
        var min = getMinTemp(list).toInt()
        removeAllViews()
        val llRoot = LinearLayout(context)
        llRoot.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        llRoot.orientation = LinearLayout.HORIZONTAL
        for (i in 0 until list.size) {
            val model: HourlyWeather = list[i]
            val itemView = HourlyWeatherItem(context)
            itemView.setMaxTemp(max)
            itemView.setMinTemp(min)
            itemView.setTime(model.time)
            itemView.setTemp(model.temp)
            itemView.setWeather(model.weather)
            itemView.setImg(getSky(model.skycon.value).icon)
            itemView.setWindOri(model.windOri)
            itemView.setWindLevel(model.windLevel)
            itemView.setAirLevel(model.airLevel)
            itemView.setLayoutParams(
                LinearLayout.LayoutParams(
                    screenWidth / mColumnNumber,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            itemView.setClickable(true)
            itemView.setOnClickListener(OnClickListener {
                if (onWeatherItemClickListener != null) {
                    onWeatherItemClickListener.onItemClick(itemView, i, list[i])
                }
            })
            llRoot.addView(itemView)
        }
        addView(llRoot)
        invalidate()
    }

    open interface OnWeatherItemClickListener {
        fun onItemClick(
            itemView: HourlyWeatherItem?,
            position: Int,
            hourlyWeather: HourlyWeather?
        )
    }

}