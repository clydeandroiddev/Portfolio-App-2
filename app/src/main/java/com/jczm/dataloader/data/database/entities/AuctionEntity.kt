package com.jczm.dataloader.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "auctionInfo"
)
data class AuctionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "aucId")
    val id : Int,
    val carId : Int,
    val bids : Int,
    val endTimeInSec : Long,
    val currencyEN : String,
    val currencyAr : String,
    val currentPrice : Int,
    val priority : Int
)