package com.jczm.dataloader.data.model

data class AuctionInfo(
    val bids: Int,
    val currencyAr: String,
    val currencyEn: String,
    val currentPrice: Int,
    val endTimeInSec: Long,
    val priority: Int
)