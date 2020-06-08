package com.wjx.android.weather.module.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjx.android.weather.base.viewmodel.BaseViewModel
import com.wjx.android.weather.model.Place

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : BaseViewModel<HomeRepository>(application) {

    val mFirstPlaceData: MutableLiveData<Place> = MutableLiveData()
    fun queryFirstPlace() {
        viewModelScope.launch {
            mFirstPlaceData.value = withContext(Dispatchers.IO) {
                mRepository.queryFirstPlace()
            }
        }
    }
}