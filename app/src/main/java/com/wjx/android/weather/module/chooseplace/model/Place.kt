package com.wjx.android.weather.module.chooseplace.model

import com.google.gson.annotations.SerializedName

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/4 17:52
 */
data class Place(
    val name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String
)