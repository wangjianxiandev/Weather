package com.wjx.android.weather.module.home.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjx.android.weather.base.viewmodel.BaseViewModel
import com.wjx.android.weather.common.initiateRequest
import com.wjx.android.weather.model.DailyResponse
import com.wjx.android.weather.model.Place
import com.wjx.android.weather.model.RealTimeResponse
import com.wjx.android.weather.module.home.repository.HomeRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : BaseViewModel<HomeRepository>(application) {

    val mFirstPlaceData: MutableLiveData<Place> = MutableLiveData()
    val mRealTimeData: MutableLiveData<RealTimeResponse> = MutableLiveData()
    val mDailyData: MutableLiveData<DailyResponse> = MutableLiveData()
    fun queryFirstPlace() {
        viewModelScope.launch {
            mFirstPlaceData.value = withContext(Dispatchers.IO) {
                mRepository.queryFirstPlace()
            }
        }
    }

    fun loadRealtimeWeather(lng: String?, lat: String?) {
        initiateRequest(
            { mRealTimeData.value = mRepository.loadRealtimeWeather(lng, lat) },
            loadState
        )
    }

    fun loadDailyWeather(lng: String?, lat: String?) {
        initiateRequest({
            mDailyData.value = mRepository.loadDailyWeather(lng, lat)
        }, loadState)
    }
}