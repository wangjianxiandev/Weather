package com.wjx.android.weather.module.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wjx.android.weather.base.callback.UnPeekLiveData
import com.wjx.android.weather.base.viewmodel.BaseViewModel
import com.wjx.android.weather.model.ChoosePlaceData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/4 12:25
 */
class AppViewModel(application: Application) : BaseViewModel<AppRepository>(application) {
    var mCurrentPlace = UnPeekLiveData<Int>()
    val mChoosePlaceData: UnPeekLiveData<MutableList<ChoosePlaceData>> = UnPeekLiveData()

    fun changeCurrentPlace(position: Int) {
        mCurrentPlace.value = position
    }

    fun queryAllChoosePlace() {
        viewModelScope.launch {
            mChoosePlaceData.value = withContext(Dispatchers.IO) {
                mRepository.queryAllChoosePlace()
            }
        }
    }
}