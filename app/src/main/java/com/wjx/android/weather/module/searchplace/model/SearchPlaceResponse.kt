package com.wjx.android.weather.module.searchplace.model

import com.wjx.android.weather.model.Place

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/8 21:19
 */
data class SearchPlaceResponse(
    val status: String,
    val places : List<Place>
)