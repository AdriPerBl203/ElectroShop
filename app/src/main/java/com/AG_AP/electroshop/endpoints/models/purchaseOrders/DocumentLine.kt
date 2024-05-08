package com.AG_AP.electroshop.endpoints.models.purchaseOrders

data class DocumentLine(

    val ItemCode: String,
    val ItemDescription: String,
    val Quantity: Double,
    val DiscountPercent: Double,
    val LineNum: Int,
    val Price: Double,

)