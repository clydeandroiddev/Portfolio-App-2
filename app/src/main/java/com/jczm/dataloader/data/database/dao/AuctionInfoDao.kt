package com.jczm.dataloader.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.jczm.dataloader.data.database.entities.AuctionEntity
import com.jczm.dataloader.data.database.entities.CarEntity

@Dao
interface AuctionInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAuctionInfo(auctionInfos : List<AuctionEntity>)


}