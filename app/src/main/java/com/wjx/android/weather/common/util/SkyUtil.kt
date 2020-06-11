package com.wjx.android.weather.common.util

import com.wjx.android.weather.R

class Sky(val info: String, val icon: Int)

private val sky = mapOf(
    "CLEAR_DAY" to Sky(
        "晴",
        R.drawable.vector_drawable_ic_clear_day
    ),
    "CLEAR_NIGHT" to Sky(
        "晴",
        R.drawable.vector_drawable_ic_clear_night
    ),
    "PARTLY_CLOUDY_DAY" to Sky(
        "多云",
        R.drawable.vector_drawable_ic_partly_cloud_day
    ),
    "PARTLY_CLOUDY_NIGHT" to Sky(
        "多云",
        R.drawable.vector_drawable_ic_partly_cloud_night
    ),
    "CLOUDY" to Sky(
        "阴",
        R.drawable.vector_drawable_ic_cloudy
    ),
    "WIND" to Sky(
        "大风",
        R.drawable.vector_drawable_ic_windy
    ),
    "LIGHT_RAIN" to Sky(
        "小雨",
        R.drawable.vector_drawable_ic_light_rain
    ),
    "MODERATE_RAIN" to Sky(
        "中雨",
        R.drawable.vector_drawable_ic_moderate_rain
    ),
    "HEAVY_RAIN" to Sky(
        "大雨",
        R.drawable.vector_drawable_ic_heavy_rain
    ),
    "STORM_RAIN" to Sky(
        "暴雨",
        R.drawable.vector_drawable_ic_storm_rain
    ),
    "THUNDER_SHOWER" to Sky(
        "雷阵雨",
        R.drawable.vector_drawable_ic_thunder_shower
    ),
    "SLEET" to Sky(
        "雨夹雪",
        R.drawable.vector_drawable_ic_sleet
    ),
    "LIGHT_SNOW" to Sky(
        "小雪",
        R.drawable.vector_drawable_ic_light_snow
    ),
    "MODERATE_SNOW" to Sky(
        "中雪",
        R.drawable.vector_drawable_ic_moderate_snow
    ),
    "HEAVY_SNOW" to Sky(
        "大雪",
        R.drawable.vector_drawable_ic_heavy_snow
    ),
    "STORM_SNOW" to Sky(
        "暴雪",
        R.drawable.vector_drawable_ic_heavy_snow
    ),
    "HAIL" to Sky(
        "冰雹",
        R.drawable.vector_drawable_ic_hail
    ),
    "LIGHT_HAZE" to Sky(
        "轻度雾霾",
        R.drawable.vector_drawable_ic_light_haze
    ),
    "MODERATE_HAZE" to Sky(
        "中度雾霾",
        R.drawable.vector_drawable_ic_moderate_haze
    ),
    "HEAVY_HAZE" to Sky(
        "重度雾霾",
        R.drawable.vector_drawable_ic_heavy_haze
    ),
    "FOG" to Sky(
        "雾",
        R.drawable.vector_drawable_ic_fog
    ),
    "DUST" to Sky(
        "浮尘",
        R.drawable.vector_drawable_ic_fog
    )
)

fun getSky(skycon: String): Sky {
    return sky[skycon] ?: sky["CLEAR_DAY"]!!
}