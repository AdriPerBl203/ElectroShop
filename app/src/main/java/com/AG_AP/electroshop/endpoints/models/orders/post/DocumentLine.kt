package com.AG_AP.electroshop.endpoints.models.orders.post

data class DocumentLine(
    val DiscountPercent: Double,
    val ItemCode: String,
    val LineNum: Int,
    val Price: Double,
    val Quantity: Int
)