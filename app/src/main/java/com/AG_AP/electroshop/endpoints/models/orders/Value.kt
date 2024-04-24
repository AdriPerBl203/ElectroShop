package com.AG_AP.electroshop.endpoints.models.orders

data class Value(

    val CardCode: String,
    val CardName: String,
    val DocNum: Int,
    val DocDate: String,
    val DocDueDate: String,
    val TaxDate: String,
    val Comments: String,
    val DocTotal: Double,
    val DocTotalFc: Double,
    val DocTotalSys: Double,
    val DiscountPercent: Double,
    val DocumentLines: List<DocumentLine>,

)