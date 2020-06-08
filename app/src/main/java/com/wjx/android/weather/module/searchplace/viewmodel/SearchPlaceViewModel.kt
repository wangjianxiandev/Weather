package com.wjx.android.weather.module.searchplace.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wjx.android.weather.base.viewmodel.BaseViewModel
import com.wjx.android.weather.common.initiateRequest
import com.wjx.android.weather.module.searchplace.model.SearchPlaceResponse
import com.wjx.android.weather.module.searchplace.repository.SearchPlaceRepository

class SearchPlaceViewModel(application: Application) : BaseViewModel<SearchPlaceRepository>(
    application
) {
    val mSearchPlacesData: MutableLiveData<SearchPlaceResponse> = MutableLiveData()

    fun searchPlaces(query: String) {
        initiateRequest({
            mSearchPlacesData.value = mRepository.searchPlaces(query)
        }, loadState)
    }
}