package com.jczm.dataloader.data.model

data class AuctionData (
    val bids : Int,
    val currency : String,
    val currentPrice : Int,
    val endTimeInSec : Long,
    val priority : Int
)