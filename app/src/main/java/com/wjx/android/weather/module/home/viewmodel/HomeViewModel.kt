package com.wjx.android.weather.module.home.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjx.android.weather.base.viewmodel.BaseViewModel
import com.wjx.android.weather.model.Place
import com.wjx.android.weather.module.home.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/17 20:41
 */
class HomeViewModel(application: Application) :
    BaseViewModel<HomeRepository>(application) {
    val mPlaceData: MutableLiveData<MutableList<Place>> = MutableLiveData()

    fun queryAllPlace() {
        viewModelScope.launch {
            mPlaceData.value = withContext(Dispatchers.IO) {
                mRepository.queryAllPlace()
            }
        }
    }
}