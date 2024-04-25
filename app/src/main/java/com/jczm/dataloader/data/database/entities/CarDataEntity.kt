package com.jczm.dataloader.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.jczm.dataloader.data.model.AuctionData
import com.jczm.dataloader.data.model.CarData
import com.jczm.dataloader.util.helper.LangEnum


data class CarDataEntity(
    @Embedded
    val car : CarEntity,
    @Embedded
    val auctionEntity: AuctionEntity
){
    companion object {

        private fun CarDataEntity.toDomain(language: String): CarData {
            return CarData(
                carId = car.id,
                image = car.image.replace("carID", car.id.toString(), true),
                title = if (language == LangEnum.ar.name) car.titleAr else car.titleEn,
                auctionData = AuctionData(
                    bids = auctionEntity.bids,
                    currency = if (language == LangEnum.ar.name) auctionEntity.currencyAr else auctionEntity.currencyEN,
                    currentPrice = auctionEntity.currentPrice,
                    priority = auctionEntity.priority,
                    endTimeInSec = auctionEntity.endTimeInSec
                )
            )
        }

        fun List<CarDataEntity>.toDomainList(language : String) : List<CarData>{
            return map { it.toDomain(language) }
        }

    }
}
