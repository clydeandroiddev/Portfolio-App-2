package com.jczm.dataloader.data.model


enum class SortType {
    PRIORTY_ASC,
    PRIORITY_DESC,
    PRICE_ASC,
    PRICE_DESC,
    BIDS,
}

data class SortData(
    val label : String,
    val value : SortType,
    var isSelected : Boolean
)
