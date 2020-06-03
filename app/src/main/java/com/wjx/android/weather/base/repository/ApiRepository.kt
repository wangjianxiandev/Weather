package com.wjx.android.weather.base.repository

import com.wjx.android.weather.network.ApiService
import com.wjx.android.weather.network.RetrofitFactory

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/3 22:54
 */
abstract class ApiRepository : BaseRepository() {
    protected val apiService: ApiService by lazy {
        RetrofitFactory.instance.createRetrofit(ApiService::class.java)
    }
}