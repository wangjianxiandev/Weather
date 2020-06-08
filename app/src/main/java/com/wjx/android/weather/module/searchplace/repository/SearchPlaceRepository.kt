package com.wjx.android.weather.module.searchplace.repository

import androidx.lifecycle.MutableLiveData
import com.wjx.android.weather.base.repository.ApiRepository
import com.wjx.android.weather.common.state.State
import com.wjx.android.weather.common.state.StateType
import com.wjx.android.weather.module.searchplace.model.SearchPlaceResponse

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/6 10:43
 */
class SearchPlaceRepository(var loadState: MutableLiveData<State>) : ApiRepository() {
    suspend fun searchPlaces(query : String) : SearchPlaceResponse {
        return apiService.searchPlaces(query)
    }
}