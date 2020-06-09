package com.wjx.android.weather.common

import java.util.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/9 13:58
 */
object DateUtil {
    fun getTodayInWeek(data: Date): String {
        var list = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
        var calendar: Calendar = Calendar.getInstance()
        calendar.time = data
        var index: Int = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (index < 0) {
            index = 0
        }
        return list[index]
    }
}