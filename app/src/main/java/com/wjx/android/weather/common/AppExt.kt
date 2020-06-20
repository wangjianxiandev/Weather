package com.wjx.android.weather.common

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.wjx.android.weather.base.BaseApplication
import com.wjx.android.weather.module.main.AppViewModel
import com.wjx.android.weather.module.main.MessageViewModel

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

fun Fragment.getActivityMessageViewModel(): MessageViewModel {
    BaseApplication.instance.let {
        return it.getAppViewModelProvider().get(MessageViewModel::class.java)
    }
}