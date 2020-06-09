package com.wjx.android.weather.network

import com.wjx.android.weather.common.Constant
import com.wjx.android.weather.model.DailyResponse
import com.wjx.android.weather.model.Hourly
import com.wjx.android.weather.model.HourlyResponse
import com.wjx.android.weather.model.RealTimeResponse
import com.wjx.android.weather.module.searchplace.model.SearchPlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/3 22:07
 */
interface ApiService {
    @GET("v2/place?token=${Constant.CAIYUN_TOKEN}&lang=zh_CN")
    suspend fun searchPlaces(@Query("query") query: String): SearchPlaceResponse

    @GET("v2.5/${Constant.CAIYUN_TOKEN}/{lng},{lat}/realtime.json")
    suspend fun loadRealtimeWeather(
        @Path("lng") lng: String?,
        @Path("lat") lat: String?
    ): RealTimeResponse

    @GET("v2.5/${Constant.CAIYUN_TOKEN}/{lng},{lat}/daily.json")
    suspend fun loadDailyWeather(@Path("lng") lng: String?, @Path("lat") lat: String?): DailyResponse

    @GET("v2.5/${Constant.CAIYUN_TOKEN}/{lng},{lat}/hourly.json")
    suspend fun loadHourlyWeather(
        @Path("lng") lng: String?,
        @Path("lat") lat: String?
    ): HourlyResponse
}