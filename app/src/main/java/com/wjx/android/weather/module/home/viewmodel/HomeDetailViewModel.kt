package com.wjx.android.weather.module.home.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjx.android.weather.base.viewmodel.BaseViewModel
import com.wjx.android.weather.common.initiateRequest
import com.wjx.android.weather.model.*
import com.wjx.android.weather.module.home.repository.HomeDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeDetailViewModel(application: Application) :
    BaseViewModel<HomeDetailRepository>(application) {
    val mRealTimeData: MutableLiveData<RealTime> = MutableLiveData()
    val mDailyData: MutableLiveData<Daily> = MutableLiveData()
    val mHourlyData: MutableLiveData<HourlyData> = MutableLiveData()

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

    fun loadHourlyWeather(lng: String?, lat: String?) {
        initiateRequest({
            mHourlyData.value = mRepository.loadHourlyWeather(lng, lat)
        }, loadState)
    }

    fun updateChoosePlace(temperature: Int, skycon: String, name: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mRepository.updateChoosePlace(temperature, skycon, name)
            }
        }
    }
}