package com.wjx.android.weather.common.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.wjx.android.weather.R

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/15 8:31
 */
class HourlyWeatherItem(context: Context) :
    LinearLayout(context) {
    private lateinit var mRootView: View
    private lateinit var mTimeText: TextView
    private lateinit var mWeather: TextView
    private lateinit var mTemperatureView: TemperatureView
    private lateinit var mWindOri: TextView
    private lateinit var mWIndLevel: TextView
    private lateinit var mAirLevel: TextView
    private lateinit var mWeatherImage: ImageView

    init {
        init(context)
    }

    private fun init(context: Context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.hourly_item, null)
        mTimeText = mRootView.findViewById(R.id.hourly_time)
        mWeather = mRootView.findViewById(R.id.hourly_weather)
        mTemperatureView = mRootView.findViewById(R.id.hourly_temp)
        mWindOri = mRootView.findViewById(R.id.hourly_wind_ori)
        mWIndLevel = mRootView.findViewById(R.id.hourly_wind_level)
        mAirLevel = mRootView.findViewById(R.id.hourly_air_level)
        mWeatherImage = mRootView.findViewById(R.id.hourly_weather_img)
        addView(mRootView)
    }

    fun setTime(time: String?) {
        mTimeText?.text = time
    }

    fun getTempX(): Float {
        return if (mTemperatureView != null) mTemperatureView.x else 0F
    }

    fun getTempY(): Float {
        return if (mTemperatureView != null) mTemperatureView.y else 0F
    }

    fun setWeather(weather: String?) {
        mWeather?.text = weather
    }

    fun setWindOri(windOri: Double) {
        mWindOri?.text = windOri.toString()
    }

    fun setWindLevel(windLevel: Double) {
        mWIndLevel?.text = windLevel.toString()
    }

//    fun setAirLevel(airLevel: AirLevel?) {
//        if (tvAirLevel != null) {
//            when (airLevel) {
//                EXCELLENT -> {
//                    tvAirLevel.setBackgroundResource(R.drawable.best_level_shape)
//                    tvAirLevel.setText("优")
//                }
//                GOOD -> {
//                    tvAirLevel.setBackgroundResource(R.drawable.good_level_shape)
//                    tvAirLevel.setText("良好")
//                }
//                LIGHT -> {
//                    tvAirLevel.setText("轻度")
//                    tvAirLevel.setBackgroundResource(R.drawable.small_level_shape)
//                }
//                MIDDLE -> {
//                    tvAirLevel.setBackgroundResource(R.drawable.mid_level_shape)
//                    tvAirLevel.setText("中度")
//                }
//                HIGH -> {
//                    tvAirLevel.setBackgroundResource(R.drawable.big_level_shape)
//                    tvAirLevel.setText("重度")
//                }
//                POISONOUS -> {
//                    tvAirLevel.setBackgroundResource(R.drawable.poison_level_shape)
//                    tvAirLevel.setText("有毒")
//                }
//            }
//        }
//    }

    fun setTemp(temp: Double) {
        mTemperatureView?.setTemp(temp)
    }

    fun setImg(resId: Int) {
        mWeatherImage?.setImageResource(resId)
    }

    fun setMaxTemp(max: Int) {
        mTemperatureView?.setMaxTemp(max)
    }

    fun setMinTemp(min: Int) {
        mTemperatureView?.setMinTemp(min)
    }
}