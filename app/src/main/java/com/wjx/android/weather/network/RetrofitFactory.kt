package com.wjx.android.weather.network

import com.wjx.android.weather.common.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/3 22:10
 */
class RetrofitFactory private constructor() {
    private val retrofit : Retrofit

    fun <T> createRetrofit(clazz: Class<T>) : T {
        return retrofit.create(clazz)
    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        val instance by lazy {
            RetrofitFactory()
        }
    }
}