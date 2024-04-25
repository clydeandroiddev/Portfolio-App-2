package com.jczm.dataloader.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName="cars"
)
data class CarEntity (
    @PrimaryKey
    val id : Int,
    val image : String,
    val titleEn : String,
    val titleAr : String
)