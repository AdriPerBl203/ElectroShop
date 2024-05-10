package com.AG_AP.electroshop.endpoints.models.priceList

data class Value(
    val Active: String,
    val BasePriceList: Int,
    val DefaultAdditionalCurrency1: String,
    val DefaultAdditionalCurrency2: String,
    val DefaultPrimeCurrency: String,
    val Factor: Double,
    val FixedAmount: Double,
    val GroupNum: String,
    val IsGrossPrice: String,
    val PriceListName: String,
    val PriceListNo: Int,
    val RoundingMethod: String,
    val RoundingRule: String,
    val ValidFrom: Any,
    val ValidTo: Any
)