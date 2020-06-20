package com.wjx.android.weather.common

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wjx.android.weather.base.BaseApplication
import com.wjx.android.weather.common.state.State
import com.wjx.android.weather.common.state.StateType
import com.wjx.android.weather.model.ChoosePlaceData
import com.wjx.android.weather.model.Place
import com.wjx.android.weather.model.Temperature
import com.wjx.android.weather.module.chooseplace.model.database.ChoosePlaceDataBase
import com.wjx.android.weather.module.searchplace.model.database.PlaceDataBase

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

    suspend fun queryPlaceByName(name: String): Place? {
        val response = placeDao?.queryPlaceByName(name)
        return response
    }

    suspend fun insertPlace(place: Place): Long? =
        placeDao?.let {
            it.queryPlaceByName(place.name)?.let {
                var i = placeDao!!.deletePlace(it)
                Log.d("insert", i.toString())
            }
            it.insertPlace(place)
        }


    suspend fun deletePlace(place: Place?) {
        placeDao?.deletePlace(place!!)
    }

    suspend fun deleteAll() {
        placeDao?.deleteAll()
    }

    private val choosePlaceDataBase by lazy {
        ChoosePlaceDataBase.getInstance(BaseApplication.instance)
    }

    private val choosePlaceDao by lazy {
        choosePlaceDataBase?.choosePlaceDao()
    }

    suspend fun queryAllChoosePlace(loadState: MutableLiveData<State>): MutableList<ChoosePlaceData> {
        val response = choosePlaceDao?.queryAllPlace()?.toMutableList()
        if (response!!.isEmpty()) {
            loadState.postValue(State(StateType.SUCCESS))
        }
        return response
    }

    suspend fun insertChoosePlace(choosePlaceData: ChoosePlaceData): Long? =
        choosePlaceDao?.let {
            it.queryChoosePlaceByName(choosePlaceData.name)?.let {
                var i = choosePlaceDao!!.deleteChoosePlace(it)
                Log.d("insert", i.toString())
            }
            it.insertPlace(choosePlaceData)
        }


    suspend fun updateChoosePlace(temperature: Int, skycon: String, name: String) {
        choosePlaceDao?.let {
            it.updateChoosePlace(temperature, skycon, name)
        }
    }

    suspend fun deleteChoosePlace(choosePlaceData: ChoosePlaceData) {
        choosePlaceDao?.deleteChoosePlace(choosePlaceData)
    }
}