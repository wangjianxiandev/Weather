package com.wjx.android.weather.module.chooseplace.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wjx.android.weather.model.ChoosePlaceData
import com.wjx.android.weather.model.LocationTypeConverter
import com.wjx.android.weather.module.chooseplace.model.dao.ChoosePlaceDao

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/17 21:38
 */
@Database(entities = [ChoosePlaceData::class], version = 1, exportSchema = false)
@TypeConverters(LocationTypeConverter::class)
abstract class ChoosePlaceDataBase : RoomDatabase() {
    abstract fun choosePlaceDao(): ChoosePlaceDao
    companion object {
        private var INSTANCE: ChoosePlaceDataBase? = null
        fun getInstance(context: Context): ChoosePlaceDataBase? {
            if (INSTANCE == null) {
                synchronized(ChoosePlaceDataBase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            ChoosePlaceDataBase::class.java,
                            "database_choose_place"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}