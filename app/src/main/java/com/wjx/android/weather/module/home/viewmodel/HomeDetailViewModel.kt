package com.wjx.android.weather.module.home.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjx.android.weather.base.viewmodel.BaseViewModel
import com.wjx.android.weather.common.initiateRequest
import com.wjx.android.weather.model.Daily
import com.wjx.android.weather.model.HourlyData
import com.wjx.android.weather.model.Place
import com.wjx.android.weather.model.RealTimeData
import com.wjx.android.weather.module.home.repository.HomeDetailRepository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeDetailViewModel(application: Application) :
    BaseViewModel<HomeDetailRepository>(application) {

    val mFirstPlaceData: MutableLiveData<Place> = MutableLiveData()
    val mPlaceData: MutableLiveData<MutableList<Place>> = MutableLiveData()
    val mRealTimeDataData: MutableLiveData<RealTimeData> = MutableLiveData()
    val mDailyData: MutableLiveData<Daily> = MutableLiveData()
    val mHourlyData: MutableLiveData<HourlyData> = MutableLiveData()

    fun queryFirstPlace() {
        viewModelScope.launch {
            mFirstPlaceData.value = withContext(Dispatchers.IO) {
                mRepository.queryFirstPlace()
            }
        }
    }

    fun queryAllPlace() {
        viewModelScope.launch {
            mPlaceData.value = withContext(Dispatchers.IO) {
                mRepository.queryAllPlace()
            }
        }
    }

    fun loadRealtimeWeather(lng: String?, lat: String?) {
        initiateRequest(
            { mRealTimeDataData.value = mRepository.loadRealtimeWeather(lng, lat) },
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
}