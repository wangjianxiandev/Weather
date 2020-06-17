package com.wjx.android.weather.module.searchplace.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wjx.android.weather.model.LocationTypeConverter
import com.wjx.android.weather.model.Place
import com.wjx.android.weather.module.searchplace.model.dao.PlaceDao

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/8 23:11
 */
@Database(entities = [Place::class], version = 1, exportSchema = false)
@TypeConverters(LocationTypeConverter::class)
abstract class PlaceDataBase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
    companion object {
        private var INSTANCE: PlaceDataBase? = null
        fun getInstance(context: Context): PlaceDataBase? {
            if (INSTANCE == null) {
                synchronized(PlaceDataBase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            PlaceDataBase::class.java,
                            "database_weather"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}