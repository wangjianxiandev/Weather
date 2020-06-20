package com.wjx.android.weather.module.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.weather.base.viewmodel.BaseViewModel
import com.wjx.android.weather.model.ChoosePlaceData
import com.wjx.android.weather.model.Place

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/4 12:25
 */
class AppViewModel(application: Application) : BaseViewModel<AppRepository>(application) {
    var currentPlace = MutableLiveData<Place>()

    val mChoosePlaceData : MutableLiveData<MutableList<ChoosePlaceData>> = MutableLiveData()

    fun changeCurrentPlace(place: Place) {
        currentPlace.value = place
    }
}