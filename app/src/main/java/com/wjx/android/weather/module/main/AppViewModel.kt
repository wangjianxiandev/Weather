package com.wjx.android.weather.module.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjx.android.weather.base.viewmodel.BaseViewModel
import com.wjx.android.weather.model.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/4 12:25
 */
class AppViewModel(application: Application) : BaseViewModel<MainRepository>(application) {
    var currentPlace = MutableLiveData<Place>()

    val mPlaceData: MutableLiveData<MutableList<Place>> = MutableLiveData()

    fun changeCurrentPlace(place: Place) {
        currentPlace.value = place
    }

    fun queryAllPlace() {
        viewModelScope.launch {
            mPlaceData.value = withContext(Dispatchers.IO) {
                mRepository.queryAllPlace()
            }
        }
    }
}