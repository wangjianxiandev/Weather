package com.wjx.android.weather.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/15 10:08
 */
data class HourlyWeather(
    val temp: Double,
    val weather: String,
    val time: String,
    val weatherImg: Int,
    val windOri: Double,
    val windLevel: Double
//    val airLevel: String
)