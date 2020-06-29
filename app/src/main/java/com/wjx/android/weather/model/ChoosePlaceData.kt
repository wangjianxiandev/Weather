package com.wjx.android.weather.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/17 20:47
 */
@Entity
data class ChoosePlaceData(
    @PrimaryKey(autoGenerate = true)
    var primaryKey: Int,
    var isLocation : Boolean,
    val name : String,
    val temperature: Int,
    val skycon : String
)