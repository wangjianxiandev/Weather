package com.wjx.android.weather.common.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.wjx.android.weather.R

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/11 22:39
 */
class MyWeatherItemView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) :
    LinearLayout(context, attrs) {
    private var mRootView: View? = null
    private var mTvWeek: TextView? = null
    private var mTvDate: TextView? = null
    private var mTvDayWeather: TextView? = null
    private var mTvNightWeather: TextView? = null
    private var mTvTemp: MyTemperatureView? = null
    private var mTvWindOri: TextView? = null
    private var mTvWindLevel: TextView? = null
    private var mTvAirLevel: TextView? = null
    private var ivDayWeather: ImageView? = null
    private var ivNightWeather: ImageView? = null

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
        mRootView = LayoutInflater.from(context).inflate(R.layout.ic_weather_item, null)
        mTvWeek = mRootView!!.findViewById<View>(R.id.tv_week) as TextView
        mTvDate = mRootView!!.findViewById<View>(R.id.tv_date) as TextView
        mTvDayWeather = mRootView!!.findViewById<View>(R.id.tv_day_weather) as TextView
        mTvNightWeather =
            mRootView!!.findViewById<View>(R.id.tv_night_weather) as TextView
        mTvTemp = mRootView!!.findViewById<View>(R.id.ttv_day) as MyTemperatureView
        mTvWindOri = mRootView!!.findViewById<View>(R.id.tv_wind_ori) as TextView
        mTvWindLevel = mRootView!!.findViewById<View>(R.id.tv_wind_level) as TextView
        ivDayWeather =
            mRootView!!.findViewById<View>(R.id.iv_day_weather) as ImageView
        ivNightWeather =
            mRootView!!.findViewById<View>(R.id.iv_night_weather) as ImageView
        mTvAirLevel = mRootView!!.findViewById<View>(R.id.tv_air_level) as TextView
        mRootView!!.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        addView(mRootView)
    }

    fun setWeek(week: String?) {
        if (mTvWeek != null) mTvWeek!!.text = week
    }

    fun setDate(date: String?) {
        if (mTvDate != null) mTvDate!!.text = date
    }

    fun getTempX(): Int {
        return if (mTvTemp != null) mTvTemp!!.x.toInt() else 0
    }

    fun getTempY(): Int {
        return if (mTvTemp != null) mTvTemp!!.y.toInt() else 0
    }

    fun setDayWeather(dayWeather: String?) {
        if (mTvDayWeather != null) mTvDayWeather!!.text = dayWeather
    }

    fun setNightWeather(nightWeather: String?) {
        if (mTvNightWeather != null) mTvNightWeather!!.text = nightWeather
    }

    fun setWindOri(windOri: String?) {
        if (mTvWindOri != null) mTvWindOri!!.text = windOri
    }

    fun setWindLevel(windLevel: String?) {
        if (mTvWindLevel != null) mTvWindLevel!!.text = windLevel
    }
    
    fun setDayTemp(dayTemp: Int) {
        if (mTvTemp != null) mTvTemp!!.setTemperatureDay(dayTemp)
    }

    fun setNightTemp(nightTemp: Int) {
        if (mTvTemp != null) mTvTemp!!.setTemperatureNight(nightTemp)
    }

    fun setDayImg(resId: Int) {
        if (ivDayWeather != null) ivDayWeather!!.setImageResource(resId)
    }

    fun setNightImg(resId: Int) {
        if (ivNightWeather != null) ivNightWeather!!.setImageResource(resId)
    }

    fun setMaxTemp(max: Int) {
        if (mTvTemp != null) mTvTemp!!.setMaxTemp(max)
    }

    fun setMinTemp(min: Int) {
        if (mTvTemp != null) {
            mTvTemp!!.setMinTemp(min)
        }
    }

    init {
        init(context, attrs)
    }
}
