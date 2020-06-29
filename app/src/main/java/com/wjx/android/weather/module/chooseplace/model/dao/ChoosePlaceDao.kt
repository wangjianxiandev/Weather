package com.wjx.android.weather.module.chooseplace.model.dao

import androidx.room.*
import com.wjx.android.weather.model.ChoosePlaceData

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/17 21:37
 */
@Dao
interface ChoosePlaceDao {
    @Transaction
    @Insert(entity = ChoosePlaceData::class)
    suspend fun insertPlace(choosePlaceData: ChoosePlaceData) : Long

    @Transaction
    @Query("SELECT * FROM chooseplacedata ORDER BY primaryKey desc")
    suspend fun queryAllPlace() : MutableList<ChoosePlaceData>

    @Transaction
    @Query("SELECT * FROM chooseplacedata WHERE name = (:name)")
    suspend fun queryChoosePlaceByName(name : String) : ChoosePlaceData?

    @Transaction
    @Query("UPDATE chooseplacedata SET temperature = (:temperature), skycon = (:skycon) WHERE name = (:name)")
    suspend fun updateChoosePlace(temperature: Int, skycon: String, name : String)

    @Transaction
    @Delete(entity = ChoosePlaceData::class)
    suspend fun deleteChoosePlace(choosePlaceData: ChoosePlaceData) : Int
}