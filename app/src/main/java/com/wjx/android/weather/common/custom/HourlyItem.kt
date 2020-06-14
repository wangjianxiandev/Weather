package com.wjx.android.weather.common.custom

import android.graphics.Point
import android.graphics.Rect

/**
 * Created by user on 2016/10/19.
 */
class HourlyItem {
    var time //时间点
            : String? = null
    var windyBoxRect //表示风力的box
            : Rect? = null
    var windy //风力
            = 0
    var temperature //温度
            = 0
    var tempPoint //温度的点坐标
            : Point? = null
    var res = -1 //图片资源(有则绘制)
}