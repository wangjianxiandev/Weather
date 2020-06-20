package com.wjx.android.weather.module.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/20 17:05
 */
class AppMessageViewModel(application: Application) : AndroidViewModel(application) {
    var addChoosePlace = MutableLiveData<Boolean>()
    var addPlace = MutableLiveData<Boolean>()
}