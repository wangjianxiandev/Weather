package com.wjx.android.weather.network

import com.wjx.android.weather.common.Constant
import com.wjx.android.weather.module.searchplace.model.SearchPlaceResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/3 22:07
 */
interface ApiService {
    @GET("v2/place?token=${Constant.CAIYUN_TOKEN}&lang=zh_CN")
    suspend fun searchPlaces(@Query("query") query : String) : SearchPlaceResponse
}