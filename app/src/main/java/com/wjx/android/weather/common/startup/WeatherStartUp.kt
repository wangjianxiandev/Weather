package com.wjx.android.weather.common.startup

import android.content.Context

import androidx.startup.Initializer
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.kingja.loadsir.core.LoadSir
import com.wjx.android.weather.common.WeatherWorkManager
import java.util.concurrent.TimeUnit

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/7/12 22:16
 */
class WeatherStartUp : Initializer<WorkManager> {
    override fun create(context: Context): WorkManager {
        val request =
            PeriodicWorkRequest.Builder(WeatherWorkManager::class.java, 15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(context).enqueue(request)
        return WorkManager.getInstance(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}