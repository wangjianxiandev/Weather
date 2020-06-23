package com.wjx.android.weather.module.main

import android.app.Application
import com.wjx.android.weather.base.callback.UnPeekLiveData
import com.wjx.android.weather.base.viewmodel.BaseViewModel

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/4 12:25
 */
class AppViewModel(application: Application) : BaseViewModel<AppRepository>(application) {
    var currentPlace = UnPeekLiveData<Int>()

    fun changeCurrentPlace(position : Int) {
        currentPlace.value = position
    }
}