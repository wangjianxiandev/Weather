package com.wjx.android.weather.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/4 17:52
 */
@Entity
data class Place(
    @PrimaryKey(autoGenerate = true)
    var primaryKey: Int,
    var name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String = ""
) {
    constructor() : this(0, "", Location("", ""), "")
}