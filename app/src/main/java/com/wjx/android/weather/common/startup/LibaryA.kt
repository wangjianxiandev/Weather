package com.wjx.android.weather.common.startup

import android.content.Context
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/7/12 22:04
 */
class LibaryA : Initializer<WorkManager> {
    override fun create(context: Context): WorkManager {
        // 初始化LibaryA
        val configuration = Configuration.Builder().build()
        WorkManager.initialize(context, configuration)
        return WorkManager.getInstance(context)
    }
    override fun dependencies(): List<Class<out Initializer<*>>> {
        // LibaryA实例化不需要依赖其他
        return emptyList()
    }
}