package com.AG_AP.electroshop.endpoints.models.item.getItems

data class ItemPrice(
    val AdditionalCurrency1: Any,
    val AdditionalCurrency2: Any,
    val AdditionalPrice1: Double,
    val AdditionalPrice2: Double,
    val BasePriceList: Int,
    val Currency: String,
    val Factor: Double,
    val Price: Double,
    val PriceList: Int,
)