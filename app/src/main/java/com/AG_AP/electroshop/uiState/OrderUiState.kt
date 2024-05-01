package com.AG_AP.electroshop.uiState

data class OrderUiState(
    val CardCode: String = "",
    val CardName: String = "",
    val DocDate: String = "",
    val DocDueDate: String = "",
    val TaxDate: String = "",
    val DiscountPercent: String ="", //Descuento
    val DocumentLine: MutableList<ArticleUiState?> = listOf(
        ArticleUiState(
            0,"","",0.0F,0.0F,0.0F
        ),
        ArticleUiState(
            1,"","",0.0F,0.0F,0.0F
        ),
        ArticleUiState(
            2,"","",0.0F,0.0F,0.0F
        ),
        ArticleUiState(
            3,"","",0.0F,0.0F,0.0F
        ),
        ArticleUiState(
            4,"","",0.0F,0.0F,0.0F
        )
    ).toMutableList(),
    val DocumentLineList: MutableList<String> = mutableListOf(),
    val trash:Int =0
)

