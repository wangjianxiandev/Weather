package com.wjx.android.weather.module.chooseplace.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjx.android.weather.base.viewmodel.BaseViewModel
import com.wjx.android.weather.model.Place
import com.wjx.android.weather.module.chooseplace.repository.ChoosePlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChoosePlaceViewModel(application: Application) :
    BaseViewModel<ChoosePlaceRepository>(application) {

    val mPlaceData: MutableLiveData<MutableList<Place>> = MutableLiveData()

    fun queryAllPlace() {
        viewModelScope.launch {
            mPlaceData.value = withContext(Dispatchers.IO) {
                mRepository.queryAllPlace()
            }
        }
    }

    fun deletePlace(place: Place) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mRepository.deletePlace(place)
                queryAllPlace()
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
}