package com.wjx.android.weather.module.home

import androidx.lifecycle.MutableLiveData
import com.wjx.android.weather.base.repository.ApiRepository
import com.wjx.android.weather.common.RoomHelper
import com.wjx.android.weather.common.state.State

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/9 0:27
 */
class HomeRepository (var loadState: MutableLiveData<State>) : ApiRepository() {
    suspend fun queryFirstPlace() = RoomHelper.queryFirstPlace(loadState)
}