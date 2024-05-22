package com.AG_AP.electroshop.endpoints.models.specialPrices

data class Value(
    val AutoUpdate: String,
    val CardCode: String,
    val Currency: String,
    val DiscountPercent: Double,
    val ItemCode: String,
    val Price: Double,
    val PriceListNum: Int,
    val SourcePrice: String,
    val SpecialPriceDataAreas: List<Any>,
    val Valid: String,
    val ValidFrom: Any,
    val ValidTo: Any
)