package com.wjx.android.weather.common

import android.util.Log
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

    suspend fun queryAllPlace(loadState: MutableLiveData<State>): MutableList<Place> {
        val response = placeDao?.queryAllPlace()?.toMutableList()
        if (response!!.isEmpty()) {
            loadState.postValue(State(StateType.SUCCESS))
        }
        return response
    }

    suspend fun queryFirstPlace(loadState: MutableLiveData<State>): Place? {
        val response = placeDao?.queryFirstPlace()
        return response
    }

    suspend fun insertPlace(place: Place) {
        placeDao?.let {
            it.queryPlaceByName(place.name)?.let {
                var i = placeDao!!.deleteArticle(it)
                Log.d("insert" ,i.toString())
            }
            it.insertPlace(place)
        }
    }

    suspend fun deletePlace(place: Place) {
        placeDao?.deleteArticle(place)
    }

    suspend fun deleteAll() {
        placeDao?.deleteAll()
    }
}