package com.jczm.dataloader.data.model

import com.google.gson.annotations.SerializedName


data class Car(
    val carID: Int,
    val image: String,
    val titleAr: String,
    val titleEn: String,
    @SerializedName("AuctionInfo")
    val auctionInfo: AuctionInfo
)