package com.wjx.android.weather.module.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.weather.base.callback.UnPeekLiveData
import com.wjx.android.weather.base.viewmodel.BaseViewModel
import com.wjx.android.weather.model.Place

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/4 12:25
 */
class AppViewModel(application: Application) : BaseViewModel<MainRepository>(application) {
    var currentPlace = MutableLiveData<Place>()

    fun changeCurrentPlace(place: Place) {
        currentPlace.value = place
    }
}