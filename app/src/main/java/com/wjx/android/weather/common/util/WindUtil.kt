package com.wjx.android.weather.common.util

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/16 21:53
 */
class WindOri(val ori: String)
class WindSpeed(val speed: String)


fun getWindOri(windOri: Double): WindOri {
    if (windOri in 11.26..56.25) {
        return WindOri("东北风")
    }
    if (windOri in 56.26..78.75) {
        return WindOri("东风")
    }
    if (windOri in 78.76..146.25) {
        return WindOri("东南风")
    }
    if (windOri in 146.26..168.75) {
        return WindOri("南风")
    }
    if (windOri in 168.76..236.25) {
        return WindOri("西南风")
    }
    if (windOri in 236.26..258.75) {
        return WindOri("西风")
    }
    if (windOri in 281.26..348.75) {
        return WindOri("西北风")
    }
    return WindOri("北风")
}

fun getWindSpeed(windSpeed: Double): WindSpeed {
    if (windSpeed in 1.0..5.0) {
        return WindSpeed("1级")
    }
    if (windSpeed in 6.0..11.0) {
        return WindSpeed("2级")
    }
    if (windSpeed in 12.0..19.0) {
        return WindSpeed("3级")
    }
    if (windSpeed in 20.0..28.0) {
        return WindSpeed("4级")
    }
    if (windSpeed in 29.0..38.0) {
        return WindSpeed("5级")
    }
    if (windSpeed in 39.0..49.0) {
        return WindSpeed("6级")
    }
    if (windSpeed in 50.0..61.0) {
        return WindSpeed("7级")
    }
    if (windSpeed in 62.0..74.0) {
        return WindSpeed("8级")
    }
    if (windSpeed in 75.0..88.0) {
        return WindSpeed("9级")
    }
    if (windSpeed in 89.0..102.0) {
        return WindSpeed("10级")
    }
    if (windSpeed in 103.0..117.0) {
        return WindSpeed("11级")
    }
    if (windSpeed in 118.0..133.0) {
        return WindSpeed("12级")
    }

    if (windSpeed in 134.0..149.0) {
        return WindSpeed("13级")
    }
    if (windSpeed in 150.0..166.0) {
        return WindSpeed("14级")
    }
    if (windSpeed in 167.0..183.0) {
        return WindSpeed("15级")
    }
    if (windSpeed in 184.0..201.0) {
        return WindSpeed("16级")
    }
    if (windSpeed in 202.0..220.0) {
        return WindSpeed("17级")
    }
    return WindSpeed("0级")
}