package com.jczm.dataloader.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jczm.dataloader.data.database.dao.AuctionInfoDao
import com.jczm.dataloader.data.database.dao.CarDao
import com.jczm.dataloader.data.database.entities.AuctionEntity
import com.jczm.dataloader.data.database.entities.CarEntity
import com.jczm.dataloader.util.constants.Values


@Database(entities = [CarEntity::class, AuctionEntity::class], version = 1, exportSchema = false)
abstract class CarDatabase : RoomDatabase() {
    abstract fun carDao() : CarDao
    abstract fun auctionInfoDao() : AuctionInfoDao

    companion object {
        fun initWith(context: Context): CarDatabase {
            return Room.databaseBuilder(context, CarDatabase::class.java, Values.CARS_DB)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
    }


}