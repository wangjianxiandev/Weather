package com.wjx.android.weather.module.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/20 17:05
 */
class AppEventViewModel : ViewModel() {
    var addChoosePlace = MutableLiveData<Boolean>()
    var addPlace = MutableLiveData<Boolean>()
    var changeCurrentPlace = MutableLiveData<Boolean>()
}