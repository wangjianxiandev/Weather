package com.wjx.android.weather.common

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.wjx.android.weather.base.BaseApplication
import com.wjx.android.weather.module.app.AppViewModel

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/23 19:50
 */
class WeatherWorkManager(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    private val mAppViewModel =
        BaseApplication.instance.getAppViewModelProvider().get(AppViewModel::class.java)

    override fun doWork(): Result {
        mAppViewModel.queryAllChoosePlace()
        return Result.success()
    }

}