package com.AG_AP.electroshop.uiState

import java.util.concurrent.ConcurrentHashMap

data class PurchaseOrderUiState(
    val CardCode: String = "",
    val CardName: String = "",
    val DocNum: Int = -1,
    val DocDate: String = "",
    val DocDueDate: String = "",
    val TaxDate: String = "",
    val DiscountPercent: Double = 0.0, //Descuento
    val DocumentLine: MutableList<ArticleUiState?> = listOf(
        ArticleUiState(
            0, "", "", 0.0F, 0.0F, 0.0F
        ),
        ArticleUiState(
            1, "", "", 0.0F, 0.0F, 0.0F
        ),
        ArticleUiState(
            2, "", "", 0.0F, 0.0F, 0.0F
        ),
        ArticleUiState(
            3, "", "", 0.0F, 0.0F, 0.0F
        ),
        ArticleUiState(
            4, "", "", 0.0F, 0.0F, 0.0F
        )
    ).toMutableList(),
    val DocumentLineList: ConcurrentHashMap<Int, MutableList<String>> = ConcurrentHashMap(),
    val trash: Int = 0,
    val message:Boolean = false,
    val progress:Boolean = false,
    val text:String = ""
)
