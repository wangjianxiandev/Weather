package com.wjx.android.weather.module.addedplace.model.dao

import androidx.room.*
import com.wjx.android.weather.model.Place

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/8 23:11
 */
@Dao
interface PlaceDao {
    @Transaction
    @Insert(entity = Place::class)
    suspend fun insertPlace(place: Place) : Long

    @Transaction
    @Query("SELECT * FROM place")
    suspend fun queryAllPlace() : List<Place>

    @Transaction
    @Query("SELECT * FROM place WHERE name = (:name)")
    suspend fun queryPlaceByName(name : String) : Place?

    @Transaction
    @Query("SELECT * FROM place order by primaryKey desc")
    suspend fun queryFirstPlace() : Place?

    @Transaction
    @Delete(entity = Place::class)
    suspend fun deleteArticle(place: Place) : Int

    @Transaction
    @Query("DELETE FROM place")
    suspend fun deleteAll()
}