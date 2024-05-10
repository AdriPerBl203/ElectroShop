package com.AG_AP.electroshop.uiState.Items

data class ArticleUiState(
    val LineNum:Int =0,
    val ItemCode:String ="",
    val ItemDescription:String="",
    val Quantity: Float= 0.0F,
    val Price: Float = 0.0F,
    val DiscountPercent: Float =0.0F
)
