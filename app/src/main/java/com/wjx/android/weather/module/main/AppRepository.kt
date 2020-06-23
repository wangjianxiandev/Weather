package com.wjx.android.weather.module.main

import androidx.lifecycle.MutableLiveData
import com.wjx.android.weather.base.repository.ApiRepository
import com.wjx.android.weather.common.RoomHelper
import com.wjx.android.weather.common.state.State

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/4 12:25
 */

class AppRepository(var loadState: MutableLiveData<State>) : ApiRepository() {
    suspend fun queryAllChoosePlace() = RoomHelper.queryAllChoosePlace(loadState)
}