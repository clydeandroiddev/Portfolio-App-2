package com.jczm.dataloader.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SupportSQLiteQuery
import com.jczm.dataloader.data.database.entities.CarDataEntity
import com.jczm.dataloader.data.database.entities.CarEntity

@Dao
interface CarDao {
    @Transaction
    @Query("SELECT cars.*, auctionInfo.* FROM cars INNER JOIN auctionInfo ON cars.id = auctionInfo.carId ORDER BY auctionInfo.priority ASC")
    suspend fun getCarsList() : List<CarDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCars(cars : List<CarEntity>)

    @Query("SELECT COUNT(id) FROM cars")
    fun getCount() : Int;

    @Transaction
    @RawQuery
    suspend fun getCartListWith(query: SupportSQLiteQuery) : List<CarDataEntity>

}