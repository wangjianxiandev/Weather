package com.wjx.android.weather.common

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.wjx.android.weather.base.BaseApplication
import com.wjx.android.weather.module.main.AppViewModel

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/9 23:29
 */
fun AppCompatActivity.getAppViewModel(): AppViewModel {
    BaseApplication.instance.let {
        return it.getAppViewModelProvider().get(AppViewModel::class.java)
    }
}

fun Fragment.getAppViewModel(): AppViewModel {
    BaseApplication.instance.let {
        return it.getAppViewModelProvider().get(AppViewModel::class.java)
    }
}