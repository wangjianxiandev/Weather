package com.wjx.android.weather.model

import androidx.room.TypeConverter
import org.json.JSONObject

class LocationTypeConverter {
    @TypeConverter
    fun fromLocation(location: Location): String {
        return JSONObject().apply {
            put("lng", location.lng)
            put("lat", location.lat)
        }.toString()
    }

    @TypeConverter
    fun toLocation(location: String): Location {
        val json = JSONObject(location)
        return Location(json.getString("lng"), json.getString("lat"))
    }
}