package com.wjx.android.weather.common

import androidx.lifecycle.MutableLiveData
import com.wjx.android.weather.base.BaseApplication
import com.wjx.android.weather.common.state.State
import com.wjx.android.weather.common.state.StateType
import com.wjx.android.weather.model.Place
import com.wjx.android.weather.module.chooseplace.model.database.PlaceDataBase

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/8 23:19
 */
object RoomHelper {
    private val placeDataBase by lazy {
        PlaceDataBase.getInstance(BaseApplication.instance)
    }

    private val placeDao by lazy {
        placeDataBase?.placeDao()
    }

    suspend fun queryAllPlace(loadState: MutableLiveData<State>): List<Place> {
        val response = placeDao?.queryAllPlace()?.reversed()
        if (response!!.isEmpty()) {
            loadState.postValue(State(StateType.SUCCESS))
        }
        return response
    }

    suspend fun insertPlace(place: Place) {
        placeDao?.let {
            it.queryPlaceByName(place.name)?.let {
                var i = placeDao!!.deleteArticle(place)
            }
            it.insertPlace(place.apply { primaryKey = 0 })
        }
    }

    suspend fun deletePlace(place: Place) {
        placeDao?.deleteArticle(place)
    }

    suspend fun deleteAll() {
        placeDao?.deleteAll()
    }
}