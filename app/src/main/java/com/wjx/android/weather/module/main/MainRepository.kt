package com.wjx.android.weather.module.main

import androidx.lifecycle.MutableLiveData
import com.wjx.android.weather.base.repository.ApiRepository
import com.wjx.android.weather.common.RoomHelper
import com.wjx.android.weather.common.state.State
import com.wjx.android.weather.model.ChoosePlaceData
import com.wjx.android.weather.model.Place
import com.wjx.android.weather.module.searchplace.model.SearchPlaceResponse

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/28 20:56
 */
class MainRepository(var loadState: MutableLiveData<State>) : ApiRepository() {
    suspend fun searchPlaces(query: String): SearchPlaceResponse {
        return apiService.searchPlaces(query)
    }

    suspend fun loadRealtimeWeather(lng: String?, lat: String?) =
        apiService.loadRealtimeWeather(lng, lat)

    suspend fun insertPlaces(place: Place): Long? = RoomHelper.insertPlace(place)

    suspend fun insertChoosePlaces(choosePlaceData: ChoosePlaceData): Long? =
        RoomHelper.insertChoosePlace(choosePlaceData)
}