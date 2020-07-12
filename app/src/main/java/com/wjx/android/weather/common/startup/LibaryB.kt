package com.wjx.android.weather.common.startup

import android.content.Context
import androidx.startup.AppInitializer
import androidx.startup.Initializer
import androidx.work.WorkManager

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/7/12 21:58
 */
// Initializes WorkManager.
class LibaryB : Initializer<WorkManager> {
    override fun create(context: Context): WorkManager {
        // 手动初始化LibaryA
        AppInitializer.getInstance(context).initializeComponent(LibaryA::class.java)
        return WorkManager.getInstance(context)
    }

    /**
     * 返回需要提前初始化的列表，同时设置App启动时的依赖库的依赖顺序
     */
    override fun dependencies(): List<Class<out Initializer<*>>> {
        // LibaryB的实例化依赖LibaryA
        return mutableListOf(LibaryA::class.java)
    }
}