package com.wjx.android.weather.module.main.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjx.android.weather.base.viewmodel.BaseViewModel
import com.wjx.android.weather.common.initiateRequest
import com.wjx.android.weather.model.ChoosePlaceData
import com.wjx.android.weather.model.Place
import com.wjx.android.weather.model.RealTime
import com.wjx.android.weather.module.main.repository.MainRepository
import com.wjx.android.weather.module.searchplace.model.SearchPlaceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/28 20:55
 */
class MainViewModel : BaseViewModel<MainRepository>() {
    val mSearchPlacesData: MutableLiveData<SearchPlaceResponse> = MutableLiveData()
    val mRealTimeData: MutableLiveData<RealTime> = MutableLiveData()
    val mPlaceInsertResult: MutableLiveData<Long?> = MutableLiveData()
    val mChoosePlaceInsertResult: MutableLiveData<Long?> = MutableLiveData()

    fun searchPlaces(query: String) {
        initiateRequest({
            mSearchPlacesData.value = mRepository.searchPlaces(query)
        }, loadState)
    }

    fun insertPlace(place: Place) {
        viewModelScope.launch {
            mPlaceInsertResult.value = withContext(Dispatchers.IO) {
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
            mChoosePlaceInsertResult.value = withContext(Dispatchers.IO) {
                mRepository.insertChoosePlaces(choosePlaceData)
            }
        }
    }
}