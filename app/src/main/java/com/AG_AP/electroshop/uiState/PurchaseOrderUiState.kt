package com.AG_AP.electroshop.uiState

data class PurchaseOrderUiState(
    val CardCode: String = "",
    val CardName: String = "",
    val DocDate: String = "",
    val DocDueDate: String = "",
    val TaxDate: String = "",
    val DiscountPercent: String ="", //Descuento
    val DocumentLine: List<ArticleUiState?> = listOf()
    /*val Comments: String = "",
    val CardCode: String = "",
    val CardCode: String = "",
    val CardCode: String = "",
    val CardCode: String = "",
    val CardCode: String = "",
    val CardCode: String = ""*/
)
