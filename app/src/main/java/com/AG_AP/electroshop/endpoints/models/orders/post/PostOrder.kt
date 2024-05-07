package com.AG_AP.electroshop.endpoints.models.orders.post

data class PostOrder(
    val CardCode: String,
    val CardName: String,
    val DiscountPercent: Double,
    val DocDate: String,
    val DocDueDate: String,
    val DocNum: Int,
    val DocumentLines: List<DocumentLine>,
    val TaxDate: String
)