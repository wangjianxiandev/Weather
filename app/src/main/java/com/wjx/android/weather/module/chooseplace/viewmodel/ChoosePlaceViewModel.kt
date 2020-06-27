package com.wjx.android.weather.module.chooseplace.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjx.android.weather.base.viewmodel.BaseViewModel
import com.wjx.android.weather.common.RoomHelper
import com.wjx.android.weather.common.initiateRequest
import com.wjx.android.weather.model.ChoosePlaceData
import com.wjx.android.weather.model.Place
import com.wjx.android.weather.model.RealTime
import com.wjx.android.weather.module.chooseplace.repository.ChoosePlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChoosePlaceViewModel :
    BaseViewModel<ChoosePlaceRepository>() {

    val mPlaceData: MutableLiveData<MutableList<Place>> = MutableLiveData()
    val mRealTimeData: MutableLiveData<RealTime> = MutableLiveData()
    val mChoosePlaceData : MutableLiveData<MutableList<ChoosePlaceData>> = MutableLiveData()

    fun queryAllPlace() {
        viewModelScope.launch {
            mPlaceData.value = withContext(Dispatchers.IO) {
                mRepository.queryAllPlace()
            }
        }
    }

    fun queryAllChoosePlace() {
        viewModelScope.launch {
            mChoosePlaceData.value = withContext(Dispatchers.IO) {
                mRepository.queryAllChoosePlace()
            }
        }
    }

    fun deleteChoosePlace(choosePlaceData: ChoosePlaceData) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mRepository.deleteChoosePlace(choosePlaceData)
                queryAllChoosePlace()
            }
        }
    }

    fun deletePlace(name : String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mRepository.deletePlace(RoomHelper.queryPlaceByName(name))
                queryAllChoosePlace()
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mRepository.deleteAll()
                queryAllPlace()
            }
        }
    }

    fun loadRealtimeWeather(lng: String?, lat: String?) {
        initiateRequest(
            { mRealTimeData.value = mRepository.loadRealtimeWeather(lng, lat) },
            loadState
        )
    }
}