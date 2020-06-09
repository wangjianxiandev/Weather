package com.wjx.android.weather.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/9 14:28
 */
data class HourlyResponse(
    val api_status: String,
    val api_version: String,
    val lang: String,
    val location: List<Double>,
    val result: Result,
    val server_time: Int,
    val status: String,
    val timezone: String,
    val tzshift: Int,
    val unit: String
)

data class Result(
    val forecast_keypoint: String,
    val hourly: Hourly,
    val primary: Int
)

data class Hourly(
    val air_quality: AirQuality,
    val cloudrate: List<Cloudrate>,
    val description: String,
    val dswrf: List<Dswrf>,
    val humidity: List<Humidity>,
    val precipitation: List<Precipitation>,
    val pressure: List<Pressure>,
    val skycon: List<Skycon>,
    val status: String,
    val temperature: List<Temperature>,
    val visibility: List<Visibility>,
    val wind: List<Wind>
)

data class AirQuality(
    val aqi: List<Aqi>,
    val pm25: List<Pm25>
)

data class Cloudrate(
    val datetime: String,
    val value: Double
)

data class Dswrf(
    val datetime: String,
    val value: Double
)

data class Humidity(
    val datetime: String,
    val value: Double
)

data class Precipitation(
    val datetime: String,
    val value: Double
)

data class Pressure(
    val datetime: String,
    val value: Double
)

data class Skycon(
    val datetime: String,
    val value: String
)

data class Temperature(
    val datetime: String,
    val value: Double
)

data class Visibility(
    val datetime: String,
    val value: Double
)

data class Wind(
    val datetime: String,
    val direction: Double,
    val speed: Double
)

data class Aqi(
    val datetime: String,
    val value: Value
)

data class Pm25(
    val datetime: String,
    val value: Int
)

data class Value(
    val chn: Int,
    val usa: Int
)