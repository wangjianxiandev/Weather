package com.wjx.android.weather.base

import android.app.Application
import com.kingja.loadsir.core.LoadSir
import com.wjx.android.wanandroidmvvm.common.callback.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/3 21:57
 */
open class BaseApplication  : Application() {
    companion object {
        lateinit var instance : BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initLoadSir()
    }

    private fun initLoadSir() {
        LoadSir.beginBuilder()
            .addCallback(ErrorCallBack())
            .addCallback(LoadingCallBack())
            .addCallback(EmptyCallBack())
            .commit()
    }
}