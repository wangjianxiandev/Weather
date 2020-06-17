package com.wjx.android.weather.module.searchplace.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjx.android.weather.base.viewmodel.BaseViewModel
import com.wjx.android.weather.common.initiateRequest
import com.wjx.android.weather.model.ChoosePlaceData
import com.wjx.android.weather.model.Place
import com.wjx.android.weather.model.RealTime
import com.wjx.android.weather.module.searchplace.model.SearchPlaceResponse
import com.wjx.android.weather.module.searchplace.repository.SearchPlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchPlaceViewModel(application: Application) : BaseViewModel<SearchPlaceRepository>(
    application
) {
    val mSearchPlacesData: MutableLiveData<SearchPlaceResponse> = MutableLiveData()
    val mRealTimeData: MutableLiveData<RealTime> = MutableLiveData()

    fun searchPlaces(query: String) {
        initiateRequest({
            mSearchPlacesData.value = mRepository.searchPlaces(query)
        }, loadState)
    }

    fun insertPlace(place: Place) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mRepository.insertPlaces(place)
            }
        }
    }

    fun loadRealtimeWeather(lng: String?, lat: String?) {
        initiateRequest(
            { mRealTimeData.value = mRepository.loadRealtimeWeather(lng, lat) },
            loadState
        )
    }


    fun insertChoosePlace(choosePlaceData: ChoosePlaceData) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mRepository.insertChoosePlaces(choosePlaceData)
            }
        }
    }
}