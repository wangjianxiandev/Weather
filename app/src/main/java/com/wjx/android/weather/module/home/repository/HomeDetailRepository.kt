package com.wjx.android.weather.module.home.repository

import android.service.chooser.ChooserTargetService
import androidx.lifecycle.MutableLiveData
import com.wjx.android.weather.base.repository.ApiRepository
import com.wjx.android.weather.common.RoomHelper
import com.wjx.android.weather.common.state.State
import com.wjx.android.weather.model.ChoosePlaceData
import com.wjx.android.weather.model.Place
import retrofit2.http.Path

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/9 0:27
 */
class HomeDetailRepository(var loadState: MutableLiveData<State>) : ApiRepository() {
    suspend fun queryAllPlace() = RoomHelper.queryAllPlace(loadState)
    suspend fun loadRealtimeWeather(lng: String?, lat: String?) =
        apiService.loadRealtimeWeather(lng, lat)

    suspend fun loadDailyWeather(lng: String?, lat: String?) = apiService.loadDailyWeather(lng, lat)
    suspend fun loadHourlyWeather(lng: String?, lat: String?) =
        apiService.loadHourlyWeather(lng, lat)

    suspend fun updateChoosePlace(temperature: Int, skycon: String, name: String) =
        RoomHelper.updateChoosePlace(temperature, skycon, name)
}