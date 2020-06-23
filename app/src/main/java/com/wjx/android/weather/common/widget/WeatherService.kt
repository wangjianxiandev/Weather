package com.wjx.android.weather.common.widget

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/23 20:51
 */
class WeatherService :Service() {
    override fun onBind(intent: Intent?): IBinder? = null
}